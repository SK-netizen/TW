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
import javax.servlet.http.HttpSession;
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
 * Servlet implementation class Configuracion
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
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
			throws IOException, ServletException {

		String usuario = req.getParameter("user");
		String passRecibido = req.getParameter("password");
		ServletContext contexto = req.getServletContext();

		String passBD = null;

		ResultSet resultados = null;// Objeto para guardar los resultados

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		try {
		synchronized (sentencia) {
				// Cogemos todos los datos de la asignaturas
				resultados = sentencia
						.executeQuery("SELECT * FROM usuarios where username='"+ usuario + "'");
			}
			if (resultados.next() == false) {
				error_Username(res);
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
					DevolverHTML(req, res);
					return;
				} else {
					Error_Password(res);
					return;
				}
			}

		} catch (SQLException e2) {
			// Error SQL: login/passwd mal
			out.println("ERROR:Fallo en SQL: " + e2.getMessage());

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

	private void DevolverHTML(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		GenerarWeb g = new GenerarWeb();
		g.generarHead(out);
		g.generarCabecera(out);
		g.generarMenuAutenticado(out,(String)req.getSession().getAttribute("Nombre"));
		out.println("<div id='Content'>");
		out.println("<h1>Autenticación correcta</h1>");
		out.println("</div>");
		g.generarCerrarContenedor(out);
		g.generarCierreBody(out);
		out.flush();
	}

	private void error_Username (HttpServletResponse res) throws IOException {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		GenerarWeb g = new GenerarWeb();
		g.generarHead(out);
		g.generarCabecera(out);
		g.generarMenuNoAutenticado(out);
		out.println("<div id='Content'>");
		out.println("<h1>Problemas con la autenticación</h1>");
		out.println("<p>Si quieres volver a intentarlo, pulse <a href='Autenticar.html'>Aquí</a></p>");
		out.println("<p>CSS obtenida de <a href='http://bluerobot.com/'	title='BlueRobot Home'>BLUEROBOT.COM</a></p>");
		out.println("</div>");
		g.generarCierreBody(out);
		out.flush();
	}

	private void Error_Password(HttpServletResponse res) throws IOException {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		GenerarWeb g = new GenerarWeb();
		g.generarHead(out);
		g.generarCabecera(out);
		g.generarMenuNoAutenticado(out);
		out.println("<div id='Content'>");
		out.println("<h1>Problemas con la autenticación</h1>");
		out.println("<p>Si quieres volver a intentarlo, pulse <a href='Autenticar.html'>Aquí</a></p>");
		out.println("<p>CSS obtenida de <a href='http://bluerobot.com/'	title='BlueRobot Home'>BLUEROBOT.COM</a></p>");
		out.println("</div>");
		g.generarCierreBody(out);
		out.flush();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
