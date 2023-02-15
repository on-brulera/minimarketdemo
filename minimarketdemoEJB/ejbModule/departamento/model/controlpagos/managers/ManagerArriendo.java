package departamento.model.controlpagos.managers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import departamento.model.controlpagos.DTO.PagoArriendoUnico;
import minimarketdemo.model.core.entities.DepClienteDepartamento;
import minimarketdemo.model.core.entities.DepDepartamento;
import minimarketdemo.model.core.entities.DepDepartamentoServicio;
import minimarketdemo.model.core.entities.DepNombrePago;
import minimarketdemo.model.core.entities.DepServicio;
import minimarketdemo.model.core.entities.SegUsuario;
import minimarketdemo.model.core.managers.ManagerDAO;

import java.util.Date;

/**
 * Session Bean implementation class ManagerArriendo
 */
@Stateless
@LocalBean
public class ManagerArriendo {

	@EJB
	private ManagerDAO mDAO;

	@PersistenceContext
	EntityManager em;

	public ManagerArriendo() {
	}

	public List<SegUsuario> findAllClientes() {
		TypedQuery<SegUsuario> clientes = em
				.createQuery("select c from SegUsuario c where c.activo=:activo order by c.codigo", SegUsuario.class);
		boolean activo = false;
		clientes.setParameter("activo", activo);
		return clientes.getResultList();
	}

	public List<DepDepartamento> findDepartamentobyIdCliente(int idSegUsuario) {
		// buscando relaciones
		TypedQuery<DepClienteDepartamento> relacion = em.createQuery(
				"Select r from DepClienteDepartamento r where r.idSegUsuario=:idSegUsuario",
				DepClienteDepartamento.class);
		relacion.setParameter("idSegUsuario", idSegUsuario);
		List<DepClienteDepartamento> lista = relacion.getResultList();
		// buscando departamentos
		List<DepDepartamento> depas = new ArrayList<DepDepartamento>();

		for (DepClienteDepartamento l : lista) {
			// TypedQuery<DepDepartamento> depa = em.createQuery("", DepDepartamento.class);
			depas.add(em.find(DepDepartamento.class, l.getDepDepartamento().getDepId()));
		}
		return depas;
	}

	public List<DepNombrePago> findAllNombrePagos() {
		@SuppressWarnings("unchecked")
		List<DepNombrePago> nombres = mDAO.findAll(DepNombrePago.class, "dnpDescripcion");
		return nombres;
	}

	public List<PagoArriendoUnico> calcularDatosArriendoUnico(List<DepDepartamento> depas) {

		int depId;	
		Date fechaActual = new Date();
		List<PagoArriendoUnico> datos = new ArrayList<PagoArriendoUnico>();

		for (DepDepartamento dep : depas) {

			PagoArriendoUnico dato = new PagoArriendoUnico();
			dato.setDepId(dep.getDepId());
			dato.setDepPrecio(dep.getDepPrecio());
			dato.setLimFechaPago(fechaActual);
			// calcular numero de Personas de un departamento
			depId = dep.getDepId();
			TypedQuery<DepClienteDepartamento> numClientes = em.createQuery(
					"Select r from DepClienteDepartamento r where r.depDepartamento.depId=:depId",
					DepClienteDepartamento.class);
			numClientes.setParameter("depId", depId);
			dato.setNumClientes(numClientes.getResultList().size());
			dato.setSerPrecio(calcularTotalServicios(findAllServiciosDepartamentoById(depId)));
			dato.setTotal(dato.getDepPrecio() + dato.getSerPrecio());
			datos.add(dato);
		}
		return datos;
	}

	private double calcularTotalServicios(List<DepDepartamentoServicio> servicios) {
		double total = 0;
		for (DepDepartamentoServicio ser : servicios) {
			total += (findServiceById(ser.getDepServicio().getSerId())).getSerPrecioBase();
		}
		return total;
	}

	public List<DepDepartamentoServicio> findAllServiciosDepartamentoById(int depId) {
		TypedQuery<DepDepartamentoServicio> servicios = em.createQuery(
				"Select s from DepDepartamentoServicio s where s.depDepartamento.depId=:depId",
				DepDepartamentoServicio.class);
		servicios.setParameter("depId", depId);
		return servicios.getResultList();
	}

	public DepServicio findServiceById(int serId) {
		DepServicio servicio = em.find(DepServicio.class, serId);
		return servicio;
	}

	public List<PagoArriendoUnico> añadirDepartamentoPago(List<PagoArriendoUnico> lista, PagoArriendoUnico depa) {
		for (PagoArriendoUnico li : lista) {
			if (li.getDepId() == depa.getDepId()) {				
				return lista;
			}
		}
		lista.add(depa);
		return lista;
	}
	
	public List<PagoArriendoUnico> eliminarDepartamentoPago(List<PagoArriendoUnico> lista, PagoArriendoUnico depa) {
		for (PagoArriendoUnico li : lista) {
			if (li.getDepId() == depa.getDepId()) {
				lista.remove(li);
				break;
			}
		}
		return lista;
	}
	
	public double totalDepasSeleccionados(List<PagoArriendoUnico> lista) {
		double total = 0;
		for (PagoArriendoUnico pagoArriendoUnico : lista) {
			total += pagoArriendoUnico.getTotal();
		}
		return total;
	}
}
