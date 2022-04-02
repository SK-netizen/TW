<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>HelloWorldName</title>
</head>
<body>
	<h1>Hola Mundo</h1>
	<%
		String nombre = request.getParameter("nombre");
		out.println("Tu nombre es: " + nombre);
	%>
</body>
</html>