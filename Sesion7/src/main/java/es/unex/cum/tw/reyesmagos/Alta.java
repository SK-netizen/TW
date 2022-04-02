package es.unex.cum.tw.reyesmagos;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
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




/**
 * Ejemplo de uso de Pool de conexiones con una BD con servlet. Importante se
 * carga el pool el init() del servlet y luego se utiliza de forma sincronizada
 * en el processRequest Es necesario configurar el fichero context.xml dentro de
 * META-INF
 * 
 */
@WebServlet("/Alta")
public class Alta extends HttpServlet {

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

		String usuario = null;
		usuario = req.getParameter("username");
		ServletContext contexto = req.getServletContext();
		ResultSet resultados = null;
		String query=null;
		try {
			synchronized (sentencia) {
				// Cogemos todos los datos de la asignaturas
				resultados = sentencia
						.executeQuery("SELECT * FROM usuarios where username='"
								+ usuario + "'");
			}
			if (resultados.next() == true) {
				DevolverHTML(res, true);
			} else {
				synchronized (sentencia) {
					query="INSERT INTO usuarios (nombre,apellidos,email,username,password) " 
									+ "VALUES ('"
									+ req.getParameter("nombre")
									+ "','"
									+ req.getParameter("apellidos")
									+ "','"
									+ req.getParameter("email")
									+ "','"
									+ req.getParameter("username")
									+ "','"
									+ req.getParameter("password") + "')";
					sentencia.executeUpdate(query);					
				}
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

	private void DevolverHTML(HttpServletResponse res, boolean existe)
			throws SQLException, IOException {

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		GenerarWeb g = new GenerarWeb();
		g.generarHead(out);
		g.generarCabecera(out);
		g.generarMenuNoAutenticado(out);
		out.println("<div id='Content'>");
		if (existe == false) {
			out.println("El usuario ha sido registrado");
		} else {
			out.println("<div id='Content'>");
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
