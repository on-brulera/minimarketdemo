package departamento.controller.controlpago;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import departamento.model.controlpagos.managers.ManagerArriendo;
import minimarketdemo.model.core.entities.SegUsuario;

@Named
@SessionScoped
public class BeanArriendo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private ManagerArriendo managerArriendo;
	
	private List<SegUsuario> clientes;
	private int idCliente;

	public BeanArriendo() {
	}
	
	//METODOS
	
	@PostConstruct
	
	public void inicializar() {
		clientes = managerArriendo.findAllClientes();
	}
	
	public String actionMenu(String ruta) {
		return ruta+"?faces-redirect=true";
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

}
