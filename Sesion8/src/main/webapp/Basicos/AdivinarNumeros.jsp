<!--AdivinarNumeros.jsp-->

<%! int adivinar = (int) (java.lang.Math.random() * 100)+1;%>
<%! int intentos = 0;%>

<%! /*Este metodo genera un peque�o mensaje de ayuda*/

    public String ayuda(int numero) {
        if (numero > adivinar) {
            return "El numero es menor";
        }
        return "El numero es  mayor";
    }%>

<html>
<head><title>Adivinar el numero</title></head>
<body>
<% String parametro = request.getParameter("adivinar");
    int numero;
    if (parametro != null) {
        numero = Integer.parseInt(parametro);
        intentos++;
    }
    else {
        numero = -1;
    }
    if (parametro != null && adivinar == numero) {%>
<p> �Enhorabuena! Lo has adivinado despues de <%= intentos%> intentos.</p>
<% adivinar = (int) (java.lang.Math.random() * 100)+1;
    intentos = 0;
%>
�Quieres probar otra vez <a href="/contexto/tema12/AdivinarNumeros.jsp"></a>?
<% }
else if (intentos == 0) {%>
<p>Adivina el numero; esta entre uno y 100:</p>
<% }
else {%>
<p>No es ese.  <%= ayuda(numero)%>.</p>
<p>Has hecho un total de <%= intentos%> intentos.</p>
<% }%>

<form method=get>
    �Cual es el numero? <input type=text name="adivinar">
    <input type=submit value="Submit">
</form>


</body>
</html>
