package departamento.model.controlpagos.managers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.TypedQuery;

import departamento.model.controlpagos.DTO.PagoArriendoUnico;
import minimarketdemo.model.apartment.managers.ManagerApartment;
import minimarketdemo.model.core.entities.DepArriendoCabecera;
import minimarketdemo.model.core.entities.DepArriendoDetalle;
import minimarketdemo.model.core.entities.DepClienteDepartamento;
import minimarketdemo.model.core.entities.DepDepartamento;
import minimarketdemo.model.core.entities.DepDepartamentoServicio;
import minimarketdemo.model.core.entities.DepNombrePago;
import minimarketdemo.model.core.entities.DepServicio;
import minimarketdemo.model.core.entities.SegUsuario;
import minimarketdemo.model.core.managers.ManagerDAO;

import java.util.Date;
import java.util.GregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Session Bean implementation class ManagerArriendo
 */
@Stateless
@LocalBean
public class ManagerArriendo {

	@EJB
	private ManagerDAO mDAO;
	
	@EJB
	private ManagerApartment ma;
	
	Table tabla=DepArriendoCabecera.class.getAnnotation(Table.class);

	@PersistenceContext
	EntityManager em;

	public ManagerArriendo() {
	}

	public List<DepArriendoCabecera> findCabeceras() {
		TypedQuery<DepArriendoCabecera> cab = em.createQuery("select c from DepArriendoCabecera c",
				DepArriendoCabecera.class);
		return cab.getResultList();
	}

	public List<DepArriendoCabecera> findCabecerasByDepId(int depId) {
		List<DepArriendoCabecera> datos = findAllCabeceras();
		List<DepArriendoCabecera> filtrado = new ArrayList<DepArriendoCabecera>();

		for (DepArriendoCabecera da : datos) {			
			if (da.getDepDepartamento().getDepId() == depId) {
				filtrado.add(da);
			}
		}
		return filtrado;
	}

	public List<SegUsuario> findAllClientes() {
		TypedQuery<SegUsuario> clientes = em
				.createQuery("select c from SegUsuario c where c.activo=:activo order by c.codigo", SegUsuario.class);
		boolean activo = false;
		clientes.setParameter("activo", activo);
		return clientes.getResultList();
	}

	public List<SegUsuario> findClienteByidCliente(int idSegUsuario) {
		TypedQuery<SegUsuario> clientes = em.createQuery(
				"select c from SegUsuario c where c.idSegUsuario=:idSegUsuario order by c.codigo", SegUsuario.class);
		clientes.setParameter("idSegUsuario", idSegUsuario);
		return clientes.getResultList();
	}

