package mylotte_backend

class Company {
    String cnpj
    String cep
    String telephone
    String companyName
    String socialName
    String mainAddress
    static hasMany = [members: Member, lots: Lot]

    static constraints = {
    }
}
