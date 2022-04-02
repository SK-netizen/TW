<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:useBean id="numero"
	class="es.unex.cum.tw.bean.AdivinarNumero" scope="session"/>
<jsp:setProperty name="numero" property="*" />


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Juego de la adivinanza</title>
</head>
<body>
	<%
		if (numero.getAcierto()) {
	%>

	<p>
		Prueba conseguida tras:
		<%=numero.getIntentos()%></p>
	<%
		numero.resetear();
	%>
	<a href="AdivinarNumero.jsp">Otra vez</a>?
	<%
		} else if (numero.getIntentos() == 0) {
	%>

	<p>Bienvenido a este juego</p>

	<p>Piensa un número del 0 al 100</p>
	<form method=get>
		Introduce un número<input type=text name=probar /> <input type=submit
			value="Submit" />
	</form>

	<%} else {	%>

	<p>Lo siento pero no.</p>
	<b><%=numero.getAyuda()%></b> Llevas:
	<%=numero.getIntentos()%>

	<form method=get>
		Intentalo otra vez 
		<input type=text name=probar> 
		<input type=submit value="Submit" />
	</form>
	<%
		}
	%>
</body>
</html>

