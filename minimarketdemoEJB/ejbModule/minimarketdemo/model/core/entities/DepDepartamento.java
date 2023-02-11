package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the dep_departamento database table.
 * 
 */
@Entity
@Table(name="dep_departamento")
@NamedQuery(name="DepDepartamento.findAll", query="SELECT d FROM DepDepartamento d")
public class DepDepartamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="dep_id", unique=true, nullable=false)
	private Integer depId;

	@Column(name="dep_dia_pago")
	private Integer depDiaPago;

	@Column(name="dep_disponible")
	private Boolean depDisponible;

	@Column(name="dep_estado")
	private Boolean depEstado;

	@Column(name="dep_garantia")
	private double depGarantia;

	@Column(name="dep_num_cuartos")
	private Integer depNumCuartos;

	@Column(name="dep_precio")
	private double depPrecio;

	//bi-directional many-to-one association to DepArriendoCabecera
	@OneToMany(mappedBy="depDepartamento")
	private List<DepArriendoCabecera> depArriendoCabeceras;

	//bi-directional many-to-one association to DepClienteDepartamento
	@OneToMany(mappedBy="depDepartamento")
	private List<DepClienteDepartamento> depClienteDepartamentos;

	//bi-directional many-to-one association to DepDepartamentoServicio
	@OneToMany(mappedBy="depDepartamento")
	private List<DepDepartamentoServicio> depDepartamentoServicios;

	public DepDepartamento() {
	}

	public Integer getDepId() {
		return this.depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	public Integer getDepDiaPago() {
		return this.depDiaPago;
	}

	public void setDepDiaPago(Integer depDiaPago) {
		this.depDiaPago = depDiaPago;
	}

	public Boolean getDepDisponible() {
		return this.depDisponible;
	}

	public void setDepDisponible(Boolean depDisponible) {
		this.depDisponible = depDisponible;
	}

	public Boolean getDepEstado() {
		return this.depEstado;
	}

	public void setDepEstado(Boolean depEstado) {
		this.depEstado = depEstado;
	}

	public double getDepGarantia() {
		return this.depGarantia;
	}

	public void setDepGarantia(double depGarantia) {
		this.depGarantia = depGarantia;
	}

	public Integer getDepNumCuartos() {
		return this.depNumCuartos;
	}

	public void setDepNumCuartos(Integer depNumCuartos) {
		this.depNumCuartos = depNumCuartos;
	}

	public double getDepPrecio() {
		return this.depPrecio;
	}

	public void setDepPrecio(double depPrecio) {
		this.depPrecio = depPrecio;
	}

	public List<DepArriendoCabecera> getDepArriendoCabeceras() {
		return this.depArriendoCabeceras;
	}

	public void setDepArriendoCabeceras(List<DepArriendoCabecera> depArriendoCabeceras) {
		this.depArriendoCabeceras = depArriendoCabeceras;
	}

	public DepArriendoCabecera addDepArriendoCabecera(DepArriendoCabecera depArriendoCabecera) {
		getDepArriendoCabeceras().add(depArriendoCabecera);
		depArriendoCabecera.setDepDepartamento(this);

		return depArriendoCabecera;
	}

	public DepArriendoCabecera removeDepArriendoCabecera(DepArriendoCabecera depArriendoCabecera) {
		getDepArriendoCabeceras().remove(depArriendoCabecera);
		depArriendoCabecera.setDepDepartamento(null);

		return depArriendoCabecera;
	}

	public List<DepClienteDepartamento> getDepClienteDepartamentos() {
		return this.depClienteDepartamentos;
	}

	public void setDepClienteDepartamentos(List<DepClienteDepartamento> depClienteDepartamentos) {
		this.depClienteDepartamentos = depClienteDepartamentos;
	}

	public DepClienteDepartamento addDepClienteDepartamento(DepClienteDepartamento depClienteDepartamento) {
		getDepClienteDepartamentos().add(depClienteDepartamento);
		depClienteDepartamento.setDepDepartamento(this);

		return depClienteDepartamento;
	}

	public DepClienteDepartamento removeDepClienteDepartamento(DepClienteDepartamento depClienteDepartamento) {
		getDepClienteDepartamentos().remove(depClienteDepartamento);
		depClienteDepartamento.setDepDepartamento(null);

		return depClienteDepartamento;
	}

	public List<DepDepartamentoServicio> getDepDepartamentoServicios() {
		return this.depDepartamentoServicios;
	}

	public void setDepDepartamentoServicios(List<DepDepartamentoServicio> depDepartamentoServicios) {
		this.depDepartamentoServicios = depDepartamentoServicios;
	}

	public DepDepartamentoServicio addDepDepartamentoServicio(DepDepartamentoServicio depDepartamentoServicio) {
		getDepDepartamentoServicios().add(depDepartamentoServicio);
		depDepartamentoServicio.setDepDepartamento(this);

		return depDepartamentoServicio;
	}

	public DepDepartamentoServicio removeDepDepartamentoServicio(DepDepartamentoServicio depDepartamentoServicio) {
		getDepDepartamentoServicios().remove(depDepartamentoServicio);
		depDepartamentoServicio.setDepDepartamento(null);

		return depDepartamentoServicio;
	}

}