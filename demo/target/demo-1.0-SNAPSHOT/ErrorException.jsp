<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true" import="java.io.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ERRROR</title>
</head>
<body>
	<%@ include file="Cabecera.html"%>
	<div id="contenedor">
		<%@ include file="Menu_No_Autenticado.html"%>
		<%
			if (request.getParameter("error") != null) {
				out.println(request.getParameter("error"));
			}
		%>
		<div id="Content">
			Message:
			<%=exception.getMessage()%>


			<%
				StringWriter stringWriter = new StringWriter();
				PrintWriter printWriter = new PrintWriter(stringWriter);
				exception.printStackTrace(printWriter);
				out.println(stringWriter);
				printWriter.close();
				stringWriter.close();
			%>
		</div>
	</div>
</body>
</html>