package es.unex.cum.tw.reyesmagos;

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
		HttpSession session =request.getSession();
		if(session.getAttribute("Autenticado")!=null){
			session.invalidate();
			DevolverHTML(response.getWriter(), session);
		}else{
			Error_Username(response.getWriter());
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
		g.generarMenu(out);
		out.println("<div id='Content'>");
		out.println("<p>Desconectado correctamente. Para volver a entrar <a href='Inicio.html'>Aquí</a></p>");
		out.println("<p>CSS obtenida de <a href='http://bluerobot.com/'	title='BlueRobot Home'>BLUEROBOT.COM</a></p>");
		out.println("</div>");
		g.generarCerrarContenedor(out);
		g.generarCierreBody(out);
		out.flush();
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
