package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the dep_arriendo_detalle database table.
 * 
 */
@Entity
@Table(name="dep_arriendo_detalle")
@NamedQuery(name="DepArriendoDetalle.findAll", query="SELECT d FROM DepArriendoDetalle d")
public class DepArriendoDetalle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="dad_id", unique=true, nullable=false)
	private Integer dadId;

	@Column(name="dad_cantidad")
	private double dadCantidad;

	@Temporal(TemporalType.DATE)
	@Column(name="dad_fecha_pago")
	private Date dadFechaPago;

	//bi-directional many-to-one association to DepArriendoCabecera
	@ManyToOne
	@JoinColumn(name="dac_id")
	private DepArriendoCabecera depArriendoCabecera;

	public DepArriendoDetalle() {
	}

	public Integer getDadId() {
		return this.dadId;
	}

	public void setDadId(Integer dadId) {
		this.dadId = dadId;
	}

	public double getDadCantidad() {
		return this.dadCantidad;
	}

	public void setDadCantidad(double dadCantidad) {
		this.dadCantidad = dadCantidad;
	}

	public Date getDadFechaPago() {
		return this.dadFechaPago;
	}

	public void setDadFechaPago(Date dadFechaPago) {
		this.dadFechaPago = dadFechaPago;
	}

	public DepArriendoCabecera getDepArriendoCabecera() {
		return this.depArriendoCabecera;
	}

	public void setDepArriendoCabecera(DepArriendoCabecera depArriendoCabecera) {
		this.depArriendoCabecera = depArriendoCabecera;
	}

}