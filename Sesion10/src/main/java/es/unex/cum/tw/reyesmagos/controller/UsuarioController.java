package es.unex.cum.tw.reyesmagos.controller;

import java.io.IOException;

import es.unex.cum.tw.reyesmagos.model.Usuario;
import es.unex.cum.tw.reyesmagos.service.UsuarioService;
import es.unex.cum.tw.reyesmagos.service.UsuarioServiceBD;
import es.unex.cum.tw.reyesmagos.service.UsuarioServiceMemory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Ejemplo de uso de Pool de conexiones con una BD con servlet. Importante se
 * carga el pool el init() del servlet y luego se utiliza de forma sincronizada
 * en el processRequest Es necesario configurar el fichero context.xml dentro de
 * META-INF
 * 
 */
@WebServlet("/UsuarioController")
public class UsuarioController extends HttpServlet {
	//private UsuarioService userService = new UsuarioServiceMemory();
	private UsuarioService userService = new UsuarioServiceBD();
	@Override
	public void init(ServletConfig config) throws ServletException {
	}

	public void processRequest(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String accion = req.getParameter("action");
		if (accion.equals("UsuarioLogin")) {
			doLogin(req, res);
		} else if (accion.equals("UsuarioAlta")) {
			darAlta(req, res);
		} else {
			res.sendRedirect("Error.jsp?error=No hay acción");
		}

	}

	public void doLogin(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		String username = req.getParameter("user");
		String passRecibido = req.getParameter("password");
		try {
			
			boolean result = userService.authenticate(username, passRecibido);
			Usuario user = userService.getUserByUsername(username);
			if (result == true) {
				HttpSession session = req.getSession(true);
				session.setAttribute("Id", String.valueOf(user.getId()));
				session.setAttribute("Nombre", user.getNombre());
				session.setAttribute("Username", user.getUsername());
				req.setAttribute("mensaje", "Autenticacion correcta");
				req.getRequestDispatcher("WEB-INF/Principal.jsp").forward(req, res);
			} else {
				res.sendRedirect("Error.jsp?error=Error en Password");
				return;
			}
		} catch (Exception e2) {
			// Error SQL: login/passwd mal
			res.sendRedirect("Error.jsp?error=ERROR:Fallo en SQL");
		}
	}

	public void darAlta(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		String n = req.getParameter("nombre");
		String apel = req.getParameter("apellidos");
		String email = req.getParameter("email");
		String username = req.getParameter("username");
		String pass = req.getParameter("password");
		Usuario usuario1 = new Usuario(-1, n, apel, email, username, pass);

		try {
			boolean result = userService.register(usuario1);
			if (result) {
				req.setAttribute("mensaje", "Dado de alta correctamente");
				req.getRequestDispatcher("Login.jsp").forward(req, res);
				return;
			} else {
				res.sendRedirect("Error.jsp?error=El usuario ya existe");
				return;
			}
		} catch (Exception e2) {
			// Error SQL: login/passwd mal
			res.sendRedirect("Error.jsp?error=ERROR:Fallo en SQL");
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		processRequest(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		processRequest(req, res);
	}

	@Override
	public void destroy() {
	}
}
