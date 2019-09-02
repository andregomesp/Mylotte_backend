package domain

type Company struct {
	id uint64
	cnpj string
	cep string
	telephone uint32 // Nullable
	companyName string
	socialName string
	mainAddress string // Nullable
}