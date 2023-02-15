package minimarketdemo.controller.registro;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import minimarketdemo.controller.JSFUtil;
import departamento.model.registro.managers.ManagerRegistro;
import minimarketdemo.model.core.entities.SegUsuario;

@Named
@SessionScoped
public class BeanRegistro implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerRegistro managerregistro;
	
	
	private List<SegUsuario> listaClientes;
	
	private SegUsuario Cliente;
	private String codigo;
	private String apellidos;
	private String nombres;
	private String correo;
	private SegUsuario Clientesel;
	

	public BeanRegistro() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void inicializar() {

		listaClientes=managerregistro.findAllCliente();

	}
	
	
	public void actionListenerAgregarCliente() {
		listaClientes.add(managerregistro.CrearCliente(codigo, apellidos, nombres, correo));
		JSFUtil.crearMensajeINFO("Se registro el Cliente");
	}
	
	public void actionListenerEliminarCliente(int id) {
		listaClientes.remove(managerregistro.eliminarcliente(id)) ;
		listaClientes=managerregistro.findAllCliente();
		JSFUtil.crearMensajeINFO("Cliente eliminado");
	}
	
	public void actionListenerSeleccionarCliente(SegUsuario c) {
		Clientesel=c;
	}
	
	public void actionListenerActualizarCliente() {
		managerregistro.actualizarcliente(Clientesel);
		listaClientes=managerregistro.findAllCliente();
		JSFUtil.crearMensajeINFO("Cliente actualizado.");
	}

	public ManagerRegistro getManagerregistro() {
		return managerregistro;
	}

	public void setManagerregistro(ManagerRegistro managerregistro) {
		this.managerregistro = managerregistro;
	}

	public List<SegUsuario> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<SegUsuario> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public SegUsuario getCliente() {
		return Cliente;
	}

	public void setCliente(SegUsuario cliente) {
		Cliente = cliente;
	}

	public SegUsuario getClientesel() {
		return Clientesel;
	}

	public void setClientesel(SegUsuario clientesel) {
		Clientesel = clientesel;
	}
	
	

}
