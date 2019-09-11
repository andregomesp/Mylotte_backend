package connections

import (
	"context"
	"database/sql"
	"log"
)


func PrepareContext(db *sql.DB) {
	ctx, cancel :=
}

func CreateTransaction(db *sql.DB) *sql.Tx {

	tx, err := db.BeginTx(ctx, &sql.TxOptions{Isolation: sql.LevelSerializable})
	if err != nil {panic(err)}
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
