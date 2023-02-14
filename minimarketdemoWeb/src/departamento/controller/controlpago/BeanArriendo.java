package departamento.controller.controlpago;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import departamento.model.controlpagos.DTO.PagoArriendoUnico;
import departamento.model.controlpagos.managers.ManagerArriendo;
import minimarketdemo.model.core.entities.DepDepartamento;
import minimarketdemo.model.core.entities.DepNombrePago;
import minimarketdemo.model.core.entities.SegUsuario;

@Named
@SessionScoped
public class BeanArriendo implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerArriendo managerArriendo;

	private List<DepNombrePago> nombresPagos;
	private int dnpId;
	private List<SegUsuario> clientes;
	private int idCliente;
	private List<DepDepartamento> depas;
	private int depId;	
	

	//lista para el pago unico
	private List<PagoArriendoUnico> depasPagar;
	private List<PagoArriendoUnico> depasSeleccionados;	
	double totalPagar;
	double cantidadPago;
	
	
	
	public BeanArriendo() {
	}

	// METODOS

	@PostConstruct
	public void inicializar() {
		nombresPagos = managerArriendo.findAllNombrePagos();
		clientes = managerArriendo.findAllClientes();
		depasSeleccionados = new ArrayList<PagoArriendoUnico>();	
		totalPagar = managerArriendo.totalDepasSeleccionados(depasSeleccionados);
		if (clientes.size() == 0) {
			depas = new ArrayList<DepDepartamento>();
			depasPagar = new ArrayList<PagoArriendoUnico>();
		} else {
			depas = managerArriendo.findDepartamentobyIdCliente(clientes.get(0).getIdSegUsuario());
			depasPagar = managerArriendo.calcularDatosArriendoUnico(depas);
		}
	}

	// METODOS DE RUTAS

	public String actionMenu(String ruta) {
		return ruta + "?faces-redirect=true";
	}

	public String actionMenuUnPago() {
		return "arriendo";
	}

	public String actionMenuMultiplePago() {
		return "pagomultiple";
	}

	public String actionMenuAbono() {
		return "abono";
	}

	//METODOS PARA EL ARRIENDO DE UN PAGO
	
	public void actionListenerBuscarDepas() {
		depas = managerArriendo.findDepartamentobyIdCliente(idCliente);
		depasPagar = managerArriendo.calcularDatosArriendoUnico(depas);
		depasSeleccionados = new ArrayList<PagoArriendoUnico>();
	}
	
	public void actionListenerAgregarDepa(PagoArriendoUnico depaSeleccionado) {
		depasSeleccionados = managerArriendo.añadirDepartamentoPago(depasSeleccionados, depaSeleccionado);
		totalPagar = managerArriendo.totalDepasSeleccionados(depasSeleccionados);
	}
	
	public void actionListenerEliminarDepa(PagoArriendoUnico depaSeleccionado) {
		depasSeleccionados = managerArriendo.eliminarDepartamentoPago(depasSeleccionados, depaSeleccionado);
		totalPagar = managerArriendo.totalDepasSeleccionados(depasSeleccionados);
	}	
	
	public double totalPagar() {
		return managerArriendo.totalDepasSeleccionados(depasSeleccionados);
	}
	

	// GETTER AND SETTER

	public List<SegUsuario> getClientes() {
		return clientes;
	}

	public void setClientes(List<SegUsuario> clientes) {
		this.clientes = clientes;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public List<DepDepartamento> getDepas() {
		return depas;
	}

	public void setDepas(List<DepDepartamento> depas) {
		this.depas = depas;
	}

	public int getDepId() {
		return depId;
	}

	public void setDepId(int depId) {
		this.depId = depId;
	}

	public List<DepNombrePago> getNombresPagos() {		
		return nombresPagos;
	}

	public void setNombresPagos(List<DepNombrePago> nombresPagos) {
		this.nombresPagos = nombresPagos;
	}

	public int getDnpId() {
		return dnpId;
	}

	public void setDnpId(int dnpId) {
		this.dnpId = dnpId;
	}

	public List<PagoArriendoUnico> getDepasPagar() {
		return depasPagar;
	}

	public void setDepasPagar(List<PagoArriendoUnico> depasPagar) {
		this.depasPagar = depasPagar;
	}

	public List<PagoArriendoUnico> getDepasSeleccionados() {
		return depasSeleccionados;
	}

	public void setDepasSeleccionados(List<PagoArriendoUnico> depasSeleccionados) {
		this.depasSeleccionados = depasSeleccionados;
	}

	public double getTotalPagar() {
		return totalPagar;
	}

	public void setTotalPagar(double totalPagar) {
		this.totalPagar = totalPagar;
	}

	public double getCantidadPago() {
		return cantidadPago;
	}

	public void setCantidadPago(double cantidadPago) {
		this.cantidadPago = cantidadPago;
	}	
	
	public void actionListenerPagarArriendoUnico() {
		
	}
}

