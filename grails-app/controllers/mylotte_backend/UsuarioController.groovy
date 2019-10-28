package mylotte_backend

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService
import grails.rest.*
import grails.converters.*

class UsuarioController {
    SpringSecurityService springSecurityService
    static responseFormats = ['json', 'xml']

    def query = {}

    def index() {
        if (params.id) {
            Usuario usuario = Usuario.get(params.id as long)
            Map response = [nome: usuario.nome, sobrenome: usuario.sobrenome, email: usuario.email]
            respond response
        } else {
            def max = Math.min(params.max ?: 10, 100)
            def list = Usuario.createCriteria().list(max: max, offset: params.offset) { query }
            respond "entities": list, "total": Usuario.count
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
    def saveObject(Usuario entityToBeSaved, String method) {
        def entity = entityToBeSaved
        def json = request.JSON
        entity.properties = json
        entity.password = springSecurityService.encodePassword(entity.password)
        try {
            entity.save(failOnError: true)
            if (metod === "PUT") {
                entity.member.properties = json
            } else {
                Member member = new Member(cpf: json.cpf, telephone: json.telephone, user: entity)
                json.companies.each { it ->
                    Company company = Company.get(it as long)
                    entity.member.addToCompanies(company)
                }
                member.save(failOnError: true)
                entity.member = member
                Autoridade autoridade = new Autoridade(authority: "ROLE_CLIENTE")
                autoridade.save(failOnError: true)
                UsuarioAutoridade ua = new UsuarioAutoridade(usuario: entity, autoridade: autoridade)
                ua.save(failOnError: true)
            }
            Map response = [nome: entity.nome, sobrenome: entity.sobrenome, email: entity.email]
            respond response
        } catch (Exception e) {
            transactionStatus.setRollbackOnly()
            respond e.message
        }
    }

    def edit() {
        saveObject(Usuario.get(request.JSON.id as long), "PUT")
    }

    def save() {
        saveObject(new Usuario(), "POST")
    }
}
