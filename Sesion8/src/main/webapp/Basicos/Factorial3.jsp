
<%--
  Created by IntelliJ IDEA.
  User: sk-netizen
  Date: 29/3/22
  Time: 13:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%! int numero= 0;%>
<%! int resultado= 0;%>
<%! int intermedio= 0;%>

<html>
<head><title>Adivinar el numero</title></head>
<body>
<% String parametro = request.getParameter("adivinar");
    int numero;
    if (parametro != null) {
        numero = Integer.parseInt(parametro);
        if(numero == 1){
            resultado=1;
        }else{%>
<jsp:include page="calculo.jsp">
</jsp:include>
<%
        }
    }
    else {
        /*Error*/
    }%>
Quieres probar otra vez <a href="Basicos/Factorial1.jsp"></a>?
<p>Resultado del factorial es <%= resultado%> .</p>
<form method=get>
    Introducca el numero que desea adivinar <input type=text name="adivinar">
    <input type=submit value="Submit">
</form>
</form>
</body>
</html>
