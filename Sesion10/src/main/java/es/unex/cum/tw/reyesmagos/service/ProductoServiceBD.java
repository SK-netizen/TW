package es.unex.cum.tw.reyesmagos.service;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.unex.cum.tw.reyesmagos.controller.ProductosController;
import es.unex.cum.tw.reyesmagos.model.Producto;
import util.ConexionUtil;

public class ProductoServiceBD implements ProductoService{
	public List<Producto> verTodos() throws SQLException {
		ResultSet resultados = null;// Objeto para guardar los resultados
		List<Producto> l = null;
		try {
			Statement sentencia = ConexionUtil.openStatement();
			synchronized (sentencia) {
				// Cogemos todos los datos de los productos
				resultados = sentencia.executeQuery("select * from productos");
			}
			if (!resultados.next()) {
				return null;
			} else {
				l = new ArrayList<Producto>();
				resultados.beforeFirst();
				while (resultados.next()) {
					l.add(new Producto(
							Integer.parseInt(resultados.getString(1)),
							resultados.getString(2), resultados.getString(3),
							resultados.getString(4)));
				}

			}

		} catch (SQLException e2) {
			throw e2;

		} finally {
			// Se cierra resultSet
			if (resultados != null) {
				try {
					resultados.close();
				} catch (SQLException ex) {
					Logger.getLogger(ProductosController.class.getName()).log(
							Level.SEVERE, "No se pudo cerrar el Resulset", ex);
				}
			}
		}
		return l;
	}

	public Producto verProducto(String idP) 	throws IOException, SQLException {
		ResultSet resultados = null;// Objeto para guardar los resultados
		Producto aux=null;
		try {

			//TODO
			
//		} catch (SQLException e2) {
//			throw e2;

		} finally {
			// Se cierra resultSet
			if (resultados != null) {
				try {
					resultados.close();
				} catch (SQLException ex) {
					Logger.getLogger(ProductosController.class.getName()).log(
							Level.SEVERE, "No se pudo cerrar el Resulset", ex);
				}
			}
		}
		return aux;
	}
}
