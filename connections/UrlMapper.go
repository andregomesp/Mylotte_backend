package main

import (
	"net/url"
	"strings"
)

func getPathAndParametersFromUrl (completeUrl string) (string, string) {
	u, err := url.Parse(completeUrl)
	if err != nil {
		panic(err)
	}
	URIstrings := strings.Split(u.RequestURI(), "?")
	if len(URIstrings) > 2 {
		panic("There are more than one '?' symbols on the URI")
	}
	return URIstrings[0], URIstrings[1]
}

func initializeUrlMaps() map[string]map[string]map[string]string {
	//Uses first map key to represent the url and the second one are function and method
	controllers := []string {"lot"}
	urlMaps := map[string]map[string]map[string]string {}
	for _, controller := range controllers {
		controllerMap := map[string]map[string]string {
			"get": {
				"": "get",
			},
			"list": {
				"": "list",
			},
			"post": {
				"": "post",
			},
			"put": {
				"": "put",
			},
			"delete": {
				"": "delete",
			},
		}
		urlMaps[controller] = controllerMap
	}
	return urlMaps
}
