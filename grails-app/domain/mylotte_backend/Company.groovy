package mylotte_backend

class Company {
    String cnpj
    String cep
    Integer telephone
    String companyName
    String socialName
    String mainAddress
    static hasMany = [members: Member]

    static constraints = {
    }
}
