package mylotte_backend

class Member {
    Integer telephone
    String cpf
    Usuario user
    static hasMany = [companies: Company]
    static belongsTo = [Company]
    static constraints = {
    }
}
