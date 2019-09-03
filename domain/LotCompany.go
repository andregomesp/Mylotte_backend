package domain


type LotCompany struct {
	id uint64
	offeredPrice float32
	balancedPrice float32 //Nullable
	offeredQuantity uint32
	Lot Lot
	Company Company
}

