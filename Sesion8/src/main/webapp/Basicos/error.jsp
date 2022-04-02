<%--
  Created by IntelliJ IDEA.
  User: sk-netizen
  Date: 29/3/22
  Time: 13:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error !!!!!</title>
</head>
<body>
<h1>Error :s</h1>
<%! int typeError =0; %>
<%
    typeError = Integer.parseInt(request.getParameter("error"));
    if(typeError==0){
        %>
<p>Has introducido un numero negativo</p>
<%
    }else{
        if(typeError==2){
%>

<p>No has introducido nada</p>

<%
        }else{
%>

<p>Has introducido un caracter no numero</p>

<%

        }
    }
%>

</body>
</html>
