package es.unex.cum.tw.reyesmagos.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import es.unex.cum.tw.reyesmagos.model.Producto;

public class ProductoServiceMemory implements ProductoService {
	private static List<Producto> lProductos = null;

	private void buildList() {

		if (lProductos == null) {
			lProductos = new ArrayList<Producto>();
			try {
				URL stream = ProductoServiceMemory.class.getClassLoader().getResource("productos.csv");
				System.out.println(stream.getFile());
				BufferedReader br = new BufferedReader(new InputStreamReader(new
						FileInputStream(stream.getFile())));
				String stringRead = br.readLine();

				while (stringRead != null) {
					StringTokenizer st = new StringTokenizer(stringRead, ",");
					int id = Integer.parseInt(st.nextToken());
					String nombre = st.nextToken();
					String path = st.nextToken();
					/** PROBLEM */
					String Comentarios = st.nextToken();
					/** PROBLEM */
					lProductos.add(new Producto(id, nombre, path, Comentarios));
					// read the next line
					stringRead = br.readLine();
				}
				br.close();
			} catch (IOException ioe) {

				ioe.printStackTrace();

			}
		}
	}

	public List<Producto> verTodos() throws SQLException {
		if (lProductos == null)
			buildList();
		return lProductos;
	}

	public Producto verProducto(String idP) throws IOException, SQLException {
		if (lProductos == null)
			buildList();
		Producto aux = null;
		synchronized (lProductos) {
			Iterator<Producto> it = lProductos.iterator();
			while (it.hasNext()) {
				aux = it.next();
				if (aux.getIdProductos() == Integer.parseInt(idP))
					return aux;
			}
		}
		return null;
	}
}
