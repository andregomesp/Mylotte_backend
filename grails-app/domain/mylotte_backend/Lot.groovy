package mylotte_backend

import java.time.LocalDate

class Lot {
    String typeOfLot
    String currentQuantity
    String totalQuantity
    Float unitPrice
    Float totalPrice
    String status
    LocalDate openingDate
    LocalDate closingDate
    LocalDate expirationDate
    Boolean isPriceBalanced

    static constraints = {
    }
}
