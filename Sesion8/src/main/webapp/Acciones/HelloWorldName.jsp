<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<h1>Hola Mundo</h1>
<%
	String nombre = request.getParameter("nombre");
	out.println("Tu nombre es: " + nombre);
%>
