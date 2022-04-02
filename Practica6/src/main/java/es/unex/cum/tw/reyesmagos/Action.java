package es.unex.cum.tw.reyesmagos;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * Servlet implementation class Sesiones
 */
@WebServlet("/Action")
public class Action extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession s = request.getSession();

		if (s.getAttribute("Autenticado") == null) {
			response.sendRedirect("error.html");
		}
		String accion = request.getParameter("accion");
		if (accion.equals("anadir")) {
			RequestDispatcher anhadirServlet = request
					.getRequestDispatcher("Anadir");
			anhadirServlet.forward(request, response);

		} else if (accion.equals("listar")) {

		} else {
			response.sendRedirect("error.html");
		}
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void Error_Username(PrintWriter out) {
		GenerarWeb g = new GenerarWeb();
		g.generarHead(out);
		g.generarCabecera(out);
		g.generarMenu(out);
		out.println("<div id='Content'>");
		out.println("<h1>Problemas con la autenticación</h1>");
		out.println("<p>Si quieres volver a intentarlo, pulse <a href='Autenticar.html'>Aquí</a></p>");
		out.println("<p>CSS obtenida de <a href='http://bluerobot.com/'	title='BlueRobot Home'>BLUEROBOT.COM</a></p>");
		out.println("</div>");
		g.generarCerrarContenedor(out);
		g.generarCierreBody(out);
		out.flush();
	}

}
