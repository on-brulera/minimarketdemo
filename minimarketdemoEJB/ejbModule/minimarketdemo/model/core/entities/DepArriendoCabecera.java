package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the dep_arriendo_cabecera database table.
 * 
 */
@Entity
@Table(name="dep_arriendo_cabecera")
@NamedQuery(name="DepArriendoCabecera.findAll", query="SELECT d FROM DepArriendoCabecera d")
public class DepArriendoCabecera implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="dac_id", unique=true, nullable=false)
	private Integer dacId;

	@Column(name="cli_cedula", length=10)
	private String cliCedula;

	@Column(name="cli_nombres", length=50)
	private String cliNombres;

	@Temporal(TemporalType.DATE)
	@Column(name="dac_fecha_fin")
	private Date dacFechaFin;

	@Temporal(TemporalType.DATE)
	@Column(name="dac_fecha_inicio")
	private Date dacFechaInicio;

	@Temporal(TemporalType.DATE)
	@Column(name="dac_fecha_limite")
	private Date dacFechaLimite;

	@Column(name="dac_meses_pagados")
	private Integer dacMesesPagados;

	@Column(name="dac_pago_restante")
	private double dacPagoRestante;

	@Column(name="dac_total_pago")
	private double dacTotalPago;

	//bi-directional many-to-one association to DepDepartamento
	@ManyToOne
	@JoinColumn(name="dep_id")
	private DepDepartamento depDepartamento;

	//bi-directional many-to-one association to DepNombrePago
	@ManyToOne
	@JoinColumn(name="dnp_id")
	private DepNombrePago depNombrePago;

	//bi-directional many-to-one association to DepArriendoDetalle
	@OneToMany(mappedBy="depArriendoCabecera", cascade=CascadeType.ALL)
	private List<DepArriendoDetalle> depArriendoDetalles;

	public DepArriendoCabecera() {
	}

	public Integer getDacId() {
		return this.dacId;
	}

	public void setDacId(Integer dacId) {
		this.dacId = dacId;
	}

	public String getCliCedula() {
		return this.cliCedula;
	}

	public void setCliCedula(String cliCedula) {
		this.cliCedula = cliCedula;
	}

	public String getCliNombres() {
		return this.cliNombres;
	}

	public void setCliNombres(String cliNombres) {
		this.cliNombres = cliNombres;
	}

	public Date getDacFechaFin() {
		return this.dacFechaFin;
	}

	public void setDacFechaFin(Date dacFechaFin) {
		this.dacFechaFin = dacFechaFin;
	}

	public Date getDacFechaInicio() {
		return this.dacFechaInicio;
	}

	public void setDacFechaInicio(Date dacFechaInicio) {
		this.dacFechaInicio = dacFechaInicio;
	}

	public Date getDacFechaLimite() {
		return this.dacFechaLimite;
	}

	public void setDacFechaLimite(Date dacFechaLimite) {
		this.dacFechaLimite = dacFechaLimite;
	}

	public Integer getDacMesesPagados() {
		return this.dacMesesPagados;
	}

	public void setDacMesesPagados(Integer dacMesesPagados) {
		this.dacMesesPagados = dacMesesPagados;
	}

	public double getDacPagoRestante() {
		return this.dacPagoRestante;
	}

	public void setDacPagoRestante(double dacPagoRestante) {
		this.dacPagoRestante = dacPagoRestante;
	}

	public double getDacTotalPago() {
		return this.dacTotalPago;
	}

	public void setDacTotalPago(double dacTotalPago) {
		this.dacTotalPago = dacTotalPago;
	}

	public DepDepartamento getDepDepartamento() {
		return this.depDepartamento;
	}

	public void setDepDepartamento(DepDepartamento depDepartamento) {
		this.depDepartamento = depDepartamento;
	}

	public DepNombrePago getDepNombrePago() {
		return this.depNombrePago;
	}

	public void setDepNombrePago(DepNombrePago depNombrePago) {
		this.depNombrePago = depNombrePago;
	}

	public List<DepArriendoDetalle> getDepArriendoDetalles() {
		return this.depArriendoDetalles;
	}

	public void setDepArriendoDetalles(List<DepArriendoDetalle> depArriendoDetalles) {
		this.depArriendoDetalles = depArriendoDetalles;
	}

	public DepArriendoDetalle addDepArriendoDetalle(DepArriendoDetalle depArriendoDetalle) {
		getDepArriendoDetalles().add(depArriendoDetalle);
		depArriendoDetalle.setDepArriendoCabecera(this);

		return depArriendoDetalle;
	}

	public DepArriendoDetalle removeDepArriendoDetalle(DepArriendoDetalle depArriendoDetalle) {
		getDepArriendoDetalles().remove(depArriendoDetalle);
		depArriendoDetalle.setDepArriendoCabecera(null);

		return depArriendoDetalle;
	}

}