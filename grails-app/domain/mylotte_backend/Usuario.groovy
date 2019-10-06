package mylotte_backend

class Usuario {
    String nome
    String sobrenome
    String email
    String password
    Member member
    Boolean enabled = true
    Boolean accountExpired = false
    Boolean accountLocked = false
    Boolean passwordExpired = false

    Set<Autoridade> getAuthorities() {
        (UsuarioAutoridade.findAllByUsuario(this) as List<UsuarioAutoridade>)*.autoridade as Set<Autoridade>
    }

    def getAuthoritiesMap() {
        def usuariosAutoridade = UsuarioAutoridade.findAllByUsuario(this)
        def autoridades = new ArrayList<String>()
        usuariosAutoridade.each {
            autoridades.add(it.autoridade.authority)
        }
        return autoridades
    }

    static constraints = {
        password nullable: false, blank: false, password: true
        email nullable: false, blank: false, unique: true
        member nullable: true
    }

    static mapping = {
	    password column: '`password`'
    }
}
