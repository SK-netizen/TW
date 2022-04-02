package es.unex.cum.tw.reyesmagos;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Servlet implementation class Configuracion
 */
@WebServlet("/VerProducto")
public class VerProducto extends HttpServlet {
    private Statement sentencia = null;
    private Connection conexion = null;
    private DataSource servicioConexiones = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            // Recuperar el contexto inicial
            Context ctx = new InitialContext();
            // Referencia al servicio de conexiones
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            servicioConexiones = (DataSource) envContext.lookup("jdbc/testdb");
            conexion = servicioConexiones.getConnection();
            sentencia = conexion.createStatement();
        } catch (Exception e) {
            throw new ServletException(
                    "Imposible recuperar java:comp/env/jdbc/testdb", e);
        }
    }

    public void processRequest(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        HttpSession session = req.getSession();
        if (session.getAttribute("Id") != null && session.getAttribute("Id") != "") {
            ResultSet resultado = null;
            String idProducto = (String) req.getParameter("idP");
            if (idProducto != null) {
                try {
                    String query = "SELECT * FROM productos where idProductos='" + idProducto+"'";
                    System.out.println(query);
                    resultado = sentencia.executeQuery(query);
                    DevolverHTML(req,res,resultado);
                } catch (Exception e) {
                    error_Username(res);
                }
            }
        } else {
            error_Username(res);
        }
    }

    private void DevolverHTML(HttpServletRequest req, HttpServletResponse res,
                              ResultSet rs) throws IOException {
        String imagespath = "./img/";
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        GenerarWeb g = new GenerarWeb();
        g.generarHead(out);
        g.generarCabecera(out);
        g.generarMenuAutenticado(out,
                (String) req.getSession().getAttribute("Nombre"));

        out.println("<div id='Content'>");
        try {

            out.println("<h1>Detalle del producto</h1>");
            out.println("<p align=\"center\">");
            out.println("<table border='2'>");
            out.println("<tr><td><p><b>Nombre: </b>" + rs.getString("nombreProd") + "</p>");
            out.println("<p><b>Descripción: </b>" + rs.getString("comentarios") + "</p><br/>");
            out.println("<td><img border='0' src=\"" + imagespath + rs.getString("pathImagen") + "\" width='200' height='150'/></td>");
            out.println("</tr></table>");
            out.println("</div>");

        } catch (SQLException e) {
            out.println(e.getMessage());

        }

        g.generarCerrarContenedor(out);
        g.generarCierreBody(out);
        out.flush();
    }

    private void error_Username(HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        GenerarWeb g = new GenerarWeb();
        g.generarHead(out);
        g.generarCabecera(out);
        g.generarMenuNoAutenticado(out);
        out.println("<div id='Content'>");
        out.println("<h1>Problemas con la autenticación</h1>");
        out.println("<p>Si quieres volver a intentarlo, pulse <a href='Autenticar.html'>Aquí</a></p>");
        out.println("<p>CSS obtenida de <a href='http://bluerobot.com/'	title='BlueRobot Home'>BLUEROBOT.COM</a></p>");
        out.println("</div>");
        g.generarCierreBody(out);
        out.flush();
    }

    private void errorNoHayProductos(HttpServletRequest req,
                                     HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        GenerarWeb g = new GenerarWeb();
        g.generarHead(out);
        g.generarCabecera(out);
        g.generarMenuAutenticado(out,
                (String) req.getSession().getAttribute("Nombre"));
        out.println("<div id='Content'>");
        out.println("No hay productos");
        out.println("</div>");
        g.generarCerrarContenedor(out);
        g.generarCierreBody(out);
        out.flush();
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

}
