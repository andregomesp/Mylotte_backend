package database

import (
	"database/sql"
	"fmt"
	_ "github.com/lib/pq"
)

const (
	host = "ec2-23-21-91-183.compute-1.amazonaws.com"
	port = 5432
	user = "utproodmdnwuav"
	password = "2015c03801e4802587b0f5ca24d50864911c9931ede6204275c1912e2191a759"
	dbname = "d5fbhblu8un6fn"
)


func CreateDBConnection() *sql.DB {
	psqlInfo := fmt.Sprintf("host=%s port=%d user=%s "+
		"password=%s dbname=%s	",
		host, port, user, password, dbname)

	db, err := sql.Open("postgres", psqlInfo)
	if err != nil {
		panic(err)
	}
	db.SetConnMaxLifetime(60000000000)
	return db
}

func CheckIfConnectionIsStillAlive(db *sql.DB) bool {
	err := db.Ping()
	status := true
	if err != nil {
		status = false
	}
	return status
}
