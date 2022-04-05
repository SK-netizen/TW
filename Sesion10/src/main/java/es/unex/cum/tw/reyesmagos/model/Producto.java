package es.unex.cum.tw.reyesmagos.model;

public class Producto {
	private int idProductos;
	private String nombreProd;
	private String pathImagen;
	private String comentarios;
	
	
	
	public Producto(int id,String nombreProd, String pathImagen, String comentarios) {
		idProductos=id;
		this.nombreProd = nombreProd;
		this.pathImagen = pathImagen;
		this.comentarios = comentarios;
	}
	public int getIdProductos() {
		return idProductos;
	}
	public void setIdProductos(int idProductos) {
		this.idProductos = idProductos;
	}
	public String getNombreProd() {
		return nombreProd;
	}
	public void setNombreProd(String nombreProd) {
		this.nombreProd = nombreProd;
	}
	public String getPathImagen() {
		return pathImagen;
	}
	public void setPathImagen(String pathImagen) {
		this.pathImagen = pathImagen;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	
	

}
