package mylotte_backend


import grails.rest.*
import grails.converters.*

class ProductController {
	static responseFormats = ['json', 'xml']

    def query = {}

    def index() {
        if (params.id) {
            Product.get(params.id as long)
        } else {
            def max = Math.min(params.max ?: 10, 100)
            def list = Product.createCriteria().list(max: max, offset: params.offset) { query }
            respond "entities": list, "total": Product.count
        }
    }

    def delete() {
        try {
            Product.get(params.id as long).delete(failOnError: true)
            response.status = 204
            respond {}
        } catch (Exception e) {
             respond(error: e.message)
        }
    }

    def saveObject(def entityToBeSaved) {
        def entity = entityToBeSaved
        def json = request.JSON
        entity.properties = json
        json.categories.each { it ->
            entity.addTo("categories", Category.get(it as long))
        }
        json.packings.each { it ->
            entity.addTo("packings", Packing.get(it as long))
        }
        try {
            entity.save(failOnError: true)
        } catch (Exception e) {
             respond(error: e.message)
        }
    }

    def edit() {
        saveObject(Product.get(request.JSON.id as long))
    }

    def save() {
        saveObject(new Product())
    }
}
