package mylotte_backend

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.codehaus.groovy.util.HashCodeHelper
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@ToString(cache=true, includeNames=true, includePackage=false)
class UsuarioAutoridade implements Serializable {

	private static final long serialVersionUID = 1

	Usuario usuario
	Autoridade autoridade

	@Override
	boolean equals(other) {
		if (other instanceof UsuarioAutoridade) {
			other.usuarioId == usuario?.id && other.autoridadeId == autoridade?.id
		}
	}

    @Override
	int hashCode() {
	    int hashCode = HashCodeHelper.initHash()
        if (usuario) {
            hashCode = HashCodeHelper.updateHash(hashCode, usuario.id)
		}
		if (autoridade) {
		    hashCode = HashCodeHelper.updateHash(hashCode, autoridade.id)
		}
		hashCode
	}

	static UsuarioAutoridade get(long usuarioId, long autoridadeId) {
		criteriaFor(usuarioId, autoridadeId).get()
	}

	static boolean exists(long usuarioId, long autoridadeId) {
		criteriaFor(usuarioId, autoridadeId).count()
	}

	private static DetachedCriteria criteriaFor(long usuarioId, long autoridadeId) {
		UsuarioAutoridade.where {
			usuario == Usuario.load(usuarioId) &&
			autoridade == Autoridade.load(autoridadeId)
		}
	}

	static UsuarioAutoridade create(Usuario usuario, Autoridade autoridade, boolean flush = false) {
		def instance = new UsuarioAutoridade(usuario: usuario, autoridade: autoridade)
		instance.save(flush: flush)
		instance
	}

	static boolean remove(Usuario u, Autoridade r) {
		if (u != null && r != null) {
			UsuarioAutoridade.where { usuario == u && autoridade == r }.deleteAll()
		}
	}

	static int removeAll(Usuario u) {
		u == null ? 0 : UsuarioAutoridade.where { usuario == u }.deleteAll() as int
	}

	static int removeAll(Autoridade r) {
		r == null ? 0 : UsuarioAutoridade.where { autoridade == r }.deleteAll() as int
	}

	static constraints = {
	    usuario nullable: false
		autoridade nullable: false, validator: { Autoridade r, UsuarioAutoridade ur ->
			if (ur.usuario?.id) {
				if (UsuarioAutoridade.exists(ur.usuario.id, r.id)) {
				    return ['userRole.exists']
				}
			}
		}
	}

	static mapping = {
		id composite: ['usuario', 'autoridade']
		version false
	}
}
