package es.unex.cum.tw.reyesmagos.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import es.unex.cum.tw.reyesmagos.model.Producto;
import es.unex.cum.tw.reyesmagos.service.ProductoService;
import es.unex.cum.tw.reyesmagos.service.ProductoServiceMemory;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Configuracion
 */
@WebServlet("/ProductosController")
public class ProductosController extends HttpServlet {
	/*
	 * private Statement sentencia = null; private Connection conexion = null;
	 * private DataSource servicioConexiones = null;
	 */
	//private ProductoService s = new ProductoServiceMemory();
	private ProductoService s = new ProductoServiceMemory();

	@Override
	public void init(ServletConfig config) throws ServletException {

	}

	public void processRequest(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {

		HttpSession session = req.getSession();

		String id = (String) session.getAttribute("Id");
		if (id != null) {
			String accion = req.getParameter("action");
			if (accion.equals("VerProductos")) {
				verTodos(req, res);
			} else if (accion.equals("VerProducto")) {
				verProducto(req, res);
			} else {
				res.sendRedirect("WEB-INF/Principal.jsp?mensaje=No hay acción");
			}

		} else {
			res.sendRedirect("Error.jsp?error=Problemas con la sesión");
		}
	}

	public void verTodos(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		try {
			
			List<Producto> result = s.verTodos();
			if (result == null) {
				res.sendRedirect("WEB-INF/Principal.jsp?mensaje=ERROR: No hay productos");
				return;
			} else {
				req.setAttribute("lista", result);
				req.getRequestDispatcher("WEB-INF/VerProductos.jsp").forward(req, res);
			}

		} catch (SQLException e2) {
			res.sendRedirect("WEB-INF/Principal.jsp?mensaje=Error en la consulta. Hable con administrador");

		}

	}

	public void verProducto(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		String idP = (String) req.getParameter("idP");
		try {
			
			Producto result = s.verProducto(idP);
			if (result == null) {
				res.sendRedirect("WEB-INF/Principal.jsp?mensaje=ERROR: No hay productos");
				return;
			} else {
				req.setAttribute("producto", result);
				req.getRequestDispatcher("WEB-INF/VerProducto.jsp").forward(req, res);
			}

		} catch (SQLException e2) {
			res.sendRedirect("WEB-INF/Principal.jsp?mensaje=Error en la consulta. Hable con administrador");

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