	public List<DepClienteDepartamento> findAllClienteDepartamento() {
		TypedQuery<DepClienteDepartamento> datos= em.createQuery("select c from DepClienteDepartamento c", DepClienteDepartamento.class);
		return datos.getResultList();
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

	public int calcularHabitantesDepartament(int depId) {
		List<DepClienteDepartamento> lista = findAllClienteDepartamento();
		int cont = 0;
		for (DepClienteDepartamento li : lista) {
			if (li.getDepDepartamento().getDepId() == depId) {
				cont++;
			}
		}	
		return cont;
	}
	
	public List<PagoArriendoUnico> calcularDatosArriendoUnico(List<DepDepartamento> depas) throws ParseException {

		int depId;
		Date fechaPago;

		List<PagoArriendoUnico> datos = new ArrayList<PagoArriendoUnico>();

		for (DepDepartamento dep : depas) {

			PagoArriendoUnico dato = new PagoArriendoUnico();
			dato.setDepId(dep.getDepId());
			dato.setDepPrecio(dep.getDepPrecio());
			String fechaTexto = calcularFechaPago(dep);
			fechaPago = new SimpleDateFormat("dd/MM/yyyy").parse(fechaTexto);
			dato.setLimFechaPago(fechaPago);
			// calcular numero de Personas de un departamento
			depId = dep.getDepId();
			TypedQuery<DepClienteDepartamento> numClientes = em.createQuery(
					"Select r from DepClienteDepartamento r where r.depDepartamento.depId=:depId",
					DepClienteDepartamento.class);
			numClientes.setParameter("depId", depId);
			dato.setNumClientes(numClientes.getResultList().size());
			dato.setSerPrecio(calcularTotalServicios(findAllServiciosDepartamentoById(depId)));
			dato.setTotal((dato.getDepPrecio() * calcularHabitantesDepartament(depId)) + dato.getSerPrecio());
			datos.add(dato);
		}
		return datos;
	}

	private String calcularFechaPago(DepDepartamento dep) {
		List<DepArriendoCabecera> cab = findCabecerasByDepId(dep.getDepId());
		int anio = 0;
		int mes = 0;
		for (DepArriendoCabecera c : cab) {

			String cadenaFecha = c.getDacFechaFin().toString();
			String[] arregloFecha = cadenaFecha.split("-");
			int auxanio = Integer.parseInt(arregloFecha[0]);
			int auxmes = Integer.parseInt(arregloFecha[1]);

			if (auxanio > anio) {
				anio = auxanio;
			}

			if (auxmes > mes) {
				mes = auxmes;
			}
		}

		if (mes == 0 && anio == 0) {
			Calendar fecha = new GregorianCalendar();
			anio = fecha.get(Calendar.YEAR);
			mes = fecha.get(Calendar.MONTH);
		}

		mes++;
		if (mes == 13) {
			mes = 1;
			anio++;
		}
		
		return dep.getDepDiaPago() + "/" + mes + "/" + anio;
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
		ma.getDatosAuditoria("insertar", tabla);
		return lista;
	}

	public List<PagoArriendoUnico> eliminarDepartamentoPago(List<PagoArriendoUnico> lista, PagoArriendoUnico depa) {
		for (PagoArriendoUnico li : lista) {
			if (li.getDepId() == depa.getDepId()) {
				lista.remove(li);
				ma.getDatosAuditoria("eliminar", tabla);
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

	public void pagarPagoUnico(List<PagoArriendoUnico> lista, double pago, int idSegUsuario, int dnpId) {
		Date fechaActual = new Date();
		for (PagoArriendoUnico depa : lista) {
			if (pago > 0) {
				DepArriendoCabecera cab = new DepArriendoCabecera();

				// List<SegUsuario> cliente = findClienteByidCliente(idSegUsuario);
				SegUsuario cli = em.find(SegUsuario.class, idSegUsuario);
				cab.setCliNombres(cli.getNombres() + " " + cli.getApellidos());
				cab.setCliCedula(cli.getClave());

				DepDepartamento dep = em.find(DepDepartamento.class, depa.getDepId());
				cab.setDepDepartamento(dep);

				DepNombrePago dpn = em.find(DepNombrePago.class, dnpId);
				cab.setDepNombrePago(dpn);

				cab.setDacTotalPago(depa.getTotal());

				// pago

				if (depa.getTotal() >= pago) {
					cab.setDacPagoRestante(depa.getTotal() - pago);
				} else {
					cab.setDacPagoRestante(0);
				}

				cab.setDacMesesPagados(1);
				cab.setDacFechaLimite(depa.getLimFechaPago());
				cab.setDacFechaInicio(fechaActual);
				cab.setDacFechaFin(fechaActual);

				List<DepArriendoDetalle> detalles = new ArrayList<DepArriendoDetalle>();
				cab.setDepArriendoDetalles(detalles);

				DepArriendoDetalle det = new DepArriendoDetalle();
				det.setDadCantidad(pago);
				pago = pago - depa.getTotal();
				det.setDadFechaPago(fechaActual);
				det.setDepArriendoCabecera(cab);

				em.persist(cab);
				em.persist(det);
			}
		}
	}

	// ARRIENDO MULTIPLE

	public List<PagoArriendoUnico> calcularDatosArriendoMultiple(List<DepDepartamento> depas, int numMeses)
			throws ParseException {

		int depId;
		List<PagoArriendoUnico> datos = new ArrayList<PagoArriendoUnico>();

		for (DepDepartamento dep : depas) {

			PagoArriendoUnico dato = new PagoArriendoUnico();
			dato.setDepId(dep.getDepId());
			dato.setDepPrecio(dep.getDepPrecio());
			// fecha
			String fechaTexto = calcularFechaPago(dep);
			Date fechaPago = new SimpleDateFormat("dd/MM/yyyy").parse(fechaTexto);
			dato.setLimFechaPago(fechaPago);
			// calcular numero de Personas de un departamento
			depId = dep.getDepId();
			TypedQuery<DepClienteDepartamento> numClientes = em.createQuery(
					"Select r from DepClienteDepartamento r where r.depDepartamento.depId=:depId",
					DepClienteDepartamento.class);
			numClientes.setParameter("depId", depId);
			dato.setNumClientes(numClientes.getResultList().size());
			dato.setSerPrecio(calcularTotalServicios(findAllServiciosDepartamentoById(depId)));
			dato.setTotal((dato.getDepPrecio() + dato.getSerPrecio()) * numMeses);
			datos.add(dato);
		}
		return datos;
	}

	// PARA LOS ABONOS

	public List<DepArriendoCabecera> findAllCabeceras() {
		TypedQuery<DepArriendoCabecera> cab = em.createQuery("Select r from DepArriendoCabecera r",
				DepArriendoCabecera.class);
		return cab.getResultList();
	}
	
	public List<DepArriendoCabecera> findAllRegistrosArriendo() {
		List<DepArriendoCabecera> lista = findAllCabeceras();
		List<DepArriendoCabecera> datos = new ArrayList<DepArriendoCabecera>();
		for (DepArriendoCabecera li : lista) {
			if (li.getDepNombrePago().getDnpId() == 1) {
				datos.add(li);
			}
		}
		return datos;
	}

	public List<DepArriendoCabecera> findListaDeudas() {
		List<DepArriendoCabecera> lista = findAllCabeceras();
		List<DepArriendoCabecera> deudas = new ArrayList<DepArriendoCabecera>();

		for (DepArriendoCabecera li : lista) {
			if (li.getDacPagoRestante() > 0 && li.getDepNombrePago().getDnpId() == 1) {
				deudas.add(li);
			}
		}
		return deudas;
	}
	
	public void insertarAbono(DepArriendoCabecera depArriendoCabecera, double dadCantidad) {
		DepArriendoDetalle abono = new DepArriendoDetalle();
		depArriendoCabecera.setDacPagoRestante(depArriendoCabecera.getDacPagoRestante() -  dadCantidad);
		depArriendoCabecera.setDacFechaFin(new Date());
		em.merge(depArriendoCabecera);		
		abono.setDadCantidad(dadCantidad);
		abono.setDadFechaPago(new Date());
		abono.setDepArriendoCabecera(depArriendoCabecera);
		em.persist(abono);
		ma.getDatosAuditoria("insertar", tabla);
		
	}
}
