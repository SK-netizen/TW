package es.unex.cum.tw.reyesmagos.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import es.unex.cum.tw.reyesmagos.model.Producto;

public interface ProductoService {
	public List<Producto> verTodos() throws SQLException;

	public Producto verProducto(String idP) throws IOException, SQLException;
}
