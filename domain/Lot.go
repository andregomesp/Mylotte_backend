package domain
import "time"

type Lot struct {
	id uint64
	typeOfLot string
	currentQuantity string
	totalQuantity string
	unitPrice string
	totalPrice string
	status string
	openingDate time.Time
	closingDate time.Time
	expirationDate time.Time
	isPriceBalanced bool
}