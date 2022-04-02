<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="javax.naming.Context"%>
<%@ page import="javax.naming.InitialContext"%>
<%@ page import="javax.sql.DataSource"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		Context ctx = new InitialContext();
		DataSource ds = (DataSource) ctx
				.lookup("java:comp/env/jdbc/testdb");
		Connection con = ds.getConnection();
		Statement sentencia = con.createStatement();
		ResultSet rs=null;
		synchronized (sentencia) {
			// Cogemos todos los datos de los productos
			rs = sentencia
					.executeQuery("select * from productos");
		}
		out.println("<table border='1'>");
		out.println("<tr><td><b>Producto</b></td><td><b>Comentarios</b></td></tr>");
		try {
			while (rs.next()) {
				int idProducto = Integer.parseInt(rs.getString("idProductos"));
				out.println("<tr><td><a href=\"VerProducto?idP=" + idProducto
						+ "\">" + rs.getString("nombreProd") + "</a>");
				out.println("</td><td>" + rs.getString("comentarios"));
				out.println("</td><td><a href=\"AddCarrito?idProducto="
						+ idProducto + "\">Añadir al carrito</A>");
			}
			out.println("</td></tr>");

		} catch (SQLException e) {
			e.printStackTrace();

		}
		out.println("</TABLE></P>");
		con.close();
	%>

</body>
</html>


