package pck_fact_conta.backingbean;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import pck_fact_conta.entidades.Usuarios;

@ManagedBean
@ViewScoped
public class menuController implements Serializable{

    public menuController() {
        
    }
    
    public void verificarSesion(){
        try{
            FacesContext context = FacesContext.getCurrentInstance();
            Usuarios us = (Usuarios) context.getExternalContext().getSessionMap().get("usuario");
            if(us == null){
                context.getExternalContext().getFlash().setKeepMessages(true);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                   "Error", "No ha iniciado sesión"));
                context.getExternalContext().redirect("../index.xhtml");
            }
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void cerrarSesion(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
}
