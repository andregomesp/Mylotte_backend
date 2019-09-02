package domain

type Member struct {
	id        uint64
	telephone uint32 // Nullable
	cpf       string
	UserData UserData
}
