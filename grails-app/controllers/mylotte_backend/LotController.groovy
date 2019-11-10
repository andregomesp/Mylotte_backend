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

    def query = {}

    def index() {
        if (params.id) {
            respond Lot.get(params.id as long)
        } else {
            def max = Math.min(params.max ?: 10, 100)
            List<Lot> list = Lot.createCriteria().list(max: max, offset: params.offset) {query}
            def new_list = []
            list.each { lot -> 
                Map el = [:]
                el.currentQuantity = lot.currentQuantity
                el.totalQuantity = lot.totalQuantity
                el.ownerCompany = [:]
                el.ownerCompany["id"] = lot.ownerCompany.id
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
            println(new_list)
            respond "entities": new_list, "total": Lot.count
        }
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
            println(user.member)
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
