package connections

import (
	"context"
	"database/sql"
)

func prepareContext(db *sql.DB) {

}

func createTransaction(db *sql.DB, context context.Context) *sql.Tx {
	tx, err := db.BeginTx(context)
}
