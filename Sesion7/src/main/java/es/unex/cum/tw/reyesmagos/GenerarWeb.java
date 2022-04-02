package es.unex.cum.tw.reyesmagos;

import java.io.PrintWriter;

public class GenerarWeb {

	public void generarHead(PrintWriter out) {
		out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta http-equiv='content-type' content='text/html; charset=iso-8859-1' />");
		out.println("<title>Carta a los reyes Magos</title>");
		out.println("<link rel='stylesheet' type='text/css' href='css/CSS.css' />");
		out.println("</head>");
		out.println("<body>");
	}

	public void generarCabecera(PrintWriter out) {
		out.println("<div id='Header'>");
		out.println("<img src='img/11818262-navidad-reyes-magos.jpg'></img>");
		out.println("</div>");
	}

	public void generarMenuAutenticado(PrintWriter out, String user) {
		out.println("<div id='contenedor'>");
		out.println("<div id='Menu'>");
		out.println("<a href='Action?accion=VerProductos' title='Listar'>Ver Productos</a><br />");
		out.println("<a	href='Action?accion=CartaReyes' title='Carta Reyes'>Carta a los Reyes</a><br />");
		out.println("<a	href='InfoUsuario' title='Info Usuario'>Info usuario</a><br />");
		out.println("<a href='Action?accion=Logout' title=''>Logout</a><br />");
		out.println("---------<br/>");
		out.println("Usuario:"+user);
		out.println("</div>");
	}
	public void generarMenuNoAutenticado(PrintWriter out) {
		out.println("<div id='contenedor'>");
		out.println("<div id='Menu'>");
		out.println("<a href='Autenticar.html' title='Entrada'>Login</a><br />");
		out.println("</div>");
	}

	public void generarCerrarContenedor(PrintWriter out) {
		out.println("</div>");
	}

	public void generarCierreBody(PrintWriter out) {
		out.println("</body>");
		out.println("</html>");
	}

}
