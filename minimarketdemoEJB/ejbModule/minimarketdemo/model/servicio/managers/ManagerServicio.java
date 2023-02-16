package minimarketdemo.model.servicio.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import minimarketdemo.model.apartment.managers.ManagerApartment;
import minimarketdemo.model.core.entities.DepDepartamento;
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
	
	@EJB
	private ManagerApartment ma;
	
	public ManagerServicio() {
		// TODO Auto-generated constructor stub
	}

	public void insertarServicio(String descripcion, double precio) throws Exception {

		DepServicio nuevoServicio = new DepServicio();
		nuevoServicio.setSerDescripcion(descripcion);
		nuevoServicio.setSerPrecioBase(precio);
		// em.persist(nuevoServicio);
		mDAO.insertar(nuevoServicio);
		ma.getDatosAuditoria("insertar");
	}

	public List<DepServicio> findAllServicio() {
		return mDAO.findAll(DepServicio.class, "serId");
	}

	public void actualizarServicio(DepServicio edicionServicio) throws Exception {
		DepServicio servicio = (DepServicio) mDAO.findById(DepServicio.class, edicionServicio.getSerId());
		servicio.setSerDescripcion(edicionServicio.getSerDescripcion());
		servicio.setSerPrecioBase(edicionServicio.getSerPrecioBase());
		mDAO.actualizar(servicio);
		ma.getDatosAuditoria("actualizar");
	}

	public void eliminarServicio(int idDepServicio) throws Exception {
		DepServicio servicio = (DepServicio) mDAO.findById(DepServicio.class, idDepServicio);
		if (servicio.getSerId() == 1)
			throw new Exception("No se puede eliminar el servicio administrador.");
		if (servicio.getDepDepartamentoServicios().size() > 0)
			throw new Exception("No se puede elimininar el servicio porque tiene asignaciones de módulos.");
		mDAO.eliminar(DepServicio.class, servicio.getSerId());
		ma.getDatosAuditoria("eliminar");
		// TODO agregar uso de LoginDTO para auditar metodo.
	}

	public int calcularCostoTotal(List<DTODepServicio> puntajes) {
		int total = 0;
		for (DTODepServicio puntos : puntajes) {
			total += puntos.getSer_precio_base();
		}
		return total;
	}

	public List<DepDepartamento> findDepaById(int id) {
		TypedQuery<DepDepartamento> c = em.createQuery("select d from DepDepartamento d where d.depId",
				DepDepartamento.class);
		c.setParameter("depId", id);
		return c.getResultList();
	}

	/*
	 * public List<DepDepartamento> findAllDepartamento() { return
	 * mDAO.findAll(DepDepartamento.class, "depId"); }
	 */

	/*
	 * public DTODepServicio crearDeparServi(int id, String observacion) {
	 * DepServicio ds= em.find(DepServicio.class, id);
	 * 
	 * DTODepServicio detalle = new DTODepServicio(); detalle.setSer_id(id);
	 * detalle.setSer_descripcion(ds.getSerDescripcion());
	 * detalle.setSer_precio_base(puntaje);
	 * 
	 * return detalle; }
	 */

	/*
	 * public void guardarDatos(int depId, List<DepServicio> listaServicio) { //
	 * guardar en datos en la tabla de mantenimiento DepDepartamento departamento =
	 * em.find(DepDepartamento.class, depId); DepServicio ds=new DepServicio();
	 * ds.getDepDepartamentoServicios(); ds.getSerDescripcion();
	 * ds.getSerPrecioBase(calcularCostoTotal(listaServicio));
	 * 
	 * List<DetalleMantenimiento> listaDetalle = new
	 * ArrayList<DetalleMantenimiento>();
	 * manteni.setDetalleMantenimientos(listaDetalle);
	 * 
	 * // para luego guardar en la tablade detalle mantenimiento for (DTODepServicio
	 * det : listaDetalles) { DetalleMantenimiento mantenidet = new
	 * DetalleMantenimiento(); mantenidet.setMantenimiento(manteni); Trabajo
	 * caracteristica = em.find(Trabajo.class, det.getId_trabajo());
	 * mantenidet.setTrabajo(caracteristica);
	 * mantenidet.setObservacion(det.getObservacion());
	 * listaDetalle.add(mantenidet); } em.persist(manteni); }
	 */

}
