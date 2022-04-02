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

		//String valor = request.getParameter("Regalo");
		String action = request.getParameter("accion");

		//ServletContext contexto = request.getServletContext();
		HttpSession s=request.getSession();
		String id = (String) s.getAttribute("Id");
		//Boolean autenticado = (Boolean) contexto.getAttribute("autenticado");
		PrintWriter out = null;
		try {
			if (id!=null && id!=null) {

				//HttpSession s = request.getSession();
				if (action.equals("eliminar")) {
					RequestDispatcher listar = request.getRequestDispatcher("/EliminarCarrito");
					listar.forward(request, response);
				} else if (action.equals("CartaReyes")) {
					RequestDispatcher listar = request.getRequestDispatcher("/CartaReyes");
					listar.forward(request, response);
				} else if (action.equals("VerProductos")) {
					RequestDispatcher listar = request.getRequestDispatcher("/VerProductos");
					listar.forward(request, response);
				} else if (action.equals("VerProducto")) {
					RequestDispatcher listar = request.getRequestDispatcher("/VerProducto");
					listar.forward(request, response);
				} else if (action.equals("Carrito")) {
					RequestDispatcher anhadirServlet = request.getRequestDispatcher("/AddCarrito");
					anhadirServlet.forward(request, response);				
				} else if (action.equals("Logout")) {
					RequestDispatcher anhadirServlet = request.getRequestDispatcher("/Logout");
					anhadirServlet.forward(request, response);				
				}

			} else {
				response.setContentType("text/html;charset=UTF-8");
				out = response.getWriter();
				error_Username(response);
				out.close();
			}	
			
		} finally {
			
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

	private void error_Username(HttpServletResponse res) throws IOException  {
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
		out.flush();	}

}
