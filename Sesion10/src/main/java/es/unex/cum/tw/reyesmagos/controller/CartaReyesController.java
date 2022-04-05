package es.unex.cum.tw.reyesmagos.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import es.unex.cum.tw.reyesmagos.model.Carta;
import es.unex.cum.tw.reyesmagos.service.CartaService;
import es.unex.cum.tw.reyesmagos.service.CartaServiceBD;
import es.unex.cum.tw.reyesmagos.service.CartaServiceMemory;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Configuracion
 */
@WebServlet("/CartaReyesController")
public class CartaReyesController extends HttpServlet {
	/*private Statement sentencia = null;
	private Connection conexion = null;
	private DataSource servicioConexiones = null;*/

	//private CartaService c= new CartaServiceMemory();
	private CartaService c= new CartaServiceMemory();
	@Override
	public void init(ServletConfig config) throws ServletException {
	}

	public void processRequest(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {

		ServletContext contexto = req.getServletContext();
		HttpSession session = req.getSession();

		String id = (String) session.getAttribute("Id");
		if (id != null && !id.equals("")) {
			String accion = req.getParameter("action");
			if (accion.equals("anadirCarta")) {
				anadirCarta(req, res);
			} else if (accion.equals("VerCarta")) {
				verCarta(req, res);
			} else if (accion.equals("eliminarCarta")) {
				eliminarCarta(req, res);
			} else {
				res.sendRedirect("WEB-INF/Principal.jsp?mensaje=No hay acción");
			}

		} else {
			res.sendRedirect("Error.jsp?error=ERROR: Problema con la sesión");
		}
	}

	public void anadirCarta(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {

		String idP = (String) req.getParameter("idProducto");
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("Id");
		String query="";
		try {

			
			if (!c.anadirCarta(idP, id)) {
				res.sendRedirect("WEB-INF/Principal.jsp?mensaje=No hay productos");
				return;
			} else {
				req.setAttribute("mensaje", "Añadido al carrito perfectamente");
				req.getRequestDispatcher("WEB-INF/Principal.jsp").forward(req, res);
			}

		} catch (SQLException e2) {			
			res.sendRedirect("WEB-INF/Principal.jsp?mensaje=Error en la consulta. Hable con administrador");
			

		} 
	}

	public void eliminarCarta(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("Id");
		String idP = (String) req.getParameter("idP");
		try {
			
			if (!c.eliminarCarta(idP, id)) {
				res.sendRedirect("WEB-INF/Principal.jsp?mensaje=ERROR: No hay productos");
				return;
			} else {
				req.setAttribute("mensaje", "Eliminado del carrito perfectamente");
				req.getRequestDispatcher("WEB-INF/Principal.jsp").forward(req, res);
			}

		} catch (SQLException e2) {
			res.sendRedirect("WEB-INF/Principal.jsp?mensaje=Error en la consulta. Hable con administrador");

		} 
	}

	public void verCarta(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("Id");
		String idP = (String) req.getParameter("idP");
		
		try {
//			String query="";
//			query="SELECT idProducto, nombre, nombreProd, cantidad from carta JOIN usuarios ON ( idCliente = id)"
//					+" join productos ON (carta.idProducto=productos.idProductos) where idCliente="+id;
//		
		
			List<Carta> result = c.verCarta(idP, id);
			if (result==null) {
				res.sendRedirect("WEB-INF/Principal.jsp?mensaje=ERROR: No hay productos");
				return;
			} else {
				req.setAttribute("lista", result);
				req.getRequestDispatcher("WEB-INF/VerCarta.jsp").forward(req, res);
			}

		} catch (SQLException e2) {
			res.sendRedirect("WEB-INF/Principal.jsp?mensaje=ERROR: Error en la consulta. Hable con administrador");

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
