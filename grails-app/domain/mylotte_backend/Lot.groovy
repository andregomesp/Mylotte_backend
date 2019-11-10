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
}
