<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:useBean id="c" scope="page" class="es.unex.cum.tw.bean.Calculadora" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ejemplo Calculadora</title>
</head>
<body>

	<jsp:setProperty property="op1" name="c"
		value="<%=Integer.parseInt(request.getParameter(\"ope1\"))%>" />
	<jsp:setProperty property="op2" name="c"
		value="<%=Integer.parseInt(request.getParameter(\"ope2\"))%>" />
	<h3>
		El resultado de la suma es:
		<jsp:getProperty property="suma" name="c" />

	</h3>
</body>
</html>