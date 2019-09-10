package connections

import (
	"database/sql"
)

func createDBConnection() *sql.DB {
	connnectionParameters := `dbname=postgres://utproodmdnwuav:2015c03801e4802587b0f5ca24d50864911c9931ede6204275c1912e2191a759@ec2-23-21-91-183.compute-1.amazonaws.com:5432/d5fbhblu8un6fn`
	db, err := sql.Open("postgres", connnectionParameters)
	if err != nil {panic(err)}
	db.SetConnMaxLifetime(60000000000)
	return db
}

func checkIfConnectionIsStillAlive(db *sql.DB) bool {
	err := db.Ping()
	status := true
	if err != nil {
		status = false
	}
	return status
}