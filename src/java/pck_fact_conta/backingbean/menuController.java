package pck_fact_conta.backingbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import pck_fact_conta.entidades.Usuarios;

@ManagedBean
@ViewScoped
public class menuController implements Serializable{

    private List<Boolean> botones;
    private int rol;
    public menuController() {
        botones = new ArrayList<>();
        for(int i = 0; i<9;i++){
            botones.add(false);
        }
    }

    public List<Boolean> getBotones() {
        return botones;
    }

    public void setBotones(List<Boolean> botones) {
        this.botones = botones;
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
            }else{
                rol = us.getUsRol().intValueExact();
                switch(rol){
                    case 1:
                        for(int i=0 ; i<9;i++){
                            botones.set(i, true);
                        }
                        break;
                    case 2:
                        for(int i=0 ; i<4;i++){
                            botones.set(i, true);
                        }
                        break;
                    case 3:
                        for(int i=4;i<8;i++){
                            botones.set(i, true);
                        }
                        break;
                    case 4:
                        botones.set(0, true);
                        botones.set(3, true);
                        break;
                    case 5:
                        botones.set(1, true);
                        botones.set(3, true);
                        break;
                    case 6:
                        botones.set(2, true);
                        botones.set(3, true);
                        break;
                    case 7:
                        botones.set(4, true);
                        botones.set(7, true);
                        break;
                    case 8:
                        botones.set(5, true);
                        botones.set(7, true);
                        break;
                    case 9:
                        botones.set(6, true);
                        botones.set(7, true);
                        break;
                        
                }
            }
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void cerrarSesion(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
}
