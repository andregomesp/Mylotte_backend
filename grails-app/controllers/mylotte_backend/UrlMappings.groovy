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

        "/api/enterLot"(controller: 'lot', action: 'enterLot')
        "/api/exitLot"(controller: 'lot', action: 'exitLot')
    }
}
