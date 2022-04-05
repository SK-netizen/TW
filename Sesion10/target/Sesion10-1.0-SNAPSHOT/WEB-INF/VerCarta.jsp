<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"
	import="java.util.*,es.unex.cum.tw.reyesmagos.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf8" />
<title>Carta a los reyes Magos</title>
<link rel="stylesheet" type="text/css" href="css/CSS.css" />
</head>
<body>
	<%
		if (session.getAttribute("Nombre") == null) {
			response.sendRedirect("WEB-INF/Principal.jsp");
		} else {
	%>
	<%@ include file="../Cabecera.html"%>
	<div id="contenedor">
		<jsp:include page="../Menu_Autenticado.jsp" />
		<%
			if (request.getParameter("error") != null) {
					out.println(request.getParameter("error"));
				}
		%>
		<div id="Content">
			<%
				ArrayList lista = (ArrayList) request.getAttribute("lista");
			%>
			<h1>Productos</h1>
			<p>
			<table border='1'>
				<tr>
					<td><b>Usuario</b></td>
					<td><b>Nombre</b></td>
					<td><b>Cantidad</b></td>
					<td><b>Eliminar?</b></td>
				</tr>
				<%
					for (int i = 0; i < lista.size(); i++) {
							Carta c = (Carta) lista.get(i);	%>
				<tr><td><%= c.getNombre() %></td>
					<td><%= c.getNombreProd() %></td>
					<td><%= c.getCantidad() %></td>
					<td><a href="Action?action=eliminarCarta&idP=<%= c.getIdProducto() %>"><img
							border='0' src="img/eliminar.jpg"  width='30' height='30' /></a>
				</tr>


				<%
					}
				%>
				</td>
				</tr>

			</table>
		</div>
	</div>
	<%
		}
	%>
</body>
</html>