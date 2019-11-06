package mylotte_backend

class Lot {
    String typeOfLot
    String currentQuantity
    String totalQuantity
    Float unitPrice
    Float totalPrice
    String status
    Date openingDate
    Date closingDate
    Date expirationDate
    Boolean isPriceBalanced
    Product product

    static constraints = {
    }
}
