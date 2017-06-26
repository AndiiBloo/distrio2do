/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck_fact_conta.servelt;

import com.sun.javafx.css.Combinator;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pck_fact_conta.entidades.Comprobantecontabilidad;
import pck_fact_conta.entidades.Cuenta;
import pck_fact_conta.entidades.Detallecomprobantecontabilidad;
import pck_fact_conta.entidades.Tipocuenta;
import pck_fact_conta.negocio.negocio_articulo;
import pck_fact_conta.negocio.negocio_comprobante;
import pck_fact_conta.negocio.negocio_cuenta;
import pck_fact_conta.negocio.negocio_detalle;

/**
 *
 * @author Marco Rodriguez
 */
@WebServlet(name = "servlet_comprobante", urlPatterns = {"/servlet_comprobante"})
public class servlet_comprobante extends HttpServlet 
{
String ls_mensaje="";
    
    negocio_cuenta cuenta= new negocio_cuenta();
    negocio_comprobante comprobante= new negocio_comprobante();
    List<Cuenta> cuentas= cuenta.mostrar();  
    negocio_articulo articulos=new negocio_articulo();
    negocio_detalle det= new negocio_detalle();            
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException 
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        List<Detallecomprobantecontabilidad> deatalles= new ArrayList<>();
        String is_pantalla="";
        ls_mensaje="";
        String is_boton="";
        String ls_codigo="";
        String ls_fecha="";
        String ls_obserbacion="";
        String ls_bancos="";
        String ls_ventas="";
        String ls_tipo1="";
        String ls_tipo2="";
        is_boton=request.getParameter("boton");
        ls_codigo=request.getParameter("codigo"); 
        ls_fecha=request.getParameter("fecha"); 
        ls_obserbacion=request.getParameter("observacion");
        ls_bancos=request.getParameter("bancohaber");
        ls_ventas=request.getParameter("ventasdebe");
        ls_tipo1=request.getParameter("tipo1");
        ls_tipo2=request.getParameter("tipo2");
            if (is_boton==null || is_boton =="")
            {   
                is_pantalla=desplegar_pantalla("","","","","");
            }    


