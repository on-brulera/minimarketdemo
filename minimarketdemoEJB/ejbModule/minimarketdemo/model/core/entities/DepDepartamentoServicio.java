package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the dep_departamento_servicio database table.
 * 
 */
@Entity
@Table(name="dep_departamento_servicio")
@NamedQuery(name="DepDepartamentoServicio.findAll", query="SELECT d FROM DepDepartamentoServicio d")
public class DepDepartamentoServicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="dds_id", unique=true, nullable=false)
	private Integer ddsId;

	@Column(name="dds_observacion", length=200)
	private String ddsObservacion;

	//bi-directional many-to-one association to DepDepartamento
	@ManyToOne
	@JoinColumn(name="dep_id")
	private DepDepartamento depDepartamento;

	//bi-directional many-to-one association to DepServicio
	@ManyToOne
	@JoinColumn(name="ser_id")
	private DepServicio depServicio;

	public DepDepartamentoServicio() {
	}

	public Integer getDdsId() {
		return this.ddsId;
	}

	public void setDdsId(Integer ddsId) {
		this.ddsId = ddsId;
	}

	public String getDdsObservacion() {
		return this.ddsObservacion;
	}

	public void setDdsObservacion(String ddsObservacion) {
		this.ddsObservacion = ddsObservacion;
	}

	public DepDepartamento getDepDepartamento() {
		return this.depDepartamento;
	}

	public void setDepDepartamento(DepDepartamento depDepartamento) {
		this.depDepartamento = depDepartamento;
	}

	public DepServicio getDepServicio() {
		return this.depServicio;
	}

	public void setDepServicio(DepServicio depServicio) {
		this.depServicio = depServicio;
	}

}