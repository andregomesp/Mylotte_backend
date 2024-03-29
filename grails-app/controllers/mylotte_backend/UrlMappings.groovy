package mylotte_backend

class UrlMappings {

    static mappings = {
        delete "/api/$controller(.$format)?"(action:"delete")
        get "/api/$controller(.$format)?"(action:"index")
        post "/api/$controller(.$format)?"(action:"save")
        put "/api/$controller(.$format)?"(action:"edit")

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
        "/api/lot/getMyLottes/"(controller: 'lot', action: 'getMyLottes', method: 'GET')
        "/api/lot/enterLot/"(controller: 'lot', action: 'enterLot', method: 'POST')
        "/api/lot/enterLotVenda/"(controller: 'lot', action: 'enterLotVenda', method: 'POST')
        "/api/lot/exitLot/"(controller: 'lot', action: 'exitLot', method: 'DELETE')
    }
}
