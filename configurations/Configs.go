package configurations

import (
	L "../tools"
	"reflect"
	"strings"
)

type Configs struct {
	Port string
}

func NewConfigs() Configs {
	configString := L.ReadTxt("./configurations/ServerConfigs.txt")
	configValues := strings.Fields(configString)
	configs := Configs{}
	for _, configValue := range configValues {
		configPair := strings.Split(configValue, "=")
		configKey := configPair[0]
		configFieldValue := configPair[1]
		reflect.ValueOf(&configs).Elem().FieldByName(configKey).SetString(configFieldValue)
	}
	return configs
}







