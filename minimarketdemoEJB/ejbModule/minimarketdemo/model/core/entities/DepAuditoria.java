package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the dep_auditoria database table.
 * 
 */
@Entity
@Table(name="dep_auditoria")
@NamedQuery(name="DepAuditoria.findAll", query="SELECT d FROM DepAuditoria d")
public class DepAuditoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="aud_id", unique=true, nullable=false)
	private Integer audId;

	@Column(name="aud_accion", nullable=false, length=20)
	private String audAccion;

	@Column(name="aud_accion_descripcion", length=200)
	private String audAccionDescripcion;

	@Temporal(TemporalType.DATE)
	@Column(name="aud_fecha_accion", nullable=false)
	private Date audFechaAccion;

	@Column(name="aud_id_usuario")
	private Integer audIdUsuario;

	@Column(name="aud_nombre_persona", nullable=false, length=50)
	private String audNombrePersona;

	@Column(name="aud_nombre_rol", nullable=false, length=50)
	private String audNombreRol;

	@Column(name="aud_nombre_tabla", nullable=false, length=30)
	private String audNombreTabla;

	//bi-directional many-to-one association to DepLinkert
	@OneToMany(mappedBy="depAuditoria")
	private List<DepLinkert> depLinkerts;

	public DepAuditoria() {
	}

	public Integer getAudId() {
		return this.audId;
	}

	public void setAudId(Integer audId) {
		this.audId = audId;
	}

	public String getAudAccion() {
		return this.audAccion;
	}

	public void setAudAccion(String audAccion) {
		this.audAccion = audAccion;
	}

	public String getAudAccionDescripcion() {
		return this.audAccionDescripcion;
	}

	public void setAudAccionDescripcion(String audAccionDescripcion) {
		this.audAccionDescripcion = audAccionDescripcion;
	}

	public Date getAudFechaAccion() {
		return this.audFechaAccion;
	}

	public void setAudFechaAccion(Date audFechaAccion) {
		this.audFechaAccion = audFechaAccion;
	}

	public Integer getAudIdUsuario() {
		return this.audIdUsuario;
	}

	public void setAudIdUsuario(Integer audIdUsuario) {
		this.audIdUsuario = audIdUsuario;
	}

	public String getAudNombrePersona() {
		return this.audNombrePersona;
	}

	public void setAudNombrePersona(String audNombrePersona) {
		this.audNombrePersona = audNombrePersona;
	}

	public String getAudNombreRol() {
		return this.audNombreRol;
	}

	public void setAudNombreRol(String audNombreRol) {
		this.audNombreRol = audNombreRol;
	}

	public String getAudNombreTabla() {
		return this.audNombreTabla;
	}

	public void setAudNombreTabla(String audNombreTabla) {
		this.audNombreTabla = audNombreTabla;
	}

	public List<DepLinkert> getDepLinkerts() {
		return this.depLinkerts;
	}

	public void setDepLinkerts(List<DepLinkert> depLinkerts) {
		this.depLinkerts = depLinkerts;
	}

	public DepLinkert addDepLinkert(DepLinkert depLinkert) {
		getDepLinkerts().add(depLinkert);
		depLinkert.setDepAuditoria(this);

		return depLinkert;
	}

	public DepLinkert removeDepLinkert(DepLinkert depLinkert) {
		getDepLinkerts().remove(depLinkert);
		depLinkert.setDepAuditoria(null);

		return depLinkert;
	}

}