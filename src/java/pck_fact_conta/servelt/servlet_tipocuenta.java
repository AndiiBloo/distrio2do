/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck_fact_conta.servelt;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pck_fact_conta.negocio.negocio_tipocuenta;

/**
 *
 * @author Marco Rodriguez
 */
@WebServlet(name = "servlet_tipocuenta", urlPatterns = {"/servlet_tipocuenta"})
public class servlet_tipocuenta extends HttpServlet 
{
    String ls_mensaje="";
    
    negocio_tipocuenta ncli=new negocio_tipocuenta();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException 
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String is_pantalla="";
        ls_mensaje="";
        String is_boton="";
        String ls_codigo="";
        String ls_nombre="";       
        is_boton=request.getParameter("boton");
        ls_codigo=request.getParameter("codigo"); 
        ls_nombre=request.getParameter("nombre"); 
            if (is_boton==null || is_boton =="")
            {   
                is_pantalla=desplegar_pantalla("","");
            }    


            if (is_boton!=null && is_boton !="")
            {

                if(is_boton.equals("Insertar"))
                {  if (ncli.insertar(ls_nombre)==1)
                    {
                        ls_mensaje="Se insertó";
                    }
                   else 
                    {
                        ls_mensaje="No se pudo insertar";
                    }
                   is_pantalla=desplegar_pantalla("","");                  
                   is_pantalla+=ls_mensaje;
                }
                if(is_boton.equals("Eliminar"))
                {
                   if (ncli.eliminar(BigDecimal.valueOf(Double.valueOf(ls_codigo)))==1)
                   {
                       ls_mensaje="Se eliminó";
                   } 
                   else
                   {
                       ls_mensaje="No se pudo eliminar";
                   }
                   is_pantalla=desplegar_pantalla("","");                  
                    is_pantalla+=ls_mensaje;

                }
                if(is_boton.equals("Modificar"))
                {
                   if (ncli.modificar(BigDecimal.valueOf(Double.valueOf(ls_codigo)), ls_nombre)==1)
                   {
                       ls_mensaje="Se modificó";
                   }
                   else
                   {
                       ls_mensaje="No se pudo modificar";
                   }    
                   is_pantalla=desplegar_pantalla("","");                  
                   is_pantalla+=ls_mensaje;
                }
                if(is_boton.equals("Buscar"))
                {  ls_nombre=ncli.buscar(BigDecimal.valueOf(Double.valueOf(ls_codigo))).get(0);
                    
                   if(ls_nombre!=null)
                   {
                       ls_mensaje="Se encontró";
                   }
                   else
                   {
                       ls_mensaje="No se encontró";
                   } 
                   is_pantalla=desplegar_pantalla(ls_codigo,ls_nombre);                  
                   is_pantalla+=ls_mensaje;
                    
                }
                if(is_boton.equals("Regresar"))
                {
                    response.sendRedirect("servlet_menu");
                }
                
            }
                    out.println(is_pantalla);
                  
        
    }   

    public String desplegar_pantalla(String as_codigo, String as_nombre)
    {       String ls_pantalla="";
            ls_pantalla+="<html>";
            ls_pantalla+="<head>";
            ls_pantalla+="</head>";
            ls_pantalla+="<body>";
            ls_pantalla+="<h2>Tipo de Cuenta </h2>";
            
            ls_pantalla+="<form action='servlet_tipocuenta' method='post'>";
            ls_pantalla+="Codigo:<input type='text' name='codigo'"+" value='"+as_codigo +"'></input>";
            ls_pantalla+="<br><br>";
            ls_pantalla+="Nombre:<input type='text' name='nombre'"+" value='"+as_nombre+"'></input>";  
            ls_pantalla+="<br><br>";       
            ls_pantalla+="<input type='submit' value='Insertar' name='boton'></input> ";
            ls_pantalla+="<input type='submit' value='Eliminar' name='boton' ></input> ";
            ls_pantalla+="<input type='submit' value='Modificar' name='boton'></input> ";
            ls_pantalla+="<input type='submit' value='Buscar' name='boton'></input> ";
            ls_pantalla+="<input type='submit' value='Regresar' name='boton'></input> ";
            ls_pantalla+="</form>";            
            ls_pantalla+="</body>";
            ls_pantalla+="</html>";
        
        return ls_pantalla;
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