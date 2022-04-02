package es.unex.cum.tw.reyesmagos.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;


/**
 * Servlet implementation class Configuracion
 */
@WebServlet("/CartaReyesController")
public class CartaReyesController extends HttpServlet {
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

        ServletContext contexto = req.getServletContext();
        HttpSession session = req.getSession();

        String id = (String) session.getAttribute("Id");
        if (id != null && !id.equals("")) {
            String accion = req.getParameter("action");
            if (accion.equals("anadirCarta")) {
                anadirCarta(req, res, id);
            } else if (accion.equals("VerCarta")) {
                verCarta(req, res);
            } else if (accion.equals("eliminarCarta")) {
                eliminarCarta(req, res);
            } else {
                res.sendRedirect("WEB-INF/Principal.jsp?mensaje=No hay acción");
            }

        } else {
            res.sendRedirect("Error.jsp?error=ERROR: Problema con la sesión");
        }
    }

    public void anadirCarta(HttpServletRequest req, HttpServletResponse res, String idUsusario)
            throws IOException, ServletException {
        ResultSet resultados = null;
        try {
            synchronized (sentencia) {
                // comprobamos si tiene algun producto en ese caso se le suma uno
                // en caso contrario se le añade
                String idProducto = (String) req.getParameter("idProducto");
                String consultaExite = "select cantidad from carta where idCliente=" + idUsusario + " AND idProducto=" + idProducto + ";";
                resultados = sentencia.executeQuery(consultaExite);
                if(resultados.next()==true){
                    crearEntrada(req, res, idUsusario, idProducto, Integer.parseInt(resultados.getString(1)) + 1);
                }else{
                    crearEntrada(req, res, idUsusario, idProducto, 1);
                }
            }
        } catch (Exception e) {
            res.sendRedirect("./WEB-INF/Principal.jsp?mensaje=Error en la consulta. Hable con administrador");
        } finally {
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

    public void crearEntrada(HttpServletRequest req, HttpServletResponse res, String idUsuario, String idProducto, Integer senten) throws IOException {
        ResultSet resultados = null;
        try {
            synchronized (sentencia) {
                if (senten == 1) {
                    String consulta = "INSERT INTO carta VALUES (" + idUsuario + ", " + idProducto + ", 1);";
                    sentencia.executeUpdate(consulta);
                } else {
                    String consulta = "UPDATE carta set cantidad=" + senten.toString() + " where idCliente=" + idUsuario + " AND idProducto=" + idProducto + ";";
                    System.out.println(consulta);
                    sentencia.executeUpdate(consulta);
                }
            }
        } catch (Exception e) {
            res.sendRedirect("WEB-INF/Principal.jsp?mensaje=Error en la consulta. Hable con administrador");
        } finally {
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

    public void verCarta(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        //TODO

    }


    public void eliminarCarta(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        String id = (String) session.getAttribute("Id");
        String idP = (String) req.getParameter("idP");
        ResultSet resultados = null;// Objeto para guardar los resultados
        try {
            synchronized (sentencia) {
                // Cogemos todos los datos de los productos
                resultados = sentencia
                        .executeQuery("select * from carta where idProducto='"
                                + idP + "' and idCliente='" + id + "'");
            }
            if (!resultados.next()) {
                res.sendRedirect("Principal.jsp?mensaje=ERROR: No hay productos");
                return;
            } else {
                synchronized (sentencia) {
                    // Cogemos todos los datos de los productos
                    String query = "delete from carta where idCliente='" + id
                            + "' and idProducto='" + idP + "'";
                    sentencia.executeUpdate(query);
                }
                req.setAttribute("mensaje", "Eliminado del carrito perfectamente");
                req.getRequestDispatcher("WEB-INF/"
                        + "Principal.jsp").forward(req, res);
            }

        } catch (SQLException e2) {
            res.sendRedirect("WEB-INF/Principal.jsp?mensaje=Error en la consulta. Hable con administrador");

        } finally {
            // Se cierra resultSet
            if (resultados != null) {
                try {
                    resultados.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CartaReyesController.class.getName()).log(
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
