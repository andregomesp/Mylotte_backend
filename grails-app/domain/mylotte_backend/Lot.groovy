package mylotte_backend

class Lot {
    String typeOfLot
    Integer currentQuantity
    Integer totalQuantity
    Float unitPrice
    Float totalPrice
    String status
    Date openingDate
    Date closingDate
    Date expirationDate
    Boolean isPriceBalanced
    Product product
    static belongsTo = [ownerCompany: Company] //lot owner
    static constraints = {
    }

    static mapping = {
    }

    static def toJsonLot(def list) {
        def new_list = []
        list.each { lot ->
            Map el = [:]
            def ownedQuantity = 0
            List<LotCompany> lotCompanies = LotCompany.findAllByLot(lot as Lot)
            lotCompanies.each {
                ownedQuantity += it.offeredQuantity
            }
            el.id = lot.id
            el.ownedQuantity = ownedQuantity
            el.currentQuantity = lot.currentQuantity
            el.totalQuantity = lot.totalQuantity
            el.ownerCompany = [:]
            el.ownerCompany["id"] = lot.ownerCompany.id as Long
            el.ownerCompany["socialName"] = lot.ownerCompany.socialName as String
            el.unitPrice = lot.unitPrice
            el.totalPrice = lot.totalPrice
            el.product = [:]
            el.product["name"] = lot.product.name as String
            el.product["manufacturer"] = lot.product.manufacturer as String
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

    static def toJsonMyOwnLot(def list) {
        def new_list = []
        list.each { lotCompany ->
            Map el = [:]
            el.id = lotCompany.id
            el.offeredPrice = lotCompany.offeredPrice
            el.offeredQuantity = lotCompany.offeredQuantity
            el.ownerCompany = [:]
            el.ownerCompany["id"] = lotCompany.lot.ownerCompany.id as Long
            el.ownerCompany["socialName"] = lotCompany.lot.ownerCompany.socialName as String
            el.product = [:]
            el.product["name"] = lotCompany.lot.product.name as String
            el.product["manufacturer"] = lotCompany.lot.product.manufacturer as String
            new_list.add(el)
        }
        return new_list
    }

    static def toJsonVendasLot(def list) {
        def new_list = []
        list.each { lot ->
            Map el = [:]
            List<LotCompany> lotCompanies = LotCompany.findAllByLot(lot as Lot)
            def interestedCompanies = []
            lotCompanies.each { lc ->
                def interestedCompany = [:]
                interestedCompany["name"] = lc.company.socialName
                interestedCompany["offeredPrice"] = lc.offeredPrice
                interestedCompanies.add(interestedCompany)
            }
            el.id = lot.id
            el.currentQuantity = lot.currentQuantity
            el.totalQuantity = lot.totalQuantity
            el.ownerCompany = [:]
            el.ownerCompany["id"] = lot.ownerCompany.id as Long
            el.ownerCompany["socialName"] = lot.ownerCompany.socialName as String
            el.unitPrice = lot.unitPrice
            el.totalPrice = lot.totalPrice
            el.product = [:]
            el.product["name"] = lot.product.name as String
            el.product["manufacturer"] = lot.product.manufacturer as String
            el.openingDate = lot.openingDate
            el.closingDate = lot.closingDate
            el.expirationDate = lot.expirationDate
            el.status = lot.status
            el.typeOfLot = lot.typeOfLot
            el.interestedCompanies = interestedCompanies
            new_list.add(el)
        }
        return new_list
    }
}
