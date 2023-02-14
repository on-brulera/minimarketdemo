package minimarketdemo.controller.servicio;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import minimarketdemo.controller.JSFUtil;
import minimarketdemo.model.core.entities.DepServicio;
import minimarketdemo.model.servicio.managers.ManagerServicio;

@Named
@SessionScoped
public class BeanServicio implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerServicio managerServicio;
	private boolean panelColapsado=true;

	private List<DepServicio> listaServicios;
	private DepServicio nuevoServicio=new DepServicio();
	private DepServicio edicionServicio;

	public BeanServicio() {

	}
	
	public void actionListenerColapsarPanel() {
		panelColapsado = !panelColapsado;
	}

	public void actionListenerInsertarNuevoServicio() {
		try {
			managerServicio.insertarServicio(nuevoServicio.getSerDescripcion(),nuevoServicio.getSerPrecioBase());
			listaServicios = managerServicio.findAllServicio();
			nuevoServicio = new DepServicio();
			JSFUtil.crearMensajeINFO("Servicio insertado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public String actionSeleccionarEdicionServicio(DepServicio servicio) {
		edicionServicio = servicio;
		return "usuarios_edicion";
	}

	public void actionListenerActualizarEdicionServicio() {
		try {
			managerServicio.actualizarServicio(edicionServicio);
			listaServicios = managerServicio.findAllServicio();
			JSFUtil.crearMensajeINFO("Servicio actualizado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void actionListenerEliminarServicio(int idSegUsuario) {
		try {
			managerServicio.eliminarServicio(idSegUsuario);
			listaServicios = managerServicio.findAllServicio();
			JSFUtil.crearMensajeINFO("Servicio eliminado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public List<DepServicio> getListaServicios() {
		return listaServicios;
	}

	public void setListaServicios(List<DepServicio> listaServicios) {
		this.listaServicios = listaServicios;
	}

	public DepServicio getNuevoServicio() {
		return nuevoServicio;
	}

	public void setNuevoServicio(DepServicio nuevoServicio) {
		this.nuevoServicio = nuevoServicio;
	}

	public DepServicio getEdicionServicio() {
		return edicionServicio;
	}

	public void setEdicionServicio(DepServicio edicionServicio) {
		this.edicionServicio = edicionServicio;
	}

	public boolean isPanelColapsado() {
		return panelColapsado;
	}

	public void setPanelColapsado(boolean panelColapsado) {
		this.panelColapsado = panelColapsado;
	}

}
