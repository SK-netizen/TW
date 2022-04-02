package es.unex.cum.tw.reyesmagos;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;



/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request,
								  HttpServletResponse response) throws ServletException, IOException {

		ServletContext contexto = request.getServletContext();
		HttpSession session = request.getSession();
		PrintWriter out = null;
		try {

			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			String id = (String) session.getAttribute("Id");
			if (id != null && !id.equals("")) {
				session.invalidate();
				DevolverHTML(out, session);
			} else {
				Error_Username(out);

			}

			out.flush();
			out.close();

		} finally {
			out.close();
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void DevolverHTML(PrintWriter out, HttpSession s) {

		GenerarWeb g = new GenerarWeb();
		g.generarHead(out);
		g.generarCabecera(out);
		g.generarMenuNoAutenticado(out);
		out.println("<div id='Content'>");
		out.println("<p>Desconectado correctamente. Para volver a entrar <a href='/Practica8_ReyesMagos/index.html'>Aquí</a></p>");
		out.println("<p>CSS obtenida de <a href='http://bluerobot.com/'	title='BlueRobot Home'>BLUEROBOT.COM</a></p>");
		out.println("</div>");
		g.generarCierreBody(out);
		out.flush();
	}

	private void Error_Username(PrintWriter out) {
		GenerarWeb g = new GenerarWeb();
		g.generarHead(out);
		g.generarCabecera(out);
		g.generarMenuNoAutenticado(out);
		out.println("<div id='Content'>");
		out.println("<h1>Problemas con la autenticación</h1>");
		out.println("<p>Si quieres volver a intentarlo, pulse <a href='/Practica8_ReyesMagos/Autenticar.html'>Aquí</a></p>");
		out.println("<p>CSS obtenida de <a href='http://bluerobot.com/'	title='BlueRobot Home'>BLUEROBOT.COM</a></p>");
		out.println("</div>");
		g.generarCierreBody(out);
		out.flush();
	}
}
