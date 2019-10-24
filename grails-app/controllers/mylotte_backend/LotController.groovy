package mylotte_backend

import grails.plugin.springsecurity.SpringSecurityService
import mylotte_backend.Company
import mylotte_backend.Lot
import mylotte_backend.LotCompany

class LotController {
	static responseFormats = ['json', 'xml']

    def query = {}

    def index() {
        if (params.id) {
            respond Lot.get(params.id as long)
        } else {
            def max = Math.min(params.max ?: 10, 100)
            def list = Lot.createCriteria().list(max: max, offset: params.offset) {query}
            respond "entities": list, "total": Lot.count
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
            respond e.message
        }
    }

    def save() {
        def json = request.JSON
        Lot lot = new Lot()
        try {
            lot.properties = json
            Company company = Company.get(json.companyId as long)
            company.addToLots(lot)
            lot.save(failOnError: true)
            company.save(failOnError: true)
            respond lot
        } catch (Exception e) {
            respond e.message
        }
    }

    def edit() {
        def json = request.JSON
        Lot lot = Lot.get(json.id as long)
        try {
            lot.properties = json
            lot.save(failOnError: true)
            respond lot
        } catch (Exception e) {
            respond e.message
        }
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
        if (lot.typeOfLot == "COMPRA" && (lot.currentQuantity + lotCompany.offeredQuantity > lot.totalQuantity)) {
            throw new Error("Quantidade ofertada maior que quantidade disponivel")
        }
        if (lot.status == "CLOSED") {
            throw new Error("Lote fechado")
        }
        try {
            lotCompany.save(failOnError: true)
            respond lotCompany
        } catch (Exception e) {
            respond e.message
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
            respond e.message
        }
    }
}
