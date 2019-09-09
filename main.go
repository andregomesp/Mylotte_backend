package main

import (
	"./configurations"
	"./connections"
)

func main() {
	configs := configurations.NewConfigs()
	connections.StartListening(configs)
}
