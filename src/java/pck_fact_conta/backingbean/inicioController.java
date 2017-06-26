package pck_fact_conta.backingbean;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import pck_fact_conta.entidades.Usuarios;
import pck_fact_conta.negocio.negocio_usuario;

@ManagedBean
@ViewScoped
public class inicioController implements Serializable {

    private Usuarios usuario;

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }
    
    public inicioController() {
        usuario = new Usuarios();
    }
    
    public String validarLogin(){
        String redirect = null;
        negocio_usuario nusu = new negocio_usuario();
        Usuarios us = null;
        try{
            us = nusu.buscarUs(this.usuario.getUsNombre(), this.usuario.getUsPassword());
            if(us != null){
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", us);
                redirect = "/Views/menu?faces-redirect=true";
            }
            else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Usuario o contraseña incorectos"));
            }
        }catch(Exception e){
            throw e;
        }
        return redirect;
    }
}
