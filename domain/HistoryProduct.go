package domain
import "time"
type HistoryProduct struct {
	id uint64
	registerDate time.Time
	price float32
	Product Product
}
