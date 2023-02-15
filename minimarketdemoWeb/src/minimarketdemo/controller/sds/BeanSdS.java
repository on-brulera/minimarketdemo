package minimarketdemo.controller.sds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import minimarketdemo.controller.JSFUtil;
import minimarketdemo.model.core.entities.DepDepartamento;
import minimarketdemo.model.core.entities.DepServicio;
import minimarketdemo.model.ser_servicio_departamento.dto.DTOsds;
import minimarketdemo.model.core.entities.DepDepartamentoServicio;
import minimarketdemo.model.ser_servicio_departamento.managers.ManagerSer_Servicio_Departamento;

@Named
@SessionScoped
public class BeanSdS implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerSer_Servicio_Departamento managersds;

	private List<DepServicio> listaServicios;
	private List<DepDepartamento> listaDepartamentos;
	private List<DepDepartamentoServicio> listadcd;
	private List<DTOsds> listadto;

	private int id_ser;
	private int id_Dep;
	private String obs;

	public BeanSdS() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void inicializar() {
		listaServicios = managersds.findAllServicio();
		listaDepartamentos = managersds.findAllDepartamento();
		listadcd = managersds.findAllDep();
		listadto = new ArrayList<DTOsds>();
	}

	public void eliminar(int idser) {
		for (DTOsds revision : listadto) {
			if (revision.getId() == idser) {
				listadto.remove(revision);
				break;
			}
		}
	}

	public void actionListenerAgregarDTO() {

		listadto.add(managersds.crearDTO(id_Dep, id_ser));

	}

	public void actionListenerGuardar() {
		managersds.CrearRelacion(listadto, id_Dep, obs);
		JSFUtil.crearMensajeINFO("Departamento Asignado");
		listadto = new ArrayList<DTOsds>();
	}

	public List<DepServicio> getListaServicios() {
		return listaServicios;
	}

	public void setListaServicios(List<DepServicio> listaServicios) {
		this.listaServicios = listaServicios;
	}

	public List<DepDepartamento> getListaDepartamentos() {
		return listaDepartamentos;
	}

	public void setListaDepartamentos(List<DepDepartamento> listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}

	public List<DepDepartamentoServicio> getListadcd() {
		return listadcd;
	}

	public void setListadcd(List<DepDepartamentoServicio> listadcd) {
		this.listadcd = listadcd;
	}

	public List<DTOsds> getListadto() {
		return listadto;
	}

	public void setListadto(List<DTOsds> listadto) {
		this.listadto = listadto;
	}

	public int getId_ser() {
		return id_ser;
	}

	public void setId_ser(int id_ser) {
		this.id_ser = id_ser;
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
