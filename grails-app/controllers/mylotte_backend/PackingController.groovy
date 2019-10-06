package mylotte_backend

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class PackingController {
    SpringSecurityService springSecurityService
    static responseFormats = ['json', 'xml']

    def query = {}

    def index() {
        if (params.id) {
            respond Packing.get(params.id as long)
        } else {
            def max = Math.min(params.max ?: 10, 100)
            def list = Packing.createCriteria().list(max: max, offset: params.offset) {query}
            respond "entities": list, "total": Packing.count
        }
    }

    def delete() {
        try {
            Usuario.get(params.id as long).delete(failOnError: true)
            response.status = 204
            respond {}
        } catch (Exception e) {
            respond e.message
        }
    }

    @Transactional
    def saveObject(Packing entityToBeSaved) {
        def entity = entityToBeSaved
        def json = request.JSON
        entity.properties = json
        try {
            entity.save(failOnError: true)
            respond entity
        } catch (Exception e) {
            transactionStatus.setRollbackOnly()
            respond e.message
        }
    }

    def edit() {
        saveObject(Packing.get(request.JSON.id as long))
    }

    def save() {
        saveObject(new Packing())
    }
}
