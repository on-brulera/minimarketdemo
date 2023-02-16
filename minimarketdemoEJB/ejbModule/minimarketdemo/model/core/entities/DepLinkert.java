package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the dep_linkert database table.
 * 
 */
@Entity
@Table(name="dep_linkert")
@NamedQuery(name="DepLinkert.findAll", query="SELECT d FROM DepLinkert d")
public class DepLinkert implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="lin_id", unique=true, nullable=false)
	private Integer linId;

	@Column(name="lin_descripcion", length=50)
	private String linDescripcion;

	@Column(name="lin_linkert")
	private Integer linLinkert;

	//bi-directional many-to-one association to DepAuditoria
	@ManyToOne
	@JoinColumn(name="aud_id")
	private DepAuditoria depAuditoria;

	public DepLinkert() {
	}

	public Integer getLinId() {
		return this.linId;
	}

	public void setLinId(Integer linId) {
		this.linId = linId;
	}

	public String getLinDescripcion() {
		return this.linDescripcion;
	}

	public void setLinDescripcion(String linDescripcion) {
		this.linDescripcion = linDescripcion;
	}

	public Integer getLinLinkert() {
		return this.linLinkert;
	}

	public void setLinLinkert(Integer linLinkert) {
		this.linLinkert = linLinkert;
	}

	public DepAuditoria getDepAuditoria() {
		return this.depAuditoria;
	}

	public void setDepAuditoria(DepAuditoria depAuditoria) {
		this.depAuditoria = depAuditoria;
	}

}