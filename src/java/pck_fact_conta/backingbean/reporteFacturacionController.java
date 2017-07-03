package pck_fact_conta.backingbean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;

@ManagedBean
@ViewScoped
public class reporteFacturacionController {
    private Date fechaI;
    private Date fechaF;
    private String realPath = "";
    private StreamedContent media;
    public boolean controlPDF = false;
    private String nombreArchivoPdf = "";
    private StreamedContent file;

    public Date getFechaI() {
        return fechaI;
    }

    public void setFechaI(Date fechaI) {
        this.fechaI = fechaI;
    }

    public Date getFechaF() {
        return fechaF;
    }

    public void setFechaF(Date fechaF) {
        this.fechaF = fechaF;
    }

    public boolean isControlPDF() {
        return controlPDF;
    }

    public void setControlPDF(boolean controlPDF) {
        this.controlPDF = controlPDF;
    }

    public String getNombreArchivoPdf() {
        return nombreArchivoPdf;
    }

    public void setNombreArchivoPdf(String nombreArchivoPdf) {
        this.nombreArchivoPdf = nombreArchivoPdf;
    }
    
    public reporteFacturacionController() {
    }
    
    public void generarReporte1() 
            throws JRException, IOException, SQLException, ClassNotFoundException{
        
        Connection cn;
        Class.forName("oracle.jdbc.OracleDriver"); 
        String BaseDeDatos = "jdbc:oracle:thin:@localhost:1521:xe";
        cn = DriverManager.getConnection(BaseDeDatos, "pdist","espe");
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        realPath = servletContext.getRealPath("/reportes");
        Date fecha = new Date();
        String nombreRandomico = Long.toString(fecha.getTime());
        
        nombreArchivoPdf = nombreRandomico + ".pdf";
        
        JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(realPath + "/reporteCiudades.jasper");        
        
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("P_FechaI", fechaI);
        parametros.put("P_FechaF", fechaF);
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, cn);
        JRExporter exporter = new JRPdfExporter();
                          
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File(realPath + "/pdf/" + nombreArchivoPdf));
        exporter.exportReport(); 
        FacesContext.getCurrentInstance().getExternalContext().addResponseHeader("Cache-Control", "no-cache");
        FacesContext.getCurrentInstance().getExternalContext().addResponseHeader("Pragma", "no-cache");
        FacesContext.getCurrentInstance().getExternalContext().addResponseHeader("Expires", "0");
        FacesContext.getCurrentInstance().getExternalContext().setResponseContentType("application/pdf");
        controlPDF = true;
        cn.close();
        RequestContext.getCurrentInstance().update(":principal:acordpanReporte:pdf");
    }
    
    public void generarReporte2() 
            throws JRException, IOException, SQLException, ClassNotFoundException{
        
        Connection cn;
        Class.forName("oracle.jdbc.OracleDriver"); 
        String BaseDeDatos = "jdbc:oracle:thin:@localhost:1521:xe";
        cn = DriverManager.getConnection(BaseDeDatos, "pdist","espe");
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        realPath = servletContext.getRealPath("/reportes");
        Date fecha = new Date();
        String nombreRandomico = Long.toString(fecha.getTime());
        
        nombreArchivoPdf = nombreRandomico + ".pdf";
        
        JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(realPath + "/reporteCruzada.jasper");        
        
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("P_FechaI", fechaI);
        parametros.put("P_FechaF", fechaF);
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, cn);
        JRExporter exporter = new JRPdfExporter();
                          
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File(realPath + "/pdf/" + nombreArchivoPdf));
        exporter.exportReport(); 
        FacesContext.getCurrentInstance().getExternalContext().addResponseHeader("Cache-Control", "no-cache");
        FacesContext.getCurrentInstance().getExternalContext().addResponseHeader("Pragma", "no-cache");
        FacesContext.getCurrentInstance().getExternalContext().addResponseHeader("Expires", "0");
        FacesContext.getCurrentInstance().getExternalContext().setResponseContentType("application/pdf");
        controlPDF = true;
        cn.close();
        RequestContext.getCurrentInstance().update(":principal:acordpanReporte:pdf");
    }
}
