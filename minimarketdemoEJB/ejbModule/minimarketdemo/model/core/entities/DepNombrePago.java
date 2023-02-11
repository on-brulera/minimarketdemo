package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the dep_nombre_pago database table.
 * 
 */
@Entity
@Table(name="dep_nombre_pago")
@NamedQuery(name="DepNombrePago.findAll", query="SELECT d FROM DepNombrePago d")
public class DepNombrePago implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="dnp_id", unique=true, nullable=false)
	private Integer dnpId;

	@Column(name="dnp_descripcion", length=200)
	private String dnpDescripcion;

	//bi-directional many-to-one association to DepArriendoCabecera
	@OneToMany(mappedBy="depNombrePago")
	private List<DepArriendoCabecera> depArriendoCabeceras;

	public DepNombrePago() {
	}

	public Integer getDnpId() {
		return this.dnpId;
	}

	public void setDnpId(Integer dnpId) {
		this.dnpId = dnpId;
	}

	public String getDnpDescripcion() {
		return this.dnpDescripcion;
	}

	public void setDnpDescripcion(String dnpDescripcion) {
		this.dnpDescripcion = dnpDescripcion;
	}

	public List<DepArriendoCabecera> getDepArriendoCabeceras() {
		return this.depArriendoCabeceras;
	}

	public void setDepArriendoCabeceras(List<DepArriendoCabecera> depArriendoCabeceras) {
		this.depArriendoCabeceras = depArriendoCabeceras;
	}

	public DepArriendoCabecera addDepArriendoCabecera(DepArriendoCabecera depArriendoCabecera) {
		getDepArriendoCabeceras().add(depArriendoCabecera);
		depArriendoCabecera.setDepNombrePago(this);

		return depArriendoCabecera;
	}

	public DepArriendoCabecera removeDepArriendoCabecera(DepArriendoCabecera depArriendoCabecera) {
		getDepArriendoCabeceras().remove(depArriendoCabecera);
		depArriendoCabecera.setDepNombrePago(null);

		return depArriendoCabecera;
	}

}