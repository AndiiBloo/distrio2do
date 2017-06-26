/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck_fact_conta.servelt;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;


@WebServlet(name = "servlet_reporte2", urlPatterns = {"/servlet_reporte2"})
public class servlet_reporte2 extends HttpServlet {

    Date fecI;
Date fecF;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fechaI = request.getParameter("fecI");
        String fechaF = request.getParameter("fecF");
        
        try {
            fecI = new SimpleDateFormat("yyyy-MM-dd").parse(fechaI);            
            fecF = new SimpleDateFormat("yyyy-MM-dd").parse(fechaF);
            
        } catch(Exception ex){
             System.out.println(ex.getMessage());
        }
        
        String salida;
        
        try {
            String jrxmlFileName = "WEB-INF/reporteCruzada.jasper";
            File archivoReporte = new File(request.getRealPath(jrxmlFileName));
            Map<String, String> parametros = new HashMap<String, String>();
            HashMap parametro = null;
            parametro = new HashMap();
            parametro.put("P_FechaI", fecI);
            parametro.put("P_FechaF", fecF);
      
 
            ServletOutputStream servletOutputStream = response.getOutputStream();
 
            byte[] bytes = null;
            Connection connection;
            Class.forName("oracle.jdbc.OracleDriver"); 
            String BaseDeDatos = "jdbc:oracle:thin:@localhost:1521:xe";
            connection = DriverManager.getConnection(BaseDeDatos, "pdist","espe"); 
            
            try {
                bytes = JasperRunManager.runReportToPdf(archivoReporte.getPath(), parametro, connection);
 
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                servletOutputStream.write(bytes, 0, bytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
            } catch (JRException e) {
                StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter);
                e.printStackTrace(printWriter);
                response.setContentType("text/plain");
                response.getOutputStream().print(stringWriter.toString());
            }
        } catch (Exception e) {
            salida = "Error generando Reporte Jasper, el error del Sistema es " + e;
            System.out.println(salida);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
