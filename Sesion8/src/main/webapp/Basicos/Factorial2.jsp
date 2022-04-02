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
<%! public int factorial(int num){
    if(num==1){
        return 1;
    }else{
        return num+factorial(num-1);
    }
};%>


<html>
<head><title>Adivinar el numero</title></head>
<body>
<% String parametro = request.getParameter("adivinar");
    int numero;
    if (parametro != null ) {
       intermedio++;
        try {
            numero = Integer.parseInt(parametro);
            if(numero<0){%>
<jsp:forward page="error.jsp">
    <jsp:param name="error" value="0"/>
</jsp:forward>
<%}
            if(numero <2){
                resultado=numero;
            }else{
                resultado = factorial(numero);
            }
        }catch (Exception e){%>
<jsp:forward page="error.jsp">
    <jsp:param name="error" value="1"/>
</jsp:forward>
<%
        }
    }
    else {
        intermedio++;%>
Quieres probar otra vez <a href="Basicos/Factorial1.jsp"></a>?
<p>Resultado del factorial es <%= resultado%> .</p>
<form method=get>
    Introducca el numero que desea adivinar <input type=text name="adivinar">
    <input type=submit value="Submit">
</form>
<%
    }%>

</form>
</body>
</html>
