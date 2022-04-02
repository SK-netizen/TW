package es.unex.cum.tw.reyesmagos;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;



@WebServlet("/PruebaConexion")
public class PruebaConexion extends HttpServlet {

	private Statement sentencia = null;
	private Connection conexion = null;
	private DataSource servicioConexiones = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			// Recuperar el contexto inicial
			Context ctx = new InitialContext();
			// Referencia al servicio de conexiones
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			servicioConexiones = (DataSource) envContext.lookup("jdbc/testdb");
			conexion = servicioConexiones.getConnection();
			sentencia = conexion.createStatement();
		} catch (Exception e) {
			throw new ServletException(
					"Imposible recuperar java:comp/env/jdbc/testdb", e);
		}
	}

	public void processRequest(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		ResultSet resultados = null;
		String query=null;
		try {
			synchronized (sentencia) {
				// Cogemos todos los datos de la asignaturas
				resultados = sentencia
						.executeQuery("SELECT * FROM productos");
			}
			if (resultados.next() == true) {
				DevolverHTML(res, true);
			} else {
				DevolverHTML(res, false);
			}
			return;

		} catch (SQLException e2) {
			// Error SQL: login/passwd mal
			res.setContentType("text/html");
			PrintWriter out = res.getWriter();
			out.println("ERROR:Fallo en SQL: " + e2.getMessage()+"\n"+query);

		} finally {
			// Se cierra resultSet
			if (resultados != null) {
				try {
					resultados.close();
				} catch (SQLException ex) {
					Logger.getLogger(Alta.class.getName()).log(Level.SEVERE,
							"No se pudo cerrar el Resulset", ex);
				}
			}
		}

	}

	private void DevolverHTML(HttpServletResponse res, boolean bien)
			throws SQLException, IOException {

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		GenerarWeb g = new GenerarWeb();
		g.generarHead(out);
		g.generarCabecera(out);
		g.generarMenuNoAutenticado(out);
		out.println("<div id='Content'>");
		if (bien == true) {
			out.println("Base de datos configurada");
		} else {
			out.println("Base de datos mal configurada");
		}
		out.println("</div>");
		g.generarCierreBody(out);
		out.flush();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		processRequest(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		processRequest(req, res);
	}

	@Override
	public void destroy() {
		try {
			sentencia.close();
		} catch (SQLException ex) {
			Logger.getLogger(Alta.class.getName()).log(Level.SEVERE,
					"No se pudo cerrar el objeto Statement", ex);
		} finally {
			try {
				conexion.close();
			} catch (SQLException ex) {
				Logger.getLogger(Alta.class.getName()).log(Level.SEVERE,
						"No se pudo cerrar el objeto Conexion", ex);
			}
		}
	}
}
