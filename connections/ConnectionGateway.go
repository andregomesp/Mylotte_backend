package connections

import (
	"../configurations"
	"encoding/json"
	"fmt"
	"io"
	"io/ioutil"
	"net/http"
	"strings"
)

func handler(w http.ResponseWriter, r *http.Request) {
	path, urlParams := getPathAndParametersFromUrl(r.RequestURI)
	urlParameterMap := constructUrlParameterMap(urlParams)
	paramsMap := constructParameterMap(r.Body)
	path = r.Method + path
	result := dynamicCall(path, paramsMap, urlParameterMap)
	jsonData, err := json.Marshal(result)
	if err != nil {
		panic(err)
	}
	_, _ = fmt.Fprintf(w, string(jsonData))
}

func constructParameterMap(body io.ReadCloser) map[string]interface{} {
	paramsMap := make(map[string]interface{})
	var paramsInterface interface{}
	jsonBytes, err := ioutil.ReadAll(body)
	if err != nil {
		panic(err)
	}
	err = json.Unmarshal(jsonBytes, &paramsInterface)
	paramsMap["json"] = paramsInterface
	if paramsMap["json"] == nil {
		paramsMap["json"] = ""
	}
	return paramsMap
}

func constructUrlParameterMap(urlParams string) map[string]interface{} {
	urlParamsMap := make(map[string]interface{})
	if urlParams != "" {
		splitteredParams := strings.Split(urlParams, "&")
		for _, paramsSet := range splitteredParams {
			keyAndValue := strings.Split(paramsSet, "=")
			key, value := keyAndValue[0], keyAndValue[1]
			urlParamsMap[key] = value
		}
	} else {
		urlParamsMap[""] = ""
	}

	return urlParamsMap
}

func StartListening(configs configurations.Configs) {
	http.HandleFunc("/", handler)
	_ = http.ListenAndServe(":"+configs.Port, nil)
}