            if (is_boton!=null && is_boton !="")
            {
                int indice1=-1;
                int indice2=-1;
                if(is_boton.equals("Asiento"))
                {
                    double total= articulos.total();
                    is_pantalla=desplegar_pantalla(ls_codigo,ls_fecha,ls_obserbacion,String.valueOf(total),String.valueOf(total));
                    ls_mensaje="Total: "+total;
                    is_pantalla+=ls_mensaje;
                }
                if(is_boton.equals("Insertar"))
                {
                    for(int i=0;i<cuentas.size();i++)
                    {
                        if(cuentas.get(i).getCueCodigo().toString().equals(ls_tipo1))
                        {
                            indice1=i;
                            Detallecomprobantecontabilidad det1=new Detallecomprobantecontabilidad();
                            det1.setCueCodigo(cuentas.get(i));
                            det1.setDccDebe(Double.valueOf(ls_bancos));
                            deatalles.add(det1);
                        }
                        if(cuentas.get(i).getCueCodigo().toString().equals(ls_tipo2))
                        {
                            indice2=i;
                            Detallecomprobantecontabilidad det1=new Detallecomprobantecontabilidad();
                            det1.setCueCodigo(cuentas.get(i));
                            det1.setDccHaber(Double.valueOf(ls_ventas));
                            deatalles.add(det1);
                        }
                    }
                    if(indice1!=-1&&Integer.valueOf(ls_tipo1)!=0&&indice2!=-1&&Integer.valueOf(ls_tipo2)!=0)
                    {
                        
                        if (comprobante.insertar(ls_fecha,ls_obserbacion)==1)
                        {
                            Comprobantecontabilidad c1= new Comprobantecontabilidad();
                            BigDecimal cod= comprobante.maximo();
                            c1.setComNumero(cod);
                            c1.setComFecha(ls_fecha);
                            c1.setComObservaciones(ls_obserbacion);
                            for (int i=0;i<deatalles.size();i++)
                            {
                                det.insertar(c1, deatalles.get(i).getCueCodigo(), deatalles.get(i).getDccDebe(), deatalles.get(i).getDccDebe());
                            }
                            ls_mensaje="Se insertó "+cod;
                        }
                       else 
                        {
                            ls_mensaje="No se pudo insertar";
                        }
                    }
                    else
                    {
                        ls_mensaje="Indices: "+indice1+" 2:"+indice2+" 1:" +ls_tipo1+" 2:" +ls_tipo2 ;
                    }
                   is_pantalla=desplegar_pantalla("","","","","");                  
                   is_pantalla+=ls_mensaje;
                }
                if(is_boton.equals("Eliminar"))
                {
                   if (comprobante.eliminar(BigDecimal.valueOf(Double.valueOf(ls_codigo)))==1)
                   {
                       ls_mensaje="Se eliminó";
                   } 
                   else
                   {
                       ls_mensaje="No se pudo eliminar";
                   }
                   is_pantalla=desplegar_pantalla("","","","","");                  
                    is_pantalla+=ls_mensaje;

                }
                if(is_boton.equals("Modificar"))
                {
                    for(int i=0;i<cuentas.size();i++)
                    {
                        if(cuentas.get(i).getCueCodigo().toString().equals(ls_tipo1))
                        {
                            indice1=i;
                            Detallecomprobantecontabilidad det1=new Detallecomprobantecontabilidad();
                            det1.setCueCodigo(cuentas.get(i));
                            det1.setDccDebe(Double.valueOf(ls_bancos));
                            deatalles.add(det1);
                        }
                        if(cuentas.get(i).getCueCodigo().toString().equals(ls_tipo2))
                        {
                            indice2=i;
                            Detallecomprobantecontabilidad det1=new Detallecomprobantecontabilidad();
                            det1.setCueCodigo(cuentas.get(i));
                            det1.setDccHaber(Double.valueOf(ls_ventas));
                            deatalles.add(det1);
                        }
                    }
                   if (comprobante.modificar(BigDecimal.valueOf(Double.valueOf(ls_codigo)), ls_fecha,ls_obserbacion)==1)
                   {
                       ls_mensaje="Se modificó";
                   }
                   else
                   {
                       ls_mensaje="No se pudo modificar";
                   }    
                   is_pantalla=desplegar_pantalla("","","","","");                  
                   is_pantalla+=ls_mensaje;
                }
                if(is_boton.equals("Buscar"))
                {  ls_fecha=comprobante.buscar(BigDecimal.valueOf(Double.valueOf(ls_codigo))).get(0);
                    ls_obserbacion=comprobante.buscar(BigDecimal.valueOf(Double.valueOf(ls_codigo))).get(1);
                    
                   if(ls_fecha!=null)
                   {
                       ls_mensaje="Se encontró";
                   }
                   else
                   {
                       ls_mensaje="No se encontró";
                   } 
                   is_pantalla=desplegar_pantalla(ls_codigo,ls_fecha,ls_obserbacion,"","");                  
                   is_pantalla+=ls_mensaje;
                    
                }
                if(is_boton.equals("Regresar"))
                {
                    response.sendRedirect("servlet_menu");
                }
                
            }
                    out.println(is_pantalla);
                  
        
    }   

    public String desplegar_pantalla(String as_codigo, String as_fecha,String as_observacion,String as_bancos,String as_ventas)
    {       String ls_pantalla="";
            ls_pantalla+="<html>";
            ls_pantalla+="<head>";
            ls_pantalla+="</head>";
            ls_pantalla+="<body>";
            ls_pantalla+="<h2>Comprobante Contabilidad </h2>";
            
            ls_pantalla+="<form action='servlet_comprobante' method='post'>";
            ls_pantalla+="Codigo:<input type='text' name='codigo'"+" value='"+as_codigo +"'></input>";
            ls_pantalla+="<br><br>";
            ls_pantalla+="Fecha:<input type='text' name='fecha'"+" value='"+as_fecha+"'></input>";  
            ls_pantalla+="<br><br>";       
            ls_pantalla+="Observaciones:<input type='text' name='observacion'"+" value='"+as_observacion+"'></input>";  
            ls_pantalla+="<br><br>";
            ls_pantalla+="<input type='submit' value='Asiento' name='boton'></input> ";
            ls_pantalla+="<br><br>";
            ls_pantalla+="<table>";
            ls_pantalla+="<tr>";
            ls_pantalla+="<th>Cuenta</th>";
            ls_pantalla+="<th>Debe</th>";
            ls_pantalla+="<th>Haber</th>";            
            ls_pantalla+="</tr>";
            if(!as_bancos.equals("")&&!as_ventas.equals(""))
            {
                ls_pantalla+="<tr>";
                ls_pantalla+="<th><select name='tipo1'>";
                ls_pantalla+="<option value='0'>Cuenta</option>";
                for(int i=0;i<cuentas.size();i++)
                {
                    if(cuentas.get(i).getCueNombre().equals("Banco"))
                    {
                        ls_pantalla+= "<option value='"+cuentas.get(i).getCueCodigo()+"' selected>"+cuentas.get(i).getCueNombre()+"</option>";
                    }
                    else
                    {
                        ls_pantalla+= "<option value='"+cuentas.get(i).getCueCodigo()+"'>"+cuentas.get(i).getCueNombre()+"</option>";
                    }                
                }
                ls_pantalla+="</select></th>";
                ls_pantalla+="<th><input type='text' name='bancodebe'"+" value=''></input></th>";
                ls_pantalla+="<th><input type='text' name='bancohaber'"+" value='"+as_bancos+"'></input></th>";            
                ls_pantalla+="</tr>";
                ls_pantalla+="<tr>";
                ls_pantalla+="<th><select name='tipo2'>";
                ls_pantalla+="<option value='0'>Cuenta</option>";
                for(int i=0;i<cuentas.size();i++)
                {
                    if(cuentas.get(i).getCueNombre().equals("Ventas"))
                    {
                        ls_pantalla+= "<option value='"+cuentas.get(i).getCueCodigo()+"' selected>"+cuentas.get(i).getCueNombre()+"</option>";
                    }
                    else
                    {
                        ls_pantalla+= "<option value='"+cuentas.get(i).getCueCodigo()+"'>"+cuentas.get(i).getCueNombre()+"</option>";
                    }                
                }
                ls_pantalla+="</select></th>";
                ls_pantalla+="<th><input type='text' name='ventasdebe'"+" value='"+as_ventas+"'></input></th>";
                ls_pantalla+="<th><input type='text' name='ventashaber'"+" value=''></input></th>";            
                ls_pantalla+="</tr>";
            }
            ls_pantalla+="</table>";
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