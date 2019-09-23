package connections

import (
	"../controllers"
	"net/url"
	"strings"
)

var urlMappings = map[string]interface{}{
	"/lot/get":      controllers.LotGet,
	"/category/get": controllers.CategoryGet,
}

func dynamicCall(name string, params map[string]interface{}, urlParams map[string]interface{}) map[string]interface{} {
	return urlMappings[name].(func(params map[string]interface{}, urlParams map[string]interface{}) map[string]interface{})(params, urlParams)
}

func getPathAndParametersFromUrl(completeUrl string) (string, string) {
	u, err := url.Parse(completeUrl)
	if err != nil {
		panic(err)
	}
	URIstrings := strings.Split(u.RequestURI(), "?")
	if len(URIstrings) > 2 {
		panic("There are more than one '?' symbols on the URI")
	}
	if len(URIstrings) == 1 {
		URIstrings = append(URIstrings, "")
	}
	return URIstrings[0], URIstrings[1]
}
