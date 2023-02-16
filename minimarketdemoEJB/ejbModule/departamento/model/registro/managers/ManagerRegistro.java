package departamento.model.registro.managers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import minimarketdemo.model.apartment.managers.ManagerApartment;
import minimarketdemo.model.core.entities.SegUsuario;

@Stateless
@LocalBean
public class ManagerRegistro {
	@PersistenceContext
	private EntityManager em;
	

	@EJB
	private ManagerApartment ma=new ManagerApartment();
	
	public ManagerRegistro(){
		
	}
	
	public List<SegUsuario> findAllCliente() {
        final TypedQuery<SegUsuario> q = (TypedQuery<SegUsuario>)this.em.createQuery("select c from SegUsuario c order by c.idSegUsuario", (Class)SegUsuario.class);
        return filtrarCliente((List<SegUsuario>)q.getResultList());
    }
	
	public List<SegUsuario> filtrarCliente(List<SegUsuario> lista) {
		List<SegUsuario> datos = new ArrayList<SegUsuario>();		
		for (SegUsuario li : lista) {
			if (li.getActivo() == false) {
				datos.add(li);
			}
		}
		return datos;
	}
	
	 public SegUsuario findClienteById(int id) {
	    	return em.find(SegUsuario.class, id);
	 }
	
	
	public SegUsuario CrearCliente(String codigo,String apellidos, String nombres,String correo) {
		SegUsuario cliente= new SegUsuario();
		cliente.setCodigo(codigo);
		cliente.setApellidos(apellidos);
		cliente.setNombres(nombres);
		cliente.setCorreo(correo);
		cliente.setClave("No asignado");
		cliente.setActivo(false);
		
		em.persist(cliente);
		ma.getDatosAuditoria("insertar");
		
    	return cliente;
	}
	
	public SegUsuario  eliminarcliente(final int id) {
		final SegUsuario cliente=(SegUsuario)this.em.find((Class)SegUsuario.class, (Object)id);
    	if(cliente !=null)
    		this.em.remove((Object)cliente);
    		ma.getDatosAuditoria("eliminar");
    	return cliente;
    }

    
    
    public SegUsuario actualizarcliente(SegUsuario c) {
    	SegUsuario cliente=em.find(SegUsuario.class, c.getIdSegUsuario());
    	cliente.setCodigo(c.getCodigo());
		cliente.setApellidos(c.getApellidos());
		cliente.setNombres(c.getNombres());
		cliente.setCorreo(c.getCorreo());
    	em.merge(cliente);
		ma.getDatosAuditoria("actualizar");
    	return cliente;
    }

}
