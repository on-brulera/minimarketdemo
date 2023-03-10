package departamento.controller.controlpago;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import departamento.model.controlpagos.DTO.PagoArriendoUnico;
import departamento.model.controlpagos.managers.ManagerArriendo;
import minimarketdemo.model.core.entities.DepArriendoCabecera;
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

	// lista para el pago unico y multiple
	private List<PagoArriendoUnico> depasPagar;
	private List<PagoArriendoUnico> depasSeleccionados;
	double totalPagar;
	double cantidadPago;

	// para el pago multiple
	private int numMeses;

	// para los abonos
	private List<DepArriendoCabecera> listaDeudas;
	DepArriendoCabecera cabDeudaSeleccionado;
	double cantidadAbono;
	
	//para los registros
	private List<DepArriendoCabecera> listaRegistro;
	
	
	public BeanArriendo() {
	}

	// METODOS

	@PostConstruct
	public void inicializar() throws ParseException {
		nombresPagos = managerArriendo.findAllNombrePagos();
		clientes = managerArriendo.findAllClientes();
		depasSeleccionados = new ArrayList<PagoArriendoUnico>();
		totalPagar = managerArriendo.totalDepasSeleccionados(depasSeleccionados);
		listaDeudas = new ArrayList<DepArriendoCabecera>();
		cabDeudaSeleccionado = new DepArriendoCabecera();
		listaRegistro = new ArrayList<DepArriendoCabecera>();
		if (clientes.size() == 0) {
			depas = new ArrayList<DepDepartamento>();
			depasPagar = new ArrayList<PagoArriendoUnico>();
		} else {
			depas = managerArriendo.findDepartamentobyIdCliente(clientes.get(0).getIdSegUsuario());
			depasPagar = managerArriendo.calcularDatosArriendoUnico(depas);
		}
	}

	public void reiniciarValores() {
		clientes = managerArriendo.findAllClientes();
		depasSeleccionados = new ArrayList<PagoArriendoUnico>();
		depasPagar = new ArrayList<PagoArriendoUnico>();
		depas = new ArrayList<DepDepartamento>();
	}

	// METODOS DE RUTAS

	public String actionMenu(String ruta) {
		return ruta + "?faces-redirect=true";
	}

	public String actionMenuUnPago() {
		reiniciarValores();
		return "arriendo";
	}

	public String actionMenuMultiplePago() {
		reiniciarValores();
		return "pagomultiple";
	}

	public String actionMenuAbono() {
		listaDeudas = managerArriendo.findListaDeudas();
		return "abono";
	}
	
	public String actionMenuRegistro() {
		listaRegistro = managerArriendo.findAllRegistrosArriendo();
		return "registros";
	}

	// METODOS PARA EL ARRIENDO DE UN PAGO

	public void actionListenerBuscarDepas() throws ParseException {
		depas = managerArriendo.findDepartamentobyIdCliente(idCliente);
		depasPagar = managerArriendo.calcularDatosArriendoUnico(depas);
		depasSeleccionados = new ArrayList<PagoArriendoUnico>();
	}

	public void actionListenerAgregarDepa(PagoArriendoUnico depaSeleccionado) {
		depasSeleccionados = managerArriendo.a??adirDepartamentoPago(depasSeleccionados, depaSeleccionado);
		totalPagar = managerArriendo.totalDepasSeleccionados(depasSeleccionados);
	}

	public void actionListenerEliminarDepa(PagoArriendoUnico depaSeleccionado) {
		depasSeleccionados = managerArriendo.eliminarDepartamentoPago(depasSeleccionados, depaSeleccionado);
		totalPagar = managerArriendo.totalDepasSeleccionados(depasSeleccionados);
	}

	public double totalPagar() {
		return managerArriendo.totalDepasSeleccionados(depasSeleccionados);
	}

	public void actionListenerPagarPagoUnico() {
		managerArriendo.pagarPagoUnico(depasSeleccionados, cantidadPago, idCliente, dnpId);
	}

	// METODOS PARA EL PAGO MULTIPLE

	public void actionListenerBuscarDepasMultiple() {
		depas = managerArriendo.findDepartamentobyIdCliente(idCliente);
	}

	public void actionListenerCalcularDatosMultiple() throws ParseException {
		depasPagar = managerArriendo.calcularDatosArriendoMultiple(depas, numMeses);
		depasSeleccionados = new ArrayList<PagoArriendoUnico>();
	}

	// METODOS PARA EL ABONO

	public void actionListenerSeleccionarDeuda(DepArriendoCabecera cab) {
		this.cabDeudaSeleccionado = cab;
	}

	public void actionListenerPagarAbono() {
		managerArriendo.insertarAbono(cabDeudaSeleccionado, cantidadAbono);
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

	public int getNumMeses() {
		return numMeses;
	}

	public void setNumMeses(int numMeses) {
		this.numMeses = numMeses;
	}

	public List<DepArriendoCabecera> getListaDeudas() {
		return listaDeudas;
	}

	public void setListaDeudas(List<DepArriendoCabecera> listaDeudas) {
		this.listaDeudas = listaDeudas;
	}

	public DepArriendoCabecera getCabDeudaSeleccionado() {
		return cabDeudaSeleccionado;
	}

	public void setCabDeudaSeleccionado(DepArriendoCabecera cabDeudaSeleccionado) {
		this.cabDeudaSeleccionado = cabDeudaSeleccionado;
	}

	public double getCantidadAbono() {
		return cantidadAbono;
	}

	public void setCantidadAbono(double cantidadAbono) {
		this.cantidadAbono = cantidadAbono;
	}

	public List<DepArriendoCabecera> getListaRegistro() {
		return listaRegistro;
	}

	public void setListaRegistro(List<DepArriendoCabecera> listaRegistro) {
		this.listaRegistro = listaRegistro;
	}
	
}
