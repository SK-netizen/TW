package es.unex.cum.tw.reyesmagos;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * Servlet implementation class Anadir
 */
@WebServlet("/Anadir")
public class Anadir extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("Autenticado") != null) {
            LinkedList<String> lista = (LinkedList<String>) session.getAttribute("listaregalo");
            if (lista == null) {
                lista = new LinkedList<String>();
                session.setAttribute("listaregalo", lista);
            } else {
                String regalo = request.getParameter("Regalo");
                if (regalo != null) {
                    lista.add(regalo);
                    session.setAttribute("listaregalo", lista);
                    DevolverHTML(response.getWriter());
                } else {
                    PrintWriter ee = response.getWriter();
                    error_NoRegalo(ee);
                }
            }
        }else{
            error_Username(response.getWriter());
        }
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void DevolverHTML(PrintWriter out) {

        GenerarWeb g = new GenerarWeb();
        g.generarHead(out);
        g.generarCabecera(out);
        g.generarMenu(out);
        out.println("<div id='Content'>");
        out.println("<H2 align=\"center\">Se ha añadido el regalo correctamente </H2>");
        out.println("<p>Si quieres añadir más, pulse <a href='Introducir.html'>Aquí</a></p>");
        out.println("<p>CSS obtenida de <a href='http://bluerobot.com/'	title='BlueRobot Home'>BLUEROBOT.COM</a></p>");
        out.println("</div>");
        g.generarCerrarContenedor(out);
        g.generarCierreBody(out);
        out.flush();
    }

    private void error_Username(PrintWriter out) {
        GenerarWeb g = new GenerarWeb();
        g.generarHead(out);
        g.generarCabecera(out);
        g.generarMenu(out);
        out.println("<div id='Content'>");
        out.println("<h1>Problemas con la autenticación</h1>");
        out.println("<p>Si quieres volver a intentarlo, pulse <a href='Introducir.html'>Aquí</a></p>");
        out.println("<p>CSS obtenida de <a href='http://bluerobot.com/'	title='BlueRobot Home'>BLUEROBOT.COM</a></p>");
        out.println("</div>");
        g.generarCerrarContenedor(out);
        g.generarCierreBody(out);
        out.flush();
    }

    private void error_NoRegalo(PrintWriter out) {
        GenerarWeb g = new GenerarWeb();
        g.generarHead(out);
        g.generarCabecera(out);
        g.generarMenu(out);
        out.println("<div id='Content'>");
        out.println("<h1>No has indicado ningún regalo</h1>");
        out.println("<p>Si quieres volver a intentarlo, pulse <a href='.html'>Aquí</a></p>");
        out.println("<p>CSS obtenida de <a href='http://bluerobot.com/'	title='BlueRobot Home'>BLUEROBOT.COM</a></p>");
        out.println("</div>");
        g.generarCerrarContenedor(out);
        g.generarCierreBody(out);
        out.flush();
    }

}
