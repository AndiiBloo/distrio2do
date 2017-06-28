/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck_fact_conta.servelt;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pck_fact_conta.entidades.Tipocuenta;
import pck_fact_conta.negocio.negocio_cuenta;
import pck_fact_conta.negocio.negocio_tipocuenta;

/**
 *
 * @author Marco Rodriguez
 */
@WebServlet(name = "servlet_cuenta", urlPatterns = {"/servlet_cuenta"})
public class servlet_cuenta extends HttpServlet 
{
    String ls_mensaje="";
    negocio_cuenta ncli=new negocio_cuenta();
    negocio_tipocuenta tdc=new negocio_tipocuenta();
    List<Tipocuenta> tipos= tdc.mostrar();
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
            String ls_tipo="";
            is_boton=request.getParameter("boton");
            ls_codigo=request.getParameter("codigo");
            ls_nombre=request.getParameter("nombre"); 
            ls_tipo=request.getParameter("tipo");
            if (is_boton==null || is_boton =="")
            {   
                is_pantalla=desplegar_pantalla("","","");
            }    


            if (is_boton!=null && is_boton !="")
            {
                int indice=-1;   
                if(is_boton.equals("Insertar"))
                {
                    for(int i=0;i<tipos.size();i++)
                    {
                        if(tipos.get(i).getTdcCodigo().toString().equals(ls_tipo))
                        {
                            indice=i;
                        }
                    }
                    if(indice!=-1&&Integer.valueOf(ls_tipo)!=0)
                    {
                        if (ncli.insertar(ls_nombre,tipos.get(indice))==1)
                        {
                            ls_mensaje="Se insertó";
                        }
                       else 
                        {
                            ls_mensaje="No se pudo insertar";
                        }
                    }
                    else
                    {
                        ls_mensaje="Tipo no valido";
                    }
                    
                   is_pantalla=desplegar_pantalla("","","");                  
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
                   is_pantalla=desplegar_pantalla("","","");                  
                    is_pantalla+=ls_mensaje;

                }
                if(is_boton.equals("Modificar"))
                {
                   if (ncli.modificar(BigDecimal.valueOf(Double.valueOf(ls_codigo)),ls_nombre, tipos.get(Integer.valueOf(ls_tipo)))==1)
                   {
                       ls_mensaje="Se modificó";
                   }
                   else
                   {
                       ls_mensaje="No se pudo modificar";
                   }    
                   is_pantalla=desplegar_pantalla("","","");                  
                   is_pantalla+=ls_mensaje;
                }
                /*if(is_boton.equals("Buscar"))
                {  ls_nombre=ncli.buscar(BigDecimal.valueOf(Double.valueOf(ls_codigo))).get(0);
                    ls_tipo=ncli.buscar(BigDecimal.valueOf(Double.valueOf(ls_codigo))).get(1);
                   if(ls_nombre!=null && ls_tipo!=null)
                   {
                       ls_mensaje="Se encontró";
                   }
                   else
                   {
                       ls_mensaje="No se encontró";
                   } 
                   is_pantalla=desplegar_pantalla(ls_codigo,ls_nombre,ls_tipo);                  
                   is_pantalla+=ls_mensaje;
                    
                }*/
                if(is_boton.equals("Regresar"))
                {
                response.sendRedirect("servlet_menu");
                }
                
            }
                    out.println(is_pantalla);
                  
        
    }   

    public String desplegar_pantalla(String as_codigo, String as_nombre, String as_tipo)
    {       String ls_pantalla="";
            ls_pantalla+="<html>";
            ls_pantalla+="<head>";
            ls_pantalla+="</head>";
            ls_pantalla+="<body>";
            ls_pantalla+="<h2>Cuenta </h2>";
            
            ls_pantalla+="<form action='servlet_cuenta' method='post'>";
            ls_pantalla+="Codigo:<input type='text' name='codigo'"+" value='"+as_codigo +"'></input>";
            ls_pantalla+="<br><br>";
            ls_pantalla+="Nombre:<input type='text' name='nombre'"+" value='"+as_nombre+"'></input>";  
            ls_pantalla+="<br><br>";
            ls_pantalla+="Direccion:<select name='tipo'>";
            ls_pantalla+="<option value='0'>Tipo de Cuenta</option>";
            for(int i=0;i<tipos.size();i++)
            {
                if(tipos.get(i).getTdcCodigo().toString().equals(as_tipo))
                {
                    ls_pantalla+= "<option value='"+tipos.get(i).getTdcCodigo()+"' selected>"+tipos.get(i).getTdcNombre()+"</option>";
                }
                else
                {
                    ls_pantalla+= "<option value='"+tipos.get(i).getTdcCodigo()+"'>"+tipos.get(i).getTdcNombre()+"</option>";
                }                
            }
            ls_pantalla+="</select><br><br>";
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