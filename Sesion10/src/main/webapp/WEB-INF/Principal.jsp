<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	if (session.getAttribute("Nombre") == null) {
		response.sendRedirect("Login.jsp");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv='content-type' content='text/html; charset=iso-8859-1' />
<title>Carta a los reyes Magos</title>
<link rel='stylesheet' type='text/css' href='css/CSS.css' />
</head>
<body>
	<%@include file="../Cabecera.html"%>
	<div id='contenedor'>
		<jsp:include page="../Menu_Autenticado.jsp" />
		<div id='Content'>
		<%String mensaje=(String)request.getParameter("mensaje");
		  if (mensaje==null)
			  mensaje=(String)request.getAttribute("mensaje");
		%>		
			<h1><%=mensaje %></h1>
		</div>
	</div>
</body>
</html>
