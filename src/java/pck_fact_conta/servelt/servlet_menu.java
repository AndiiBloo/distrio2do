package pck_fact_conta.servelt;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "servlet_menu", urlPatterns = {"/servlet_menu"})
public class servlet_menu extends HttpServlet {
    
    pck_fact_conta.entidades.Usuarios us;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String pantalla = "";
        String boton = "";
        HttpSession session = request.getSession(false);
        if(session != null){ 
            session = request.getSession();
            us = (pck_fact_conta.entidades.Usuarios)session.getAttribute("usuario");
        }
        else{
            us = null;
            
        }
        
        boton = request.getParameter("boton");        
        
        if (boton==null || boton==""){
            if(us != null){
                pantalla=mostrar_pantalla("","");
            }
            else{
                response.sendRedirect("servlet_usuario");
            }
        }
        
        if (boton!=null && boton!=""){
            if(us != null){
                if (boton.equals("Clientes")) {
                    response.sendRedirect("servlet_cliente");
                }
                if(boton.equals("Ciudades")){
                    response.sendRedirect("servlet_ciudad");
                }
                if(boton.equals("Factura")){
                    response.sendRedirect("web_factura.jsp");
                }
                if(boton.equals("Cuentas")){
                    response.sendRedirect("servlet_cuenta");
                }
                if(boton.equals("Tipo de Cuentas")){
                    response.sendRedirect("servlet_tipocuenta");
                }
                if(boton.equals("Cerrar Sesion")){
                    response.sendRedirect("servlet_usuario");
                    session.removeAttribute("usuario");
                }
                if(boton.equals("Reportes"))
                {
                    response.sendRedirect("servlet_reportes");
                }
                if(boton.equals("Comprobante de Contabilidad"))
                {
                    response.sendRedirect("servlet_comprobante");
                }
            }
            else{
                response.sendRedirect("servlet_usuario");
            }
        }
        out.println(pantalla);
            
    }
    
    public String mostrar_pantalla(String nombre, String password){  
        
        String pantalla="";
        switch(us.getUsRol().intValue()){
                case 1:
                    pantalla="";
                    pantalla+="<html>";
                    pantalla+="<head>";
                    pantalla+="</head>";
                    pantalla+="<body>";
                    pantalla+="<h2>Menu Administrador</h2>";
                    pantalla+="<form action='servlet_menu' method='post'>";
                    pantalla+="<input type='submit' value='Clientes' name='boton'></input>";
                    pantalla+="<br><br>";
                    pantalla+="<input type='submit' value='Ciudades' name='boton' ></input>";
                    pantalla+="<br><br>";
                    pantalla+="<input type='submit' value='Factura' name='boton' ></input>";
                    pantalla+="<br><br>";
                    pantalla+="<input type='submit' value='Cuentas' name='boton' ></input>";
                    pantalla+="<br><br>";                    
                    pantalla+="<input type='submit' value='Tipo de Cuentas' name='boton' ></input>";
                    pantalla+="<br><br>";
                    pantalla+="<input type='submit' value='Comprobante de Contabilidad' name='boton' ></input>";
                    pantalla+="<br><br>";
                    pantalla+="<input type='submit' value='Reportes' name='boton' ></input>";
                    pantalla+="<br><br>";
                    pantalla+="<input type='submit' value='Cerrar Sesion' name='boton' ></input>";
                    pantalla+="</form>";            
                    pantalla+="</body>";
                    pantalla+="</html>";
                break;
                case 2:
                    pantalla="";
                    pantalla+="<html>";
                    pantalla+="<head>";
                    pantalla+="</head>";
                    pantalla+="<body>";
                    pantalla+="<h2>Menu Facturación</h2>";
                    pantalla+="<form action='servlet_menu' method='post'>";
                    pantalla+="<input type='submit' value='Clientes' name='boton'></input>";
                    pantalla+="<br><br>";
                    pantalla+="<input type='submit' value='Ciudades' name='boton' ></input>";
                    pantalla+="<br><br>";
                    pantalla+="<input type='submit' value='Factura' name='boton' ></input>";
                    pantalla+="<br><br>";
                    pantalla+="<input type='submit' value='Reportes' name='boton' ></input>";
                    pantalla+="<br><br>";
                    pantalla+="<input type='submit' value='Cerrar Sesion' name='boton' ></input>";
                    pantalla+="</form>";            
                    pantalla+="</body>";
                    pantalla+="</html>";
                break;
                case 3:
                    pantalla="";
                    pantalla+="<html>";
                    pantalla+="<head>";
                    pantalla+="</head>";
                    pantalla+="<body>";
                    pantalla+="<h2>Menu Contabilidad</h2>";
                    pantalla+="<form action='servlet_menu' method='post'>";
                    pantalla+="<input type='submit' value='Cuentas' name='boton' ></input>";
                    pantalla+="<br><br>";
                    pantalla+="<input type='submit' value='Tipo de Cuentas' name='boton' ></input>";
                    pantalla+="<br><br>";
                    pantalla+="<input type='submit' value='Comprobante de Contabilidad' name='boton' ></input>";
                    pantalla+="<br><br>";
                    pantalla+="<input type='submit' value='Reportes' name='boton' ></input>";
                    pantalla+="<br><br>";
                    pantalla+="<input type='submit' value='Cerrar Sesion' name='boton' ></input>";
                    pantalla+="</form>";            
                    pantalla+="</body>";
                    pantalla+="</html>";
                break;
                default:
                    
                break;
            }
        return pantalla;
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
