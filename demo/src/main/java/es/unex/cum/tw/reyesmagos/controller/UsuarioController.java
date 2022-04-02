package es.unex.cum.tw.reyesmagos.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;



/**
 * Ejemplo de uso de Pool de conexiones con una BD con servlet. Importante se
 * carga el pool el init() del servlet y luego se utiliza de forma sincronizada
 * en el processRequest Es necesario configurar el fichero context.xml dentro de
 * META-INF
 * 
 */
@WebServlet("/UsuarioController")
public class UsuarioController extends HttpServlet {

	private Statement sentencia = null;
	private Connection conexion = null;
	private DataSource servicioConexiones = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
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
		String accion = req.getParameter("action");
		if (accion.equals("UsuarioLogin")) {
			doLogin(req, res);
		} else if (accion.equals("UsuarioAlta")) {
			darAlta(req, res);
		} 
			//TODO infoUsuario
			
		else {
			res.sendRedirect("Error.jsp?error=No hay acción");
		}

	}

	public void infoUsuario(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		//TODO
	}
	
	public void doLogin(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String usuario = req.getParameter("user");
		String passRecibido = req.getParameter("password");
		String passBD = null;
		ResultSet resultados = null;// Objeto para guardar los resultados
		try {
			synchronized (sentencia) {
				// Cogemos todos los datos de la asignaturas
				resultados = sentencia
						.executeQuery("SELECT * FROM usuarios where username='"
								+ usuario + "'");
			}
			if (resultados.next() == false) {
				res.sendRedirect("Error.jsp?error=Usuario o Contraseña erronea");
				return;
			} else {
				passBD = resultados.getString("password");
				if (passRecibido.equals(passBD)) {
					HttpSession session = req.getSession(true);
					String Id_Cliente = resultados.getString("id");
					String Nombre_Cliente = resultados.getString("Nombre");
					String Username = resultados.getString("username");
					session.setAttribute("Id", Id_Cliente);
					session.setAttribute("Nombre", Nombre_Cliente);
					session.setAttribute("Username", Username);					
					req.setAttribute("mensaje", "Autenticacion correcta");
					req.getRequestDispatcher("WEB-INF/Principal.jsp").forward(req, res);
					return;
				} else {
					res.sendRedirect("Error.jsp?error=Error en Password");
					return;
				}
			}

		} catch (SQLException e2) {
			// Error SQL: login/passwd mal
			res.sendRedirect("Error.jsp?error=ERROR:Fallo en SQL");

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
	}

	public void darAlta(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String usuario = null;
		usuario = req.getParameter("username");
		ResultSet resultados = null;
		String query = null;
		try {
			synchronized (sentencia) {
				// Cogemos todos los datos de la asignaturas
				resultados = sentencia
						.executeQuery("SELECT * FROM usuarios where username='"
								+ usuario + "'");
			}
			if (resultados.next() == true) {
				res.sendRedirect("Error.jsp?error=El usuario ya existe");
			} else {
				synchronized (sentencia) {
					query = "INSERT INTO usuarios (nombre,apellidos,email,username,password) "
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
				// TODO
				req.setAttribute("mensaje", "Dado de alta correctamente");
				req.getRequestDispatcher("Login.jsp").forward(req, res);
			}
			return;

		} catch (SQLException e2) {
			// Error SQL: login/passwd mal
			res.sendRedirect("Error.jsp?error=ERROR:Fallo en SQL");

		} finally {
			// Se cierra resultSet
			if (resultados != null) {
				try {
					resultados.close();
				} catch (SQLException ex) {
					Logger.getLogger(UsuarioController.class.getName()).log(
							Level.SEVERE, "No se pudo cerrar el Resulset", ex);
				}
			}
		}

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
			Logger.getLogger(UsuarioController.class.getName()).log(
					Level.SEVERE, "No se pudo cerrar el objeto Statement", ex);
		} finally {
			try {
				conexion.close();
			} catch (SQLException ex) {
				Logger.getLogger(UsuarioController.class.getName()).log(
						Level.SEVERE, "No se pudo cerrar el objeto Conexion",
						ex);
			}
		}
	}
}
