package connections

import (
	"context"
	"database/sql"
	"log"
)

var backgroundCtx = context.Background()
var dbConnection = createDBConnection()

func PrepareDB() *sql.Tx {
	isAlive := checkIfConnectionIsStillAlive(dbConnection)
	if isAlive == false {
		dbConnection = createDBConnection()
	}
	return CreateTransaction(dbConnection)
}

func CreateTransaction(db *sql.DB) *sql.Tx {
	ctx, err := context.WithTimeout(backgroundCtx, 15000000000)
	if err != nil {
		panic(err)
	}
	tx, error := db.BeginTx(ctx, &sql.TxOptions{Isolation: sql.LevelSerializable})
	if error != nil {
		panic(error)
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
