package es.unex.cum.tw.reyesmagos;


import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Servlet implementation class Configuracion
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private String user = null;
	private String password = null;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext context = getServletContext();
		String ficheroUsuarios = context.getInitParameter("ficheroUsuarios");

		InputStream is = context.getResourceAsStream(ficheroUsuarios);
		Properties ficheroPropiedades = new Properties();
		try {
			ficheroPropiedades.load(is);
			user = ficheroPropiedades.getProperty("UserAdmin");
			password = ficheroPropiedades.getProperty("PasswordAdmin");
		} catch (Exception ex) {
			Logger.getLogger(Login.class.getName())
					.log(Level.SEVERE, "No se pudo cargar el fichero con los password", ex);
		}
	}

	public void processRequest(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {

		String usuario = req.getParameter("user");
		String passRecibido = req.getParameter("password");
		HttpSession s = req.getSession();

		if (usuario != null && usuario.equals(user)
				&& password.equals(passRecibido)) {
			s.setAttribute("Autenticado", true);
			//DevolverHTML(res.getWriter());
			res.sendRedirect("Introducir.html");
		} else {
			
			res.sendRedirect("error.html");
		}
	}

	private void DevolverHTML(PrintWriter out) {
		GenerarWeb g = new GenerarWeb();
		g.generarHead(out);
		g.generarCabecera(out);
		g.generarMenu(out);
		out.println("<div id='Content'>");
		out.println("<h1>autenticación correcta</h1>");
		out.println("<form method='get' action='Action' name='datos'>");
		out.println("Regalo: <input name='Regalo'> <br> <input name='accion' value='anadir' type='hidden'><br>");
		out.println("<button>Añadir regalo a la carta</button>");
		out.println("</form>");
		out.println("<form method='get' action='Action'>");
		out.println("<input name='accion' value='listar' type='hidden'");
		out.println("<button>Listar Regalos</button>");
		out.println("</form>");
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
