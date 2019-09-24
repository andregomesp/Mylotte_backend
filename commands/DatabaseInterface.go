package commands

import (
	"../database"
	"context"
	"database/sql"
	"encoding/json"
	"fmt"
	"reflect"
	"strconv"
	"strings"
)

func Get(entity interface{}, id uint64) []byte {
	tx := database.PrepareDBTransaction()
	ctx, cancel := context.WithTimeout(database.BackgroundCtx, 60000000000)
	defer cancel()
	tableName := reflect.TypeOf(entity).String()
	tableName = strings.Split(tableName, ".")[1]
	query := "select * from " + tableName + " where id=" + strconv.FormatInt(int64(id), 10)
	stmt, err := tx.PrepareContext(ctx, query)
	if err != nil {panic(err.Error())}
	rows, err := stmt.QueryContext(ctx)
	bytes, err := SQLToJSON(rows)
	return bytes
}

func Save(entity interface{}, properties map[string]interface{}) {

}

func Edit(entity interface{}, properties map[string]interface{}) {

}

func Erase(entity interface{}, id uint64) {

}

type jsonNullInt64 struct {
	sql.NullInt64
}

func (v jsonNullInt64) MarshalJSON() ([]byte, error) {
	if !v.Valid {
		return json.Marshal(nil)
	}
	return json.Marshal(v.Int64)
}

type jsonNullFloat64 struct {
	sql.NullFloat64
}

func (v jsonNullFloat64) MarshalJSON() ([]byte, error) {
	if !v.Valid {
		return json.Marshal(nil)
	}
	return json.Marshal(v.Float64)
}

var jsonNullInt64Type = reflect.TypeOf(jsonNullInt64{})
var jsonNullFloat64Type = reflect.TypeOf(jsonNullFloat64{})
var nullInt64Type = reflect.TypeOf(sql.NullInt64{})
var nullFloat64Type = reflect.TypeOf(sql.NullFloat64{})

func SQLToJSON(rows *sql.Rows) ([]byte, error) {
	columns, err := rows.Columns()
	if err != nil {
		return nil, fmt.Errorf("Column error: %v", err)
	}

	tt, err := rows.ColumnTypes()
	if err != nil {
		return nil, fmt.Errorf("Column type error: %v", err)
	}

	types := make([]reflect.Type, len(tt))
	for i, tp := range tt {
		st := tp.ScanType()
		if st == nil {
			return nil, fmt.Errorf("Scantype is null for column: %v", err)
		}
		switch st {
		case nullInt64Type:
			types[i] = jsonNullInt64Type
		case nullFloat64Type:
			types[i] = jsonNullFloat64Type
		default:
			types[i] = st
		}
	}

	values := make([]interface{}, len(tt))
	data := make(map[string][]interface{})

	for rows.Next() {
		for i := range values {
			values[i] = reflect.New(types[i]).Interface()
		}
		err = rows.Scan(values...)
		if err != nil {
			return nil, fmt.Errorf("Failed to scan values: %v", err)
		}
		for i, v := range values {
			data[columns[i]] = append(data[columns[i]], v)
		}
	}

	return json.Marshal(data)
}