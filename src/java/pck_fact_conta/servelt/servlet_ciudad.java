package pck_fact_conta.servelt;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pck_fact_conta.entidades.Usuarios;
import pck_fact_conta.negocio.negocio_ciudad;

@WebServlet(name = "servlet_ciudad", urlPatterns = {"/servlet_ciudad"})
public class servlet_ciudad extends HttpServlet {

    String msj="";
    negocio_ciudad nciu=new negocio_ciudad();
    Usuarios us;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String pantalla = "";
        msj = "";
        String boton = "";
        String codigo = "";
        String nombre = "";
        
        HttpSession session = request.getSession(false);
        if(session != null){ 
            session = request.getSession();
            us = (pck_fact_conta.entidades.Usuarios)session.getAttribute("usuario");
        }
        else{
            us = null;
            
        }
        
        boton = request.getParameter("boton");
        codigo = request.getParameter("codigo");
        nombre = request.getParameter("nombre");
        
        if (boton==null || boton==""){
            if(us != null && (us.getUsRol().intValueExact() == 1 || us.getUsRol().intValueExact() == 2)){
                pantalla=mostrar_pantalla("","");
            }
            else{
                response.sendRedirect("servlet_menu");
            }
        }

        if (boton!=null && boton!=""){
            if(us!=null && (us.getUsRol().intValueExact() == 1 || us.getUsRol().intValueExact() == 2)){
                if(boton.equals("Insertar")){
                    if (nciu.insertar(nombre)==1)
                        msj = "Se insertó";
                    else
                        msj = "No se pudo insertar";
                    pantalla = mostrar_pantalla("","");                  
                    pantalla+=msj;
                }

                if(boton.equals("Eliminar")){
                    if (nciu.eliminar(BigDecimal.valueOf(Double.valueOf(codigo)))==1)
                        msj = "Se eliminó";
                    else
                        msj = "No se pudo eliminar";
                    pantalla = mostrar_pantalla("","");
                    pantalla+=msj;
                }

                if(boton.equals("Modificar")){
                    if (nciu.modificar(BigDecimal.valueOf(Double.valueOf(codigo)), nombre)==1)
                        msj = "Se modificó";
                    else
                        msj = "No se pudo modificar";
                    pantalla = mostrar_pantalla("","");
                    pantalla+=msj;
                }

                if(boton.equals("Buscar")){  
                    /*nombre = nciu.buscar(BigDecimal.valueOf(Double.valueOf(codigo)));*/
                    if(nombre!=null)
                        msj="Se encontró";
                    else
                        msj="No se encontró";               
                    pantalla = mostrar_pantalla(codigo,nombre);                  
                    pantalla+=msj;
                }

                if(boton.equals("Regresar")){
                    response.sendRedirect("servlet_menu");
                }
            }
            else{
                response.sendRedirect("servlet_menu");
            }
        }
        out.println(pantalla);
        
    }   

    public String mostrar_pantalla(String as_codigo, String as_nombre){       
        String pantalla="";
        pantalla+="<html>";
        pantalla+="<head>";
        pantalla+="</head>";
        pantalla+="<body>";
        pantalla+="<h2>Tablas Simples - Ciudades</h2>";
        pantalla+="<form action='servlet_ciudad' method='post'>";
        pantalla+="Codigo:<input type='text' name='codigo'"+" value='"+as_codigo +"'></input>";
        pantalla+="<br><br>";
        pantalla+="Nombre:<input type='text' name='nombre'"+" value='"+as_nombre+"'></input>";  
        pantalla+="<br><br>";
        pantalla+="<input type='submit' value='Insertar' name='boton'></input> ";
        pantalla+="<input type='submit' value='Eliminar' name='boton' ></input> ";
        pantalla+="<input type='submit' value='Modificar' name='boton'></input> ";
        pantalla+="<input type='submit' value='Buscar' name='boton'></input> ";
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
