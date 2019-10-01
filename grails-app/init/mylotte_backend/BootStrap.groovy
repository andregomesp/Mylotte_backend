package mylotte_backend

import javax.transaction.Transactional
import java.time.LocalDate

class BootStrap {

    def init = { servletContext ->
        Lot lot = new Lot(
                typeOfLot: "COMPRA",
                currentQuantity: 100,
                totalQuantity: 250,
                unitPrice: 15.30,
                totalPrice: 3825,
                status: "ATIVO",
                openingDate: LocalDate.now(),
                closingDate: LocalDate.now().plusDays(25),
                expirationDate: LocalDate.now().plusDays(30),
                isPriceBalanced: false
        ).save()
    }
    def destroy = {

    }
}
