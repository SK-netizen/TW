package es.unex.cum.tw.reyesmagos.service;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.ConexionUtil;
import es.unex.cum.tw.reyesmagos.controller.CartaReyesController;
import es.unex.cum.tw.reyesmagos.controller.UsuarioController;
import es.unex.cum.tw.reyesmagos.model.Carta;
import es.unex.cum.tw.reyesmagos.model.CartaData;
import es.unex.cum.tw.reyesmagos.model.ItemData;
import es.unex.cum.tw.reyesmagos.model.Producto;
import es.unex.cum.tw.reyesmagos.model.Usuario;

public class CartaServiceMemory implements CartaService {

	// La Carta es compartida. La clave es la clave de idUser
	private static HashMap<Integer, CartaData> lCarta = null;

	private void buildMap() {
		// Se carga los usuarios del fichero
		if (lCarta == null) {
			lCarta = new HashMap<Integer, CartaData>();
		}
	}

	public boolean anadirCarta(String idP, String id) throws IOException,
			SQLException {
		if (lCarta== null)
			buildMap();
		synchronized (lCarta) {
			// Tengo la carta
			CartaData aux = (CartaData)lCarta.get(Integer.parseInt(id));			
			// Si el usuario existe
			if (aux != null) {
				List<ItemData> auxL = aux.getlProductos();
				Iterator<ItemData> it = auxL.iterator();
				while (it.hasNext()) {
					ItemData i = (ItemData) it.next();
					if (i.getIdProducto() == Integer.parseInt(idP)) {
						i.setCantidad(i.getCantidad() + 1);
						return true;
					}
				}
			} else {
				aux= new CartaData(Integer.parseInt(id));
				ItemData i= new ItemData();
				i.setCantidad(1);
				i.setIdProducto(Integer.parseInt(idP));
				aux.addProducto(i);
				lCarta.put(Integer.parseInt(id), aux);
				return true;
			}

		}
		return false;
	}

	public boolean eliminarCarta(String idP, String id) throws IOException,
			SQLException {
		if (lCarta== null)
			buildMap();
		synchronized (lCarta) {
			// Tengo la carta
			CartaData aux = (CartaData)lCarta.get(Integer.parseInt(id));
			// Si el usuario existe
			if (aux != null) {
				List<ItemData> auxL = aux.getlProductos();
				Iterator<ItemData> it = auxL.iterator();
				while (it.hasNext()) {
					ItemData i = (ItemData) it.next();
					if (i.getIdProducto() == Integer.parseInt(idP)) {
						it.remove();
						return true;
					}
				}
			}

		}
		return false;

	}

	public List<Carta> verCarta(String idP, String id) throws IOException,
			SQLException {
		if (lCarta== null)
			buildMap();
		List<Carta> l = new ArrayList<Carta>();
		synchronized (lCarta) {
			// Tengo la carta
			CartaData aux = (CartaData)lCarta.get(Integer.parseInt(id));
			// Si el usuario existe
			if (aux != null) {
				List<ItemData> auxL = aux.getlProductos();
				Iterator<ItemData> it = auxL.iterator();
				while (it.hasNext()) {
					ItemData i = (ItemData) it.next();
					ProductoServiceMemory p = new ProductoServiceMemory();
					UsuarioServiceMemory u = new UsuarioServiceMemory();
					Usuario auxU = u.getUserByUserId(id);
					Producto auxP = p.verProducto(String.valueOf(i.getIdProducto()));
					l.add(new Carta(i.getIdProducto(), auxU.getNombre(),
							auxP.getNombreProd(), i.getCantidad()));
				}

			}
		}

		return l;
	}

}
