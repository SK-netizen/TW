<%@ page autoFlush="true" buffer="16kb" contentType="text/html"
	errorPage="myErrorPage.jsp"
	extends="org.apache.jasper.runtime.HttpJspBase" import="java.sql.*"
	info="Página de ejemplo de directiva" language="java"
	pageEncoding="UTF-8" session="false"%>

<HTML>
<HEAD>
<TITLE>Páagina con directirva</TITLE>
</HEAD>

<BODY>
	<%--@ include file="cabecera.html"--%>
	<%--@ include file="menu.jsp"--%>
	<div id="content">
		<%
			String nombre = request.getParameter("nombre");
			out.println("Tu nombre es: " + nombre);
		%>
	</div>
</BODY>
</HTML>