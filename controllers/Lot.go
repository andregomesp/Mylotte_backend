package controllers

import (
	"../commands"
	"../domain"
)

func LotGet(params map[string]interface{}, urlParams map[string]interface{}) map[string]interface{} {
	lot := domain.Lot{}
	commands.Get(lot, 1)
	returnMap := make(map[string]interface{})
	return returnMap
}
