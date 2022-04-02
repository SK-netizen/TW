<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf8" />
<title>Carta a los reyes Magos</title>
<link rel="stylesheet" type="text/css" href="css/CSS.css" />
</head>
<body>
<%
   if (session.getAttribute("Nombre")!=null)   {
	   response.sendRedirect("Principal.jsp");
   } else {
%>
	<%@ include file="Cabecera.html"%>
	<div id="contenedor">
		<%@ include file="Menu_No_Autenticado.html"%>
		<%
			if (request.getParameter("mensaje") != null) {
				out.println(request.getParameter("error"));
			}
		%>
		<div id="Content">
			<form action="Action" method="post">
				<label>Usuario: </label> <input type="text" name="user" id="user" />
				<br /> <label>Password:</label> <input type="password"
					name="password" id="password" /><br /> <input type="submit"
					value="Validar" />
					<input type="hidden" name="action" value="UsuarioLogin" />
			</form>
			<p>Si no estas dado de alta, pincha <a  href="Registro.html">aqui</a></p>
		</div>
	</div>
	<%} %>
</body>
</html>