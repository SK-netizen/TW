package es.unex.cum.tw.reyesmagos.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



/**
 * Servlet implementation class Configuracion
 */
@WebServlet("/Action")
public class Action extends HttpServlet {
	
	public void processRequest(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
				String accion = req.getParameter("action");
		if (accion.contains("Producto")) {
			RequestDispatcher productos= getServletContext().getRequestDispatcher("/ProductosController");
			productos.forward(req, res);
		} else if (accion.contains("Usuario")) {
			req.getRequestDispatcher("/UsuarioController").forward(req, res);
		} else if (accion.contains("Carta")) {
			req.getRequestDispatcher("/CartaReyesController").forward(req, res);
		}
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
