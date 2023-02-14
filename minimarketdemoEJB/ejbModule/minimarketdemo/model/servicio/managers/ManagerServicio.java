package minimarketdemo.model.servicio.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import minimarketdemo.model.core.entities.DepServicio;
import minimarketdemo.model.core.managers.ManagerDAO;

/**
 * Session Bean implementation class ManagerServicio
 */
@Stateless
@LocalBean
public class ManagerServicio {

	@EJB
	private ManagerDAO mDAO;
	
	@PersistenceContext
	private EntityManager em;
	
    public ManagerServicio() {
        // TODO Auto-generated constructor stub
    }
    
    public void insertarServicio(String descripcion,double precio) throws Exception {
    	
    	DepServicio nuevoServicio=new DepServicio();
    	nuevoServicio.setSerDescripcion(descripcion);
    	nuevoServicio.setSerPrecioBase(precio);
    	//em.persist(nuevoServicio);
    	mDAO.insertar(nuevoServicio);
    }
    
    public List<DepServicio> findAllServicio(){
    	return mDAO.findAll(DepServicio.class, "serId");
    }
    
    public void actualizarServicio(DepServicio edicionServicio) throws Exception {
    	DepServicio servicio=(DepServicio) mDAO.findById(DepServicio.class, edicionServicio.getSerId());
    	servicio.setSerDescripcion(edicionServicio.getSerDescripcion());
    	servicio.setSerPrecioBase(edicionServicio.getSerPrecioBase());
    	mDAO.actualizar(servicio);
    }
    
    public void eliminarServicio(int idDepServicio) throws Exception {
    	DepServicio servicio=(DepServicio) mDAO.findById(DepServicio.class, idDepServicio);
    	if(servicio.getSerId()==1)
    		throw new Exception("No se puede eliminar el servicio administrador.");
    	if(servicio.getDepDepartamentoServicios().size()>0)
    		throw new Exception("No se puede elimininar el servicio porque tiene asignaciones de módulos.");
    	mDAO.eliminar(DepServicio.class, servicio.getSerId());
    	//TODO agregar uso de LoginDTO para auditar metodo.
    }

}
