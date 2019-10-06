package mylotte_backend

import grails.gorm.transactions.Transactional
import grails.rest.*
import grails.converters.*

class CompanyController {
	static responseFormats = ['json', 'xml']

    def query = {}

    def index() {
        if (params.id) {
            respond Company.get(params.id as long)
        } else {
            def max = Math.min(params.max ?: 10, 100)
            def list = Lot.createCriteria().list(max: max, offset: params.offset) {query}
            respond "entities": list, "total": Lot.count
        }
    }

    def delete() {
        try {
            Company.get(params.id as long).delete(failOnError: true)
            response.status = 204
            respond {}
        } catch (Exception e) {
            response.status = 400
            respond e.getMessage()
        }
    }

    @Transactional
    def saveObject(Company entityToBeSaved) {
        def entity = entityToBeSaved
        def json = request.JSON
        entity.properties = json
        try {
            entity.save(failOnError: true)
            respond entity
        } catch (Exception e) {
            response.status = 400
            respond errors: e.getMessage()
        }
    }

    def edit() {
        saveObject(Company.get(params.id as long))
    }

    def save() {
        saveObject(new Company())
    }
}
