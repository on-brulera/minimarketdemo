package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the dep_servicio database table.
 * 
 */
@Entity
@Table(name="dep_servicio")
@NamedQuery(name="DepServicio.findAll", query="SELECT d FROM DepServicio d")
public class DepServicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ser_id", unique=true, nullable=false)
	private Integer serId;

	@Column(name="ser_descripcion", length=200)
	private String serDescripcion;

	@Column(name="ser_precio_base")
	private double serPrecioBase;

	//bi-directional many-to-one association to DepDepartamentoServicio
	@OneToMany(mappedBy="depServicio")
	private List<DepDepartamentoServicio> depDepartamentoServicios;

	public DepServicio() {
	}

	public Integer getSerId() {
		return this.serId;
	}

	public void setSerId(Integer serId) {
		this.serId = serId;
	}

	public String getSerDescripcion() {
		return this.serDescripcion;
	}

	public void setSerDescripcion(String serDescripcion) {
		this.serDescripcion = serDescripcion;
	}

	public double getSerPrecioBase() {
		return this.serPrecioBase;
	}

	public void setSerPrecioBase(double serPrecioBase) {
		this.serPrecioBase = serPrecioBase;
	}

	public List<DepDepartamentoServicio> getDepDepartamentoServicios() {
		return this.depDepartamentoServicios;
	}

	public void setDepDepartamentoServicios(List<DepDepartamentoServicio> depDepartamentoServicios) {
		this.depDepartamentoServicios = depDepartamentoServicios;
	}

	public DepDepartamentoServicio addDepDepartamentoServicio(DepDepartamentoServicio depDepartamentoServicio) {
		getDepDepartamentoServicios().add(depDepartamentoServicio);
		depDepartamentoServicio.setDepServicio(this);

		return depDepartamentoServicio;
	}

	public DepDepartamentoServicio removeDepDepartamentoServicio(DepDepartamentoServicio depDepartamentoServicio) {
		getDepDepartamentoServicios().remove(depDepartamentoServicio);
		depDepartamentoServicio.setDepServicio(null);

		return depDepartamentoServicio;
	}

}