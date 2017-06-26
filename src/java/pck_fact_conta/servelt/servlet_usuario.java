package pck_fact_conta.servelt;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pck_fact_conta.negocio.negocio_usuario;

@WebServlet(name = "servlet_usuario", urlPatterns = {"/servlet_usuario"})
public class servlet_usuario extends HttpServlet {

    String msj="";
    negocio_usuario nusu = new negocio_usuario();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String pantalla = "";
        msj = "";
        String boton = "";
        String nombre = "";
        String password = "";
        
        boton = request.getParameter("boton");
        nombre = request.getParameter("nom");
        password = request.getParameter("pass");
        
        
        if (boton==null || boton=="")
            pantalla=mostrar_pantalla("","");

        if (boton!=null && boton!=""){
            if(boton.equals("Ingresar"))
            {
                HttpSession session = request.getSession(true);
                if(nusu.buscarUs(nombre,password) != null){
                    session.setAttribute("usuario", nusu.buscarUs(nombre,password));
                    response.sendRedirect("servlet_menu");
                }
                else{
                    pantalla = mostrar_pantalla("","");
                    msj="Usuario o contraseña incorrecto";
                }            
                pantalla+=msj;
            }
            
            if(boton.equals("Cancelar")){
                pantalla = mostrar_pantalla("","");
            }
        }
        out.println(pantalla);
    }
    
    public String mostrar_pantalla(String nombre, String password){       
        String pantalla="";
        pantalla+="<html>";
        pantalla+="<head>";
        pantalla+="</head>";
        pantalla+="<body>";
        pantalla+="<h2>Login</h2>";
        pantalla+="<form action='servlet_usuario' method='post'>";
        pantalla+="Usuario: <input type='text' name='nom'"+" value='"+nombre +"'></input>";
        pantalla+="<br><br>";
        pantalla+="Contraseña: <input type='password' name='pass'"+" value='"+password+"'></input>";  
        pantalla+="<br><br>";
        pantalla+="<input type='submit' value='Ingresar' name='boton'></input> ";
        pantalla+="<input type='submit' value='Cancelar' name='boton' ></input> ";
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
