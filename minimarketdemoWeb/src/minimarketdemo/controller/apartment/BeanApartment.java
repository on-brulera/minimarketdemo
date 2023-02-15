package minimarketdemo.controller.apartment;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


import minimarketdemo.model.apartment.managers.ManagerApartment;
import minimarketdemo.model.core.entities.DepDepartamento;



@Named
@SessionScoped
public class BeanApartment implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerApartment managerApartment;
	
	private List<DepDepartamento> listaDepartamentos;
	
	private DepDepartamento departamentoEdicion;
	private DepDepartamento departamento;

	private Integer depId;
	private Integer numeroHabitaciones;
	private double precioDepartamento;
	private double garantiaDepartamento;
	private boolean estadoDepartamento;
	private boolean disponibleDepartamento;
	
	//private boolean panelColapsado;
	
	

	public BeanApartment() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void inicializar(){
		listaDepartamentos=managerApartment.findAllDepartamento();
		departamento=new DepDepartamento();
		departamentoEdicion=new DepDepartamento();
	}
	
	public void actionListenerInsertarDepartamento() throws Exception{
		try {
			managerApartment.insertarDepartamento(departamento);
			listaDepartamentos=managerApartment.findAllDepartamento();
			departamento=new DepDepartamento();
			minimarketdemo.controller.JSFUtil.crearMensajeINFO("Departamento creado");
		} catch (Exception e) {
			minimarketdemo.controller.JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace(); 	
		}
	
	}
	
	public void actionListenerSeleccionarDepartamento(DepDepartamento departamento) {
		depId=departamento.getDepId();
		if(departamento.getDepDisponible()) {
			minimarketdemo.controller.JSFUtil.crearMensajeERROR("El departamento ya esta alquilado");
			return;
		}else {
			departamento.setDepDisponible(true);
			
		}
	}
	
	public void actionListenerAlquilarDepartamento(DepDepartamento departamento) throws Exception {
		if(!departamento.getDepDisponible()) {
			managerApartment.alquilarDepartamento(departamento);
			minimarketdemo.controller.JSFUtil.crearMensajeINFO("Departamento alquilado");
		}else minimarketdemo.controller.JSFUtil.crearMensajeERROR("Departamento no disponible para su alquiler");
		
	}
	
	public void actionListenerSeleccionarDepartamentoEdicion(DepDepartamento departamento) {
		departamentoEdicion=departamento;
	}
	
	public void actionListenerCambiarEstadoDepartamento(DepDepartamento departamento) {
		managerApartment.cambiarEstadoAlquiler(departamento);
		minimarketdemo.controller.JSFUtil.crearMensajeINFO("Estado cambiado");
	}
	
	
	public void actionListenerActualizarDepartamento() throws Exception{
		managerApartment.actualizarDepartamento(departamentoEdicion);
		listaDepartamentos=managerApartment.findAllDepartamento();
		minimarketdemo.controller.JSFUtil.crearMensajeINFO("Departamento actualizado.");
	}
	
	public void actionListenerEliminarDepartamento(DepDepartamento departamento, Integer depIdSeleccionado) throws Exception {
		managerApartment.eliminarDepartamento(departamento,depIdSeleccionado);
		listaDepartamentos=managerApartment.findAllDepartamento();
		minimarketdemo.controller.JSFUtil.crearMensajeINFO("Departamento eliminado");
		
	}
	


	public List<DepDepartamento> getListaDepartamentos() {
		return listaDepartamentos;
	}



	public void setListaDepartamentos(List<DepDepartamento> listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}



	public DepDepartamento getDepartamentoEdicion() {
		return departamentoEdicion;
	}



	public void setDepartamentoEdicion(DepDepartamento departamentoEdicion) {
		this.departamentoEdicion = departamentoEdicion;
	}



	public int getDepId() {
		return depId;
	}



	public void setDepId(Integer depId) {
		this.depId = depId;
	}



	public int getNumeroHabitaciones() {
		return numeroHabitaciones;
	}



	public void setNumeroHabitaciones(Integer numeroHabitaciones) {
		this.numeroHabitaciones = numeroHabitaciones;
	}



	public double getPrecioDepartamento() {
		return precioDepartamento;
	}



	public void setPrecioDepartamento(double precioDepartamento) {
		this.precioDepartamento = precioDepartamento;
	}



	public double getGarantiaDepartamento() {
		return garantiaDepartamento;
	}



	public void setGarantiaDepartamento(double garantiaDepartamento) {
		this.garantiaDepartamento = garantiaDepartamento;
	}



	public boolean isEstadoDepartamento() {
		return estadoDepartamento;
	}



	public void setEstadoDepartamento(boolean estadoDepartamento) {
		this.estadoDepartamento = estadoDepartamento;
	}



	public boolean isDisponibleDepartamento() {
		return disponibleDepartamento;
	}



	public void setDisponibleDepartamento(boolean disponibleDepartamento) {
		this.disponibleDepartamento = disponibleDepartamento;
	}

	public DepDepartamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(DepDepartamento departamento) {
		this.departamento = departamento;
	}



	
	
}
