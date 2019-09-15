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
	dynamicCall(path, paramsMap, urlParameterMap)
	_, _ = fmt.Fprintf(w, "Hi, your message is : %s!", r.URL.Path[1:])
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
	}
	return urlParamsMap
}

func StartListening(configs configurations.Configs) {
	http.HandleFunc("/", handler)
	_ = http.ListenAndServe(":"+configs.Port, nil)
}
