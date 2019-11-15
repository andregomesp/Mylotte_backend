package mylotte_backend

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService
import mylotte_backend.Company
import mylotte_backend.Lot
import mylotte_backend.LotCompany
import java.text.SimpleDateFormat
@Transactional
class LotController {
    SpringSecurityService springSecurityService
    static responseFormats = ['json', 'xml']

    def index() {
        def query = {}
        if (params.id) {
            respond Lot.get(params.id as long)
        } else {
            def max = Math.min(params.max ?: 10, 100)
            List<Lot> list = Lot.createCriteria().list(max: max, offset: params.offset) {query} as List<Lot>
            def new_list = toJsonLot(list)
            respond "entities": new_list, "total": Lot.count
        }
    }

    def getMyLottes() {
        Usuario user = springSecurityService.getCurrentUser() as Usuario
        def max = Math.min(params.max ?: 10, 100)
        List<LotCompany> list = LotCompany.createCriteria().list(max: max, offset: params.offset) {
            company {
                eq("id", user.member.company.id)
            }
        } as List<LotCompany>
        def new_list = toJsonMyOwnLot(list)
        respond "entities": new_list
    }

    def toJsonLot(def list) {
        def new_list = []
        list.each { lot ->
            Map el = [:]
            def ownedQuantity = 0
            List<LotCompany> lotCompanies = LotCompany.findAllByLot(lot)
            lotCompanies.each {
                ownedQuantity += it.offeredQuantity
            }
            el.id = lot.id
            el.ownedQuantity = ownedQuantity
            el.currentQuantity = lot.currentQuantity
            el.totalQuantity = lot.totalQuantity
            el.ownerCompany = [:]
            el.ownerCompany["id"] = lot.ownerCompany.id
            el.ownerCompany["socialName"] = lot.ownerCompany.socialName
            el.unitPrice = lot.unitPrice
            el.totalPrice = lot.totalPrice
            el.product = [:]
            el.product["name"] = lot.product.name
            el.product["manufacturer"] = lot.product.manufacturer
            el.openingDate = lot.openingDate
            el.closingDate = lot.closingDate
            el.expirationDate = lot.expirationDate
            el.status = lot.status
            el.isPriceBalanced = lot.isPriceBalanced
            el.typeOfLot = lot.typeOfLot
            new_list.add(el)
        }
        return new_list
    }

    def toJsonMyOwnLot(def list) {
        def new_list = []
        list.each { lotCompany ->
            Map el = [:]
            el.id = lotCompany.id
            el.offeredPrice = lotCompany.offeredPrice
            el.offeredQuantity = lotCompany.offeredQuantity
            el.ownerCompany = [:]
            el.ownerCompany["id"] = lotCompany.lot.ownerCompany.id
            el.ownerCompany["socialName"] = lotCompany.lot.ownerCompany.socialName
            el.product = [:]
            el.product["name"] = lotCompany.lot.product.name
            el.product["manufacturer"] = lotCompany.lot.product.manufacturer
            new_list.add(el)
        }
        return new_list
    }


    def getAllInterestedCompanies() {

    }

    def delete() {
        Lot lot = Lot.get(params.id as long)
        try {
            lot.delete(failOnError: true)
            response.status = 204
            respond {}
        } catch (Exception e) {
             respond(error: e.message)
        }
    }

    def process_entity(Lot lot, def json) {
        try {
            lot.properties = json
            Usuario user = springSecurityService.getCurrentUser()
            Set<Member> members = [user.member]
            Company company = user.member.company
            lot.ownerCompany = company
            lot.totalPrice = (Float) (lot.unitPrice.toFloat() * lot.totalQuantity.toInteger())
            lot.openingDate = new SimpleDateFormat("yy-MM-dddd HH:mm:SS.SSS").parse(json.openingDate as String)
            lot.closingDate = new SimpleDateFormat("yy-MM-dddd HH:mm:SS.SSS").parse(json.closingDate as String)
            lot.expirationDate = new SimpleDateFormat("yy-MM-dddd HH:mm:SS.SSS").parse(json.expirationDate as String)
            lot.product = Product.findById(json.productId as long)
            lot.save(failOnError: true)
            company.save(failOnError: true)
            respond lot
        } catch (Exception e) {
            respond(error: e.message)
        }
    }

    def save() {
        process_entity(new Lot(), request.JSON)
    }

    def edit() {
        process_entity(Lot.get(json.id as long), request.JSON)
    }

    def enterLot() {
        def json = request.JSON
        println(json)
        Company company = Company.get(json.companyId as long)
        Lot lot = Lot.get(json.lotId as long)
        LotCompany lotCompany = new LotCompany()
        lotCompany.lot = lot
        lotCompany.company = company
        lotCompany.offeredPrice = json.offeredPrice
        lotCompany.offeredQuantity = json.offeredQuantity
        lotCompany.balancedPrice = json.offeredPrice
        if (lot.typeOfLot == "COMPRA" && (lot.currentQuantity + lotCompany.offeredQuantity > lot.totalQuantity)) {
            throw new Error("Quantidade ofertada maior que quantidade disponivel")
        }
        if (lot.status == "CLOSED") {
            throw new Error("Lote fechado")
        }
        try {
            lotCompany.save(failOnError: true)
            lot.currentQuantity = lot.currentQuantity + lotCompany.offeredQuantity
            lot.save(failOnError: true)
            respond lotCompany
        } catch (Exception e) {
             respond(error: e.message)
        }
    }

    def exitLot() {
        def json = request.JSON
        Company company = Company.get(json.companyId as long)
        Lot lot = Lot.get(json.lotId as long)
        LotCompany lotCompany = LotCompany.findByLotAndCompany(lot, company)
        try {
            lotCompany.delete(failOnError: true)
            response.status = 204
            respond {}
        } catch (Exception e) {
             respond(error: e.message)
        }
    }
}
