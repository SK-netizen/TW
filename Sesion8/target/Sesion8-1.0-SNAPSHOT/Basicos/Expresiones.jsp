<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Expresiones JSP</title>
</head>
<body>
<H1>Ejemplo de expresiones JSP</H1>
<UL>
    <LI>Fecha actual: <%=new java.util.Date()%>
    <LI>Nombre del host: <%=request.getRemoteHost()%>
    <LI>ID de la sesión: <%=session.getId()%>
    <LI>El parámetro es: <%=request.getParameter("nombre")%>
</UL>
</body>
</html>