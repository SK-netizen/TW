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
import java.sql.Statement;



/**
 * Servlet implementation class Configuracion
 */
@WebServlet("/EliminarCarrito")
public class EliminarCarrito extends HttpServlet {
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
	}

	private void DevolverHTML(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		GenerarWeb g = new GenerarWeb();
		g.generarHead(out);
		g.generarCabecera(out);
		g.generarMenuAutenticado(out,
				(String) req.getSession().getAttribute("Nombre"));
		out.println("<div id='Content'>");
		out.println("<h1>El producto se ha eliminado correctamente </h1>");
		out.println("</div>");
		g.generarCerrarContenedor(out);
		g.generarCierreBody(out);
		out.flush();
	}

	private void error_Username(HttpServletResponse res) throws IOException {
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
	private void errorNoHayProductos(HttpServletRequest req,
			HttpServletResponse res) throws IOException {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		GenerarWeb g = new GenerarWeb();
		g.generarHead(out);
		g.generarCabecera(out);
		g.generarMenuAutenticado(out,
				(String) req.getSession().getAttribute("Nombre"));
		out.println("<div id='Content'>");
		out.println("Problema al eliminar el producto");
		out.println("</div>");
		g.generarCerrarContenedor(out);
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
