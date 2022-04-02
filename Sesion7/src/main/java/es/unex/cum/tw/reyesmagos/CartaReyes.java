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



/**
 * Servlet implementation class Configuracion
 */
@WebServlet("/CartaReyes")
public class CartaReyes extends HttpServlet {
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
			sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (Exception e) {
			throw new ServletException(
					"Imposible recuperar java:comp/env/jdbc/testdb", e);
		}
	}

	public void processRequest(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
	}

	private void DevolverHTML(HttpServletRequest req, HttpServletResponse res,
			ResultSet rs) throws IOException {
		String imagespath = "./img/";
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		GenerarWeb g = new GenerarWeb();
		g.generarHead(out);
		g.generarCabecera(out);
		g.generarMenuAutenticado(out,
				(String) req.getSession().getAttribute("Nombre"));
		out.println("<div id='Content'>");
		out.println("<h1>Productos en la carta</h1>");
		out.println("<p align=\"center\">");
		out.println("<table border='1'>");
		out.println("<tr><td><b>Usuario</b></td><td><b>Nombre</b></td><td><b>Cantidad</b></td></tr>");
		try {
			while (rs.next()) {
				out.println("<tr><td>" + rs.getString("nombre") + "</td>");
				out.println("<td>" + rs.getString("nombreProd") + "</td>");
				out.println("<td>" + rs.getString("cantidad") + "</td>");
				out.println("<td><a href=\"Action?accion=EliminarCarrito&idP="
						+ rs.getString("idProducto")
						+ "\"><img border='0' src=\"" + imagespath
						+ "eliminar.jpg\" width='30' height='30'/></a>");
				out.println("</tr>");
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		out.println("</table></p>");
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
		out.println("No hay productos");
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
