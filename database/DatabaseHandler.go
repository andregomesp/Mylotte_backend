package connections

import (
	"../database"
	"context"
	"database/sql"
	"log"
)

var BackgroundCtx = context.Background()
var dbConnection = database.CreateDBConnection()

func PrepareDBTransaction() *sql.Tx {
	isAlive := database.CheckIfConnectionIsStillAlive(dbConnection)
	if isAlive == false {
		dbConnection = database.CreateDBConnection()
	}
	return CreateTransaction(dbConnection)
}

func CreateTransaction(db *sql.DB) *sql.Tx {
	ctx, err := context.WithTimeout(BackgroundCtx, 15000000000)
	if err != nil {
		panic(err)
	}
	tx, err2 := db.BeginTx(ctx, &sql.TxOptions{Isolation: sql.LevelSerializable})
	if err2 != nil {
		panic(err2)
	}
	return tx
}

func CommitTransaction(tx *sql.Tx) bool {
	err := tx.Commit()
	commit := true
	if err != nil {
		commit = false
		log.Print(err)
		RollbackTransaction(tx)
	}
	return commit
}

func RollbackTransaction(tx *sql.Tx) {
	err := tx.Rollback()
	if err != nil {
		log.Println(err)
	}
}
