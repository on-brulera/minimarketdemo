package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the dep_cliente_departamento database table.
 * 
 */
@Entity
@Table(name="dep_cliente_departamento")
@NamedQuery(name="DepClienteDepartamento.findAll", query="SELECT d FROM DepClienteDepartamento d")
public class DepClienteDepartamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="dcd_id", unique=true, nullable=false)
	private Integer dcdId;

	@Column(name="dcd_observacion", length=200)
	private String dcdObservacion;

	@Column(name="id_seg_usuario")
	private Integer idSegUsuario;

	//bi-directional many-to-one association to DepDepartamento
	@ManyToOne
	@JoinColumn(name="dep_id")
	private DepDepartamento depDepartamento;

	public DepClienteDepartamento() {
	}

	public Integer getDcdId() {
		return this.dcdId;
	}

	public void setDcdId(Integer dcdId) {
		this.dcdId = dcdId;
	}

	public String getDcdObservacion() {
		return this.dcdObservacion;
	}

	public void setDcdObservacion(String dcdObservacion) {
		this.dcdObservacion = dcdObservacion;
	}

	public Integer getIdSegUsuario() {
		return this.idSegUsuario;
	}

	public void setIdSegUsuario(Integer idSegUsuario) {
		this.idSegUsuario = idSegUsuario;
	}

	public DepDepartamento getDepDepartamento() {
		return this.depDepartamento;
	}

	public void setDepDepartamento(DepDepartamento depDepartamento) {
		this.depDepartamento = depDepartamento;
	}

}