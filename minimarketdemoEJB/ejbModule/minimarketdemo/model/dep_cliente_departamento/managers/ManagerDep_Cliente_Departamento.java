package minimarketdemo.model.dep_cliente_departamento.managers;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import minimarketdemo.model.core.entities.DepClienteDepartamento;
import minimarketdemo.model.core.entities.DepDepartamento;
import minimarketdemo.model.core.entities.SegUsuario;
import minimarketdemo.model.dep_cliente_departamento.dto.DTOdcd;

@Stateless
@LocalBean
public class ManagerDep_Cliente_Departamento {
	@PersistenceContext
	private EntityManager em;
	
	
	public ManagerDep_Cliente_Departamento(){
		
	}
	
	public List<SegUsuario> findAllCliente() {
        final TypedQuery<SegUsuario> q = (TypedQuery<SegUsuario>)this.em.createQuery("select c from SegUsuario c order by c.idSegUsuario", (Class)SegUsuario.class);
        return (List<SegUsuario>)q.getResultList();
    }
	public List<DepDepartamento> findAllDepartamento() {
        final TypedQuery<DepDepartamento> q = (TypedQuery<DepDepartamento>)this.em.createQuery("select c from DepDepartamento c order by c.depId", (Class)DepDepartamento.class);
        return (List<DepDepartamento>)q.getResultList();
    }
	public List<DepClienteDepartamento> findAllDep() {
        final TypedQuery<DepClienteDepartamento> q = (TypedQuery<DepClienteDepartamento>)this.em.createQuery("select c from DepClienteDepartamento c order by c.dcdId", (Class)DepClienteDepartamento.class);
        return (List<DepClienteDepartamento>)q.getResultList();
    }
	
	
	public DTOdcd crearDTO(int id,int iddep) {
		
		SegUsuario a=em.find(SegUsuario.class, id);
		DepDepartamento dep=(DepDepartamento)this.em.find((Class)DepDepartamento.class, (Object)iddep);
		DTOdcd detalle=new DTOdcd();
    	if (dep.getDepDisponible() == true) {
        	detalle.setId(a.getIdSegUsuario());
        	detalle.setCodigo(a.getCodigo());
        	detalle.setApellidos(a.getApellidos());
        	detalle.setNombres(a.getNombres());	
		}else {
			detalle=null;
		}
    	return detalle;
    }
	
	public void CrearRelacion(List<DTOdcd> listadcd,int iddep, String obs) {

		for(DTOdcd det:listadcd) {
			DepClienteDepartamento dcd= new DepClienteDepartamento();
			DepDepartamento dep=(DepDepartamento)this.em.find((Class)DepDepartamento.class, (Object)iddep);
			dcd.setIdSegUsuario(det.getId());
			dcd.setDepDepartamento(dep);
			dep.setDepDisponible(false);
			dcd.setDcdObservacion(obs);
			em.persist(dcd);
			em.merge(dep);
    	}
	}

}
