package minimarketdemo.controller.dcd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import departamento.model.registro.managers.ManagerRegistro;
import minimarketdemo.controller.JSFUtil;
import minimarketdemo.model.core.entities.DepClienteDepartamento;
import minimarketdemo.model.core.entities.DepDepartamento;
import minimarketdemo.model.core.entities.SegUsuario;
import minimarketdemo.model.dep_cliente_departamento.dto.DTOdcd;
import minimarketdemo.model.dep_cliente_departamento.managers.ManagerDep_Cliente_Departamento;

@Named
@SessionScoped
public class BeanDcD implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerDep_Cliente_Departamento managerdcd;
	
	private List<SegUsuario> listaClientes;
	private List<DepDepartamento> listaDepartamentos;
	private List<DepClienteDepartamento> listadcd;
	private List<DTOdcd> listadto;
	
	private int id_Cliente;
	private int id_Dep;
	private String obs;

	public BeanDcD() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void inicializar() {

		listaClientes=managerdcd.findAllCliente();
		listaDepartamentos=managerdcd.findAllDepartamento();
		listadcd=managerdcd.findAllDep();
		listadto=new ArrayList<DTOdcd>();

	}
	
	public void eliminar(int idCliente) {
		for (DTOdcd revision : listadto) {
			if(revision.getId()== idCliente) {
				listadto.remove(revision);
				break;
			}
		}
	}
	
	public void actionListenerAgregarDTO(){
		if ((managerdcd.crearDTO(id_Cliente,id_Dep)) != null) {
			listadto.add(managerdcd.crearDTO(id_Cliente,id_Dep));
		}else {
			JSFUtil.crearMensajeINFO("Departamento NO Dsiponible");
		}
	}
	
	public void actionListenerGuardar() {
		managerdcd.CrearRelacion(listadto, id_Dep,obs);
		JSFUtil.crearMensajeINFO("Departamento Asignado");
		listadto= new ArrayList<DTOdcd>();
	}

	public ManagerDep_Cliente_Departamento getManagerdcd() {
		return managerdcd;
	}

	public void setManagerdcd(ManagerDep_Cliente_Departamento managerdcd) {
		this.managerdcd = managerdcd;
	}

	public List<SegUsuario> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<SegUsuario> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public List<DepDepartamento> getListaDepartamentos() {
		return listaDepartamentos;
	}

	public void setListaDepartamentos(List<DepDepartamento> listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}

	public List<DepClienteDepartamento> getListadcd() {
		return listadcd;
	}

	public void setListadcd(List<DepClienteDepartamento> listadcd) {
		this.listadcd = listadcd;
	}

	public List<DTOdcd> getListadto() {
		return listadto;
	}

	public void setListadto(List<DTOdcd> listadto) {
		this.listadto = listadto;
	}

	public int getId_Cliente() {
		return id_Cliente;
	}

	public void setId_Cliente(int id_Cliente) {
		this.id_Cliente = id_Cliente;
	}

	public int getId_Dep() {
		return id_Dep;
	}

	public void setId_Dep(int id_Dep) {
		this.id_Dep = id_Dep;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}
	
	
}
