package mylotte_backend

class Member {
    Integer telephone
    String cpf
    static belongsTo = [company: Company, user: Usuario]
    static constraints = {
    }
}
