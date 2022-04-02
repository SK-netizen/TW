package es.unex.cum.tw.reyesmagos;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Servlet implementation class Listar
 */
@WebServlet("/Listar")
public class Listar extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        if (sesion.getAttribute("Autenticado") != null) {
            PrintWriter out = response.getWriter();
            DevolverHTML(out, sesion);
        } else {
            Error_Username(response.getWriter());
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

    private void DevolverHTML(PrintWriter out, HttpSession s) {

        GenerarWeb g = new GenerarWeb();
        g.generarHead(out);
        g.generarCabecera(out);
        g.generarMenu(out);
        out.println("<div id='Content'>");
        LinkedList<String> l = (LinkedList<String>) s.getAttribute("ListaRegalo");
        if (l != null) {
            out.println("<h1>Regalos:</h1>");
            out.println("<ul>");
            Iterator<String> it = l.iterator();
            while (it.hasNext()) {
                String s1 = (String) it.next();
                out.println("<li>" + s1 + "</li>");
            }
            out.println("</ul>");
        } else {
            l = new LinkedList<String>();
            s.setAttribute("ListaRegalo", l);
            out.println("<h2>Lista Vacia</h2>");
        }
        out.println("<p>Si quieres añadir, pulse <a href='Introducir.html'>Aquí</a></p>");
        out.println("<p>CSS obtenida de <a href='http://bluerobot.com/'	title='BlueRobot Home'>BLUEROBOT.COM</a></p>");
        out.println("</div>");
        g.generarCerrarContenedor(out);
        g.generarCierreBody(out);
        out.flush();
    }

    private void Error_Username(PrintWriter out) {
        GenerarWeb g = new GenerarWeb();
        g.generarHead(out);
        g.generarCabecera(out);
        g.generarMenu(out);
        out.println("<div id='Content'>");
        out.println("<h1>Problemas con la autenticación</h1>");
        out.println("<p>Si quieres volver a intentarlo, pulse <a href='Autenticar.html'>Aquí</a></p>");
        out.println("<p>CSS obtenida de <a href='http://bluerobot.com/'	title='BlueRobot Home'>BLUEROBOT.COM</a></p>");
        out.println("</div>");
        g.generarCerrarContenedor(out);
        g.generarCierreBody(out);
        out.flush();
    }

}
