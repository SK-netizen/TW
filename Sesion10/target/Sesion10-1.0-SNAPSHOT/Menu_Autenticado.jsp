<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="Menu">
	<form id="form1" action="Action" method="post">
		<a href="javascript:;"
			onclick="document.getElementById('form1').submit();">Ver Productos</a> 
			<input type="hidden" name="action" value="VerProductos" />
	</form>
	<form id="form2" action="Action" method="post">
		<a href="javascript:;"
			onclick="document.getElementById('form2').submit();">Ver Carta</a> 
			<input type="hidden" name="action" value="VerCarta" />
	</form>
	<a href='Action?action=Info' title='Info Usuario'>Info usuario</a><br /> 
	<a href='Logout.jsp' title=''>Logout</a><br /> 
	Usuario:<%=session.getAttribute("Nombre")%>
</div>