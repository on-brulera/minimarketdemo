package departamento.model.controlpagos.DTO;

import java.util.Date;

public class PagoArriendoUnico {
	private int depId;
	private int numClientes;
	private double depPrecio;
	private double serPrecio;
	private double total;
	private Date limFechaPago;

	public PagoArriendoUnico() {
	}

	public int getDepId() {
		return depId;
	}

	public void setDepId(int depId) {
		this.depId = depId;
	}

	public int getNumClientes() {
		return numClientes;
	}

	public void setNumClientes(int numClientes) {
		this.numClientes = numClientes;
	}

	public double getDepPrecio() {
		return depPrecio;
	}

	public void setDepPrecio(double depPrecio) {
		this.depPrecio = depPrecio;
	}

	public double getSerPrecio() {
		return serPrecio;
	}

	public void setSerPrecio(double serPrecio) {
		this.serPrecio = serPrecio;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Date getLimFechaPago() {
		return limFechaPago;
	}

	public void setLimFechaPago(Date limFechaPago) {
		this.limFechaPago = limFechaPago;
	}
}
