package departamento.model.controlpagos.managers;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import minimarketdemo.model.core.entities.SegUsuario;

/**
 * Session Bean implementation class ManagerArriendo
 */
@Stateless
@LocalBean
public class ManagerArriendo {

	@PersistenceContext
	EntityManager em;
	
    public ManagerArriendo() {
    }

    public List<SegUsuario> findAllClientes(){
    	TypedQuery<SegUsuario> clientes = em.createQuery("select c from SegUsuario c where c.activo=:activo order by c.codigo", SegUsuario.class);
    	boolean activo = false;
    	clientes.setParameter("activo", activo);
    	return clientes.getResultList();	
    }
        
}
