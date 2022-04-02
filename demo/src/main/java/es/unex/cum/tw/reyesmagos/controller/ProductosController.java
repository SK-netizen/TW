package es.unex.cum.tw.reyesmagos.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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

import es.unex.cum.tw.reyesmagos.model.Producto;


/**
 * Servlet implementation class Configuracion
 */
@WebServlet("/ProductosController")
public class ProductosController extends HttpServlet {
    private Statement sentencia = null;
    private Connection conexion = null;
    private DataSource servicioConexiones = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            servicioConexiones = (DataSource) envContext.lookup("jdbc/testdb");
            conexion = servicioConexiones.getConnection();
            sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            throw new ServletException(
                    "Imposible recuperar java:comp/env/jdbc/testdb", e);
        }
    }

    public void processRequest(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        HttpSession session = req.getSession();

        String id = (String) session.getAttribute("Id");
        if (id != null) {
            String accion = req.getParameter("action");
            if (accion.equals("VerProductos")) {
                verTodos(req, res);
            } else if (accion.equals("VerProducto")) {
                verProducto(req, res);
            } else {
                res.sendRedirect("WEB-INF/Principal.jsp?mensaje=No hay acción");
            }

        } else {
            res.sendRedirect("Error.jsp?error=Problemas con la sesión");
        }
    }

    public void verTodos(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        ResultSet resultados = null;// Objeto para guardar los resultados

        try {
            synchronized (sentencia) {
                // Cogemos todos los datos de los productos
                resultados = sentencia.executeQuery("select * from productos");
            }
            if (!resultados.next()) {
                res.sendRedirect("WEB-INF/Principal.jsp?mensaje=ERROR: No hay productos");
                return;
            } else {
                resultados.beforeFirst();
                ArrayList l = new ArrayList();
                while (resultados.next()) {
                    l.add(new Producto(
                            Integer.parseInt(resultados.getString(1)),
                            resultados.getString(2), resultados.getString(3),
                            resultados.getString(4)));

                }
                req.setAttribute("lista", l);
                req.getRequestDispatcher("WEB-INF/VerProductos.jsp").forward(req, res);
            }

        } catch (SQLException e2) {
            res.sendRedirect("WEB-INF/Principal.jsp?mensaje=Error en la consulta. Hable con administrador");
        } finally {
            // Se cierra resultSet
            if (resultados != null) {
                try {
                    resultados.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductosController.class.getName()).log(
                            Level.SEVERE, "No se pudo cerrar el Resulset", ex);
                }
            }
        }

    }

    public void verProducto(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        ResultSet resultado = null;
        try {
            synchronized (sentencia) {
                String sent = "select * from productos where idProductos='" + req.getParameter("idP") + "';";
                resultado = sentencia.executeQuery(sent);
            }
            if (!resultado.next()) {
                res.sendRedirect("WEB-INF/Principal.jsp?mensaje=ERROR: No hay productos");
                return;
            } else {
                resultado.beforeFirst();
                Producto p = null;
                while (resultado.next()) {
                    p = new Producto(
                            Integer.parseInt(resultado.getString(1)),
                            resultado.getString(2), resultado.getString(3),
                            resultado.getString(4));
                }
                req.setAttribute("producto", p);
                req.getRequestDispatcher("WEB-INF/VerProducto.jsp").forward(req, res);
            }
        } catch (Exception e) {
            res.sendRedirect("WEB-INF/Principal.jsp?mensaje=Error en la consulta. Hable con administrador");
        } finally {
            // Se cierra resultSet
            if (resultado != null) {
                try {
                    resultado.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductosController.class.getName()).log(
                            Level.SEVERE, "No se pudo cerrar el Resulset", ex);
                }
            }
        }
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
