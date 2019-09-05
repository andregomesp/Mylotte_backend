package main

import (
	"../configurations"
	"fmt"
	"net/http"
	"reflect"
)

func handler(w http.ResponseWriter, r *http.Request) {
	path, parameters := getPathAndParametersFromUrl(r.RequestURI)
	methodName := "method"
	params := []reflect.Value {reflect.ValueOf(path), reflect.ValueOf(parameters)}
	results := reflect.ValueOf(methodName).MethodByName(methodName).Call(params)
	_, _ = fmt.Fprintf(w, "Hi, your message is : %s!", r.URL.Path[1:])
}

func startListening(configs configurations.Configs) {
	http.HandleFunc("/", handler)
	_ = http.ListenAndServe(":" + configs.Port, nil)
}


func main() {
	configs := configurations.NewConfigs()
	startListening(configs)
}