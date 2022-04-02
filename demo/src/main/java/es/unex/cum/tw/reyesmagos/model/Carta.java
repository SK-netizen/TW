package es.unex.cum.tw.reyesmagos.model;

public class Carta {
	private int idProducto;
	private String nombre;
	private String nombreProd;
	private int cantidad;
	
	
	public Carta(int idProducto, String nombre, String nombreProd, int cantidad) {
		super();
		this.idProducto = idProducto;
		this.nombre = nombre;
		this.nombreProd = nombreProd;
		this.cantidad = cantidad;
	}
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombreProd() {
		return nombreProd;
	}
	public void setNombreProd(String nombreProd) {
		this.nombreProd = nombreProd;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	
}
