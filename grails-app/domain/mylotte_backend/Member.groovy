package mylotte_backend

class Member {
    Integer telephone
    String cpf
    User user
    static hasMany = [companies: Company]
    static belongsTo = [Company]
    static constraints = {
    }
}
