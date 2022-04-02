<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" session="true" import="java.util.*,es.unex.cum.tw.reyesmagos.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf8"/>
    <title>Carta a los reyes Magos</title>
    <link rel="stylesheet" type="text/css" href="css/CSS.css"/>
</head>
<body>
<%
    if (session.getAttribute("Nombre") == null) {
        response.sendRedirect("Principal.jsp");
    } else {
%>
<%@ include file="../Cabecera.html" %>
<div id="contenedor">
    <jsp:include page="../Menu_Autenticado.jsp"/>
    <div id="Content">
        <%
            Producto p = (Producto) request.getAttribute("producto");
            if (p == null) {
                response.sendRedirect("WEB-INF/Principal.jsp?mensaje=Error con el producto");
            } else {
                String image="./img/"+p.getPathImagen();
        %>

        <img src=<%=image%> alt='Imagen_ddfasdfloductsadfo'>
        <%
            }
        %>
    </div>
</div>
<%} %>
</body>
</html>