<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true" import="java.util.*,es.unex.cum.tw.reyesmagos.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf8" />
<title>Carta a los reyes Magos</title>
<link rel="stylesheet" type="text/css" href="css/CSS.css" />
</head>
<body>
<%
   if (session.getAttribute("Nombre")==null)   {
	   response.sendRedirect("Principal.jsp");
   } else {
%>
	<%@ include file="../Cabecera.html"%>
	<div id="contenedor">
		<jsp:include page ="../Menu_Autenticado.jsp"/>
		<%
			if (request.getParameter("error") != null) {
				out.print(request.getParameter("error"));
			}
		%>
		<div id="Content">
		<%
			ArrayList lista=(ArrayList)request.getAttribute("lista");%>			
			<h1>Productos</h1>
			<p><table border='1'>
			<tr><td><b>Producto</b></td><td><b>Comentarios</b></td></tr>
			<%
			
			for (int i=0;i<lista.size();i++){
				 Producto p=(Producto)lista.get(i);
				 
				%>
				<tr><td><a href="Action?action=VerProducto&idP=<%= p.getIdProductos()%>"><%=p.getNombreProd() %></a>
				</td><td><%=p.getComentarios()%>
				</td><td><a href="Action?action=anadirCarta&idProducto=<%= p.getIdProductos()%>">Añadir al carrito</a>
			<%}%>
			</td></tr>
			
			</table>
			</div>
	</div>
	<%} %>
</body>
</html>