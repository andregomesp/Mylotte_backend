package main

import (
	"../configurations"
	"fmt"
	"net/http"
)

func handler(w http.ResponseWriter, r *http.Request) {
	_, _ = fmt.Fprintf(w, "Hi there, You just connected to this url %s!", r.URL.Path[1:])
}

func startListening(configs configurations.Configs) {
	http.HandleFunc("/", handler)
	_ = http.ListenAndServe(":" + configs.Port, nil)
}


func main() {
	configs := configurations.NewConfigs()
	startListening(configs)
}