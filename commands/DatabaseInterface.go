package commands

import "reflect"

func Get(entity interface{}, id uint64) {
	//tx := PrepareDB()
	println(reflect.ValueOf(entity).Type())
}

func Save(entity interface{}, properties map[string]interface{}) {

}

func Edit(entity interface{}, properties map[string]interface{}) {

}

func Erase(entity interface{}, id uint64) {

}
