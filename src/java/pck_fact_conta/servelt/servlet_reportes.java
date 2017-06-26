package pck_fact_conta.servelt;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

@WebServlet(name = "servlet_reportes", urlPatterns = {"/servlet_reportes"})
public class servlet_reportes extends HttpServlet {

    String msj="";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String pantalla = "";
        msj = "";
        String boton = "";        
        boton = request.getParameter("boton");
        
        String fecI = "";
        String fecF = "";
        fecI = request.getParameter("fechaI");
        fecF = request.getParameter("fechaF");
        
        if (boton==null || boton==""){
            pantalla=mostrar_pantalla();
        }
        
        if (boton!=null && boton!=""){
            if(boton.equals("Reporte Ventas")){
                
                response.sendRedirect("servlet_reporte1?fecI="+fecI+"&fecF="+fecF);
            }
            if(boton.equals("Reporte Cliente x Articulo")){
                response.sendRedirect("servlet_reporte2?fecI="+fecI+"&fecF="+fecF);
            }
            if(boton.equals("Balance General"))
            {
                response.sendRedirect("servlet_reporte3");
            }
            if(boton.equals("Estado de Resultados"))
            {
                response.sendRedirect("servlet_reporte4");
            }
            if(boton.equals("Regresar"))
                {
                response.sendRedirect("servlet_menu");
                }
        }
        
        out.println(pantalla);
    }
        
    public String mostrar_pantalla(){
        String pantalla="";
        pantalla+="<html>";
        pantalla+="<head>";
        pantalla+="</head>";
        pantalla+="<body>";
        pantalla+="<h2>Reportes</h2>";
        pantalla+="<form action='servlet_reportes' method='post'>";
        pantalla+="Fecha inicial:<input type='date' name='fechaI'></input>";
        pantalla+="<br><br>";
        pantalla+="Fecha final:<input type='date' name='fechaF'></input>";  
        pantalla+="<br><br>";
        pantalla+="<input type='submit' value='Reporte Ventas' name='boton'></input> ";
        pantalla+="<input type='submit' value='Reporte Cliente x Articulo' name='boton'></input> ";
        pantalla+="<input type='submit' value='Balance General' name='boton'></input> ";
        pantalla+="<input type='submit' value='Estado de Resultados' name='boton'></input> ";  
        pantalla+="<input type='submit' value='Regresar' name='boton'></input> ";
        pantalla+="</form>";            
        pantalla+="</body>";
        pantalla+="</html>";
        return pantalla;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
