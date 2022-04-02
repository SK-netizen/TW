<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<jsp:useBean id="p" scope="page" class="es.unex.cum.tw.bean.HolaMundo"></jsp:useBean>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hola Mundo con Bean</title>
</head>
<body>
	<!-- Se recibe en la petición como parámetro -->
	<!--<jsp:setProperty property="nombre" name="p" param="name" />-->	
	
	<jsp:setProperty property="nombre" name="p" value="name" />
	<h3>
		El nombre almacenado en el Bean es:
		<jsp:getProperty property="nombre" name="p" />
	</h3>
</body>
</html>