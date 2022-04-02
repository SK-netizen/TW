<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%!int contar = 0;%>
<%!public String mostrarNombre(String name) {
		if (name.equals("Luis")) {
			return ("Tu nombre es " + name);
		} else {
			return ("Tu nombre no es Luis");
		}
	}%>
<h1>Hola Mundo</h1>
<%
	String nombre = request.getParameter("nombre");
	out.println(mostrarNombre(nombre));
	contar += 1;
%>
<h1>
	Eres el visitante<%=contar%></h1>
