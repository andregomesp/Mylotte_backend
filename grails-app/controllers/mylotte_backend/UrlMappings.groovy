package mylotte_backend

class UrlMappings {

    static mappings = {

        "/"(controller: 'application', action:'index')
        "401"(view: '/notAllowed')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
