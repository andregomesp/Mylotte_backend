package tools

import (
	"io/ioutil"
)

func ReadTxt(filePath string) string {
	bytes, err := ioutil.ReadFile(filePath)
	if err != nil {panic(err)}
	return string(bytes)
}

