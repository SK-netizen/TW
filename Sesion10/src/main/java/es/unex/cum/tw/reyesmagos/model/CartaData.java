package es.unex.cum.tw.reyesmagos.model;

import java.util.ArrayList;
import java.util.List;
/*
 * Clase para la implementación en memoria
 */
public class CartaData {
	private int idUser;
	private List<ItemData> lProductos;
	
	
	
	
	public CartaData(int idUser) {
		super();
		this.idUser = idUser;
		this.lProductos = new ArrayList<ItemData>();
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public List<ItemData> getlProductos() {
		return lProductos;
	}

	public void setlProductos(List<ItemData> lProductos) {
		this.lProductos = lProductos;
	}
	public void addProducto(ItemData i){
		lProductos.add(i);
	}
		
}
