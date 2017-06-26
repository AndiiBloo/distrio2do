package pck_fact_conta.servelt;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pck_fact_conta.negocio.negocio_cliente;


@WebServlet(name = "servlet_cliente", urlPatterns = {"/servlet_cliente"})
public class servlet_cliente extends HttpServlet {
    
    String msj="";
    negocio_cliente ncli=new negocio_cliente();
    pck_fact_conta.entidades.Usuarios us;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String pantalla = "";
        msj = "";
        String boton = "";
        String ruc = "";
        String nombre = "";
        String direccion = "";
        
        HttpSession session = request.getSession(false);
        if(session != null){ 
            session = request.getSession();
            us = (pck_fact_conta.entidades.Usuarios)session.getAttribute("usuario");
        }
        else{
            us = null;
        }
        
        boton = request.getParameter("boton");
        ruc = request.getParameter("codigo");
        nombre = request.getParameter("nombre"); 
        direccion = request.getParameter("direccion");
        
        if (boton==null || boton==""){
            if(us != null && (us.getUsRol().intValueExact() == 1 || us.getUsRol().intValueExact() == 2)){
                pantalla=mostrar_pantalla("","","");
            }
            else{
                response.sendRedirect("servlet_menu");
            }
        }

        if (boton!=null && boton!=""){
            if(us!=null && (us.getUsRol().intValueExact() == 1 || us.getUsRol().intValueExact() == 2)){
                if(boton.equals("Insertar")){
                    if (ncli.insertar(ruc, nombre,direccion)==1)
                        msj = "Se insertó";
                    else
                        msj = "No se pudo insertar";
                    pantalla = mostrar_pantalla("","","");                  
                    pantalla+=msj;
                }

                if(boton.equals("Eliminar")){
                    if (ncli.eliminar(ruc)==1)
                        msj = "Se eliminó";
                    else
                        msj = "No se pudo eliminar";
                    pantalla = mostrar_pantalla("","","");
                    pantalla+=msj;
                }

                if(boton.equals("Modificar")){
                    if (ncli.modificar(ruc, nombre,direccion)==1)
                        msj = "Se modificó";
                    else
                        msj = "No se pudo modificar";
                    pantalla = mostrar_pantalla("","","");
                    pantalla+=msj;
                }

                if(boton.equals("Buscar")){  
                    nombre = ncli.buscar(ruc).get(0);
                    direccion = ncli.buscar(ruc).get(1);
                    if(nombre!=null && direccion!=null)
                        msj="Se encontró";
                    else
                        msj="No se encontró";               
                    pantalla = mostrar_pantalla(ruc,nombre,direccion);                  
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

    public String mostrar_pantalla(String as_codigo, String as_nombre, String as_direccion){       
        String pantalla="";
        pantalla+="<html>";
        pantalla+="<head>";
        pantalla+="</head>";
        pantalla+="<body>";
        pantalla+="<h2>Tablas Simples - Cliente</h2>";
        pantalla+="<form action='servlet_cliente' method='post'>";
        pantalla+="Codigo:<input type='text' name='codigo'"+" value='"+as_codigo +"'></input>";
        pantalla+="<br><br>";
        pantalla+="Nombre:<input type='text' name='nombre'"+" value='"+as_nombre+"'></input>";  
        pantalla+="<br><br>";
        pantalla+="Direccion:<input type='text' name='direccion'"+" value='"+as_direccion+"'></input>";  
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
