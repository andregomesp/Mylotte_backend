package mylotte_backend


import utils.Auditavel

class Usuario extends Auditavel {

    String username
    String nome
    String sobrenome
    String email
    String password
    String foto
    Boolean useManyPics = false
    Boolean enabled = true
    Boolean accountExpired = false
    Boolean accountLocked = false
    Boolean passwordExpired = false
    String lostPassToken
    Date lostPassCreationDate

    static transients = ['useManyPics']
    static hasMany = [dispositivos : Dispositivo]

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
        username nullable: false, blank: false, unique: true
        foto nullable: true
        lostPassToken nullable: true, blank: true
        lostPassCreationDate nullable: true
    }

    static mapping = {
	    password column: '`password`'
        dispositivos cascade: 'all-delete-orphan'
//        id generator:'sequence', params:[sequence:'usuario_seq']
    }

    def toJsonGerenciadoresList() {
        Map resposta = [
            "id": this.id,
            "nome": this.nome,
            "sobrenome": this.sobrenome,
            "email": this.email,
            "enabled": this.enabled,
            "criadoEm": this.criadoEm,
            "alteradoEm": this.alteradoEm,
            "usuarioCriacao": this.usuarioCriacao,
            "usuarioAlteracao": this.usuarioAlteracao,
            "autoridades": this.getAuthoritiesMap()
        ]
        return resposta
    }
}
