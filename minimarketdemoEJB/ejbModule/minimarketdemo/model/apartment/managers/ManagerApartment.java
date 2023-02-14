package minimarketdemo.model.apartment.managers;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import minimarketdemo.model.core.entities.DepDepartamento;

/**
 * Session Bean implementation class ManagerApartment
 */
@Stateless
@LocalBean
public class ManagerApartment {
	
	@PersistenceContext
	private EntityManager em;

    /**
     * Default constructor. 
     */
    public ManagerApartment() {
        // TODO Auto-generated constructor stub
    }
    
    public List<DepDepartamento> findAllDepartamento(){
    	TypedQuery<DepDepartamento> q=em.createQuery("Select d from DepDepartamento d order by d.depId", DepDepartamento.class);
    	return q.getResultList();
    }
    
    public DepDepartamento findDepartamentoById(Integer depId) throws Exception {
    	/*
		if (depId == null)
			throw new Exception("Debe especificar el codigo para buscar el dato.");*/
    	
		DepDepartamento o;
		try {
			o = em.find(DepDepartamento.class, depId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar la informacion especificada (" + depId + ") : " + e.getMessage());
		}
		return o;
	}
    
    public void insertarDepartamento(DepDepartamento departamento) throws Exception {
    
    	
    	DepDepartamento a= new DepDepartamento();
    	//a.setDepId(departamento.getDepId());
    	a.setDepDisponible(departamento.getDepDisponible());
    	a.setDepEstado(departamento.getDepEstado());
    	a.setDepGarantia(departamento.getDepGarantia());
    	a.setDepNumCuartos(departamento.getDepNumCuartos());
    	a.setDepPrecio(departamento.getDepPrecio());
    	a.setDepDiaPago(departamento.getDepDiaPago());
    	
    	em.persist(a);
    	
    	
    }
    
    public DepDepartamento cambiarEstadoAlquiler(DepDepartamento departamento) {
    	departamento.setDepDisponible(!departamento.getDepDisponible());
    	em.merge(departamento);
    	return departamento;
    }
    
    public DepDepartamento alquilarDepartamento (DepDepartamento departamento) throws Exception{
    	try {
			departamento.setDepDisponible(true);
			em.merge(departamento);
			return departamento;
		} catch (Exception e) {
			throw new Exception ("No se pudo alquilar el departamento"+e.getMessage());
		}
    	
    }
    
    public DepDepartamento actualizarDepartamento(DepDepartamento dep) throws Exception {
    	DepDepartamento a=findDepartamentoById(dep.getDepId());
    	//if (a==null) throw new Exception("No existe departamento con dicha id");
    	a.setDepDisponible(dep.getDepDisponible());
    	a.setDepEstado(dep.getDepEstado());
    	a.setDepGarantia(dep.getDepGarantia());
    	a.setDepNumCuartos(dep.getDepNumCuartos());
    	a.setDepPrecio(dep.getDepPrecio());
    	a.setDepDiaPago(dep.getDepDiaPago());
    	
    	//Servicio s=em.find(Servicio.class, idServicioEdicion);
    	
    	em.merge(a);
    	return a;
    	
    }
    
    public void eliminarDepartamento(DepDepartamento departamento, Integer depId) throws Exception {
		if (depId == null) {
			throw new Exception("Debe especificar un identificador para eliminar el dato solicitado.");
		}
		DepDepartamento o = findDepartamentoById(depId);
		try {
			em.remove(o);
		} catch (Exception e) {
			throw new Exception("No se pudo eliminar el dato: " + e.getMessage());
		}
	}
    

}
