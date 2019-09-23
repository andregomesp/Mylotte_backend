package commands

import (
	"../database"
	"context"
	"reflect"
)

func Get(entity interface{}, id uint64) {
	tx := database.PrepareDBTransaction()
	ctx, err := context.WithTimeout(database.BackgroundCtx, 15000000000)
	if err != nil {panic(err)}
	print(reflect.TypeOf(entity).String())
	query := "select from"
	stmt, err2 := tx.PrepareContext(ctx, query)
	if err2 != nil {panic(err)}
	print(stmt)
}

func Save(entity interface{}, properties map[string]interface{}) {

}

func Edit(entity interface{}, properties map[string]interface{}) {

}

func Erase(entity interface{}, id uint64) {

}
