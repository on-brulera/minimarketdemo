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
import departamento.model.controlpagos.managers.ManagerGarantia;
import minimarketdemo.model.core.entities.DepArriendoCabecera;
import minimarketdemo.model.core.entities.DepDepartamento;
import minimarketdemo.model.core.entities.DepNombrePago;
import minimarketdemo.model.core.entities.SegUsuario;

@Named
@SessionScoped
public class BeanGarantia implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerGarantia managerGarantia;
	
	private List<DepNombrePago> nombresPagos;
	private int dnpId;
	private List<SegUsuario> clientes;
	private int idCliente;
	private List<DepDepartamento> depas;
	private int depId;
	private List<PagoArriendoUnico> depasPagar;
	private List<PagoArriendoUnico> depasSeleccionados;
	private double totalPagar;
	private double cantidadPago;
	
	//para abonos
	private List<DepArriendoCabecera> listaDeudas;
	private DepArriendoCabecera cabDeudaSeleccionado;
	private double cantidadAbono;	
	
	//para registros
	private List<DepArriendoCabecera> listaRegistro;
	
	public BeanGarantia() {

	}

	@PostConstruct
	public void inicializar() throws ParseException {
		nombresPagos = managerGarantia.findAllNombrePagos();
		clientes = managerGarantia.findAllClientes();
		depasSeleccionados = new ArrayList<PagoArriendoUnico>();
		listaDeudas = new ArrayList<DepArriendoCabecera>();
		listaRegistro = new ArrayList<DepArriendoCabecera>();
		if (clientes.size() == 0) {
			depas = new ArrayList<DepDepartamento>();
			depasPagar = new ArrayList<PagoArriendoUnico>();
		} else {
			depas = managerGarantia.findDepartamentobyIdCliente(clientes.get(0).getIdSegUsuario());
			depasPagar = managerGarantia.calcularDatosArriendoUnico(depas);
		}
	}
	
	//MENU RUTAS
	
	public String actionMenu(String ruta) {
		return ruta + "?faces-redirect=true";
	}
	
	public String actionMenuAbono() {
		listaDeudas = managerGarantia.findListaDeudas();
		return "abono";
	}
	
	//METODOS DE PAGO GARANTIA
	
	public void actionListenerBuscarDepas() throws ParseException {
		depas = managerGarantia.findDepartamentobyIdCliente(idCliente);
		depasPagar = managerGarantia.calcularDatosArriendoUnico(depas);
		depasSeleccionados = new ArrayList<PagoArriendoUnico>();
	}
	
	public void actionListenerAgregarDepa(PagoArriendoUnico depaSeleccionado) {
		depasSeleccionados = managerGarantia.añadirDepartamentoPago(depasSeleccionados, depaSeleccionado);
		totalPagar = managerGarantia.totalDepasSeleccionados(depasSeleccionados);
	}
	
	public void actionListenerEliminarDepa(PagoArriendoUnico depaSeleccionado) {
		depasSeleccionados = managerGarantia.eliminarDepartamentoPago(depasSeleccionados, depaSeleccionado);
		totalPagar = managerGarantia.totalDepasSeleccionados(depasSeleccionados);
	}
	
	public void actionListenerPagarPagoUnico() {
		managerGarantia.pagarPagoUnico(depasSeleccionados, cantidadPago, idCliente, dnpId);
	}
	
	public void actionListenerSeleccionarDeuda(DepArriendoCabecera cab) {
		this.cabDeudaSeleccionado = cab;
	}
	
	public void actionListenerPagarAbono() {
		managerGarantia.insertarAbono(cabDeudaSeleccionado, cantidadAbono);
	}
	
	public String actionMenuRegistro() {
		listaRegistro = managerGarantia.findAllRegistrosGarantia();
		return "registros";
	}
	
	//ACCESORES

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
