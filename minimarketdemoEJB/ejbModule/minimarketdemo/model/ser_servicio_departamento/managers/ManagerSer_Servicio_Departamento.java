package minimarketdemo.model.ser_servicio_departamento.managers;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import minimarketdemo.model.core.entities.DepDepartamentoServicio;
import minimarketdemo.model.core.entities.DepDepartamento;
import minimarketdemo.model.core.entities.DepServicio;
import minimarketdemo.model.ser_servicio_departamento.dto.DTOsds;

/**
 * Session Bean implementation class ManagerSer_Servicio_Departamento
 */
@Stateless
@LocalBean
public class ManagerSer_Servicio_Departamento {
	@PersistenceContext
	private EntityManager em;

	public ManagerSer_Servicio_Departamento() {

	}

	public List<DepServicio> findAllServicio() {
		final TypedQuery<DepServicio> q = (TypedQuery<DepServicio>) this.em.createQuery("select d from DepServicio d order by d.serId", (Class) DepServicio.class);
		return (List<DepServicio>) q.getResultList();
	}

	public List<DepDepartamento> findAllDepartamento() {
		final TypedQuery<DepDepartamento> q = (TypedQuery<DepDepartamento>) this.em.createQuery("select d from DepDepartamento d order by d.depId", (Class) DepDepartamento.class);
		return (List<DepDepartamento>) q.getResultList();
	}

	public List<DepDepartamentoServicio> findAllDep() {
		final TypedQuery<DepDepartamentoServicio> q = (TypedQuery<DepDepartamentoServicio>) this.em.createQuery("select d from DepDepartamentoServicio d order by d.ddsId", (Class) DepDepartamentoServicio.class);
		return (List<DepDepartamentoServicio>) q.getResultList();
	}

	public List<DepDepartamentoServicio> findRegistro() {
		final TypedQuery<DepDepartamentoServicio> q = (TypedQuery<DepDepartamentoServicio>) this.em.createQuery("select d from DepDepartamentoServicio d order by d.ddsId", (Class) DepDepartamentoServicio.class);
		return (List<DepDepartamentoServicio>) q.getResultList();
	}
	
	public DTOsds crearDTO(int id, int serId) {

		DepDepartamento d = em.find(DepDepartamento.class, id);
		DepServicio s= em.find(DepServicio.class, serId);
		DTOsds detalle = new DTOsds();

		detalle.setId(d.getDepId());
		detalle.setDescripcion(s.getSerDescripcion());
		detalle.setPreciobase(s.getSerPrecioBase());
		return detalle;
	}

	public void CrearRelacion(List<DTOsds> listasds, int iddep, String obs) {

		for (DTOsds det : listasds) {
			DepDepartamentoServicio dcd = new DepDepartamentoServicio();
			DepDepartamento dep = (DepDepartamento) this.em.find((Class) DepDepartamento.class, (Object) iddep);
			DepServicio ds=new DepServicio();
			ds.setSerId(det.getId());
			dcd.setDepDepartamento(dep);
			dcd.setDepServicio(ds);
			dcd.setDdsObservacion(obs);
			em.persist(dcd);
			em.merge(dep);
		}
	}

}
