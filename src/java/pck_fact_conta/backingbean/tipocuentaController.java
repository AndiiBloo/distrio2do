/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck_fact_conta.backingbean;

/**
 *
 * @author Marco Rodriguez
 */
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import pck_fact_conta.entidades.Tipocuenta;
import pck_fact_conta.negocio.negocio_tipocuenta;

@ManagedBean
@SessionScoped
public class tipocuentaController implements Serializable {
    private Tipocuenta tipocuenta;
    final private negocio_tipocuenta negotipcue;

    public tipocuentaController() {
        this.tipocuenta = new Tipocuenta();
        this.negotipcue = new negocio_tipocuenta();
    }
    
    public void guardarTipoCuenta(){
        if (negotipcue.insertar(tipocuenta.getTdcNombre())==1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Tipo de Cuenta ingresada correctamente"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al insertar el Tipo de Cuenta"));
        }
    }
    
    public void buscarTipoCuenta(){
         
        tipocuenta.setTdcNombre(negotipcue.buscar(tipocuenta.getTdcCodigo()).get(0));

         if(tipocuenta.getTdcNombre() != null) {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Tipo de Cuenta encontrada"));
        } else {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al encontrar el Tipo de Cuenta"));
        }
    }
    
    public void modificarTipoCuenta(){
        
        if (negotipcue.modificar(tipocuenta.getTdcCodigo(),tipocuenta.getTdcNombre())==1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Tipo de Cuenta modificada correctamente"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al modificar el Tipo de Cuenta"));
        }
    }
    public void eliminarTipoCuenta(){
        
        if(negotipcue.eliminar(tipocuenta.getTdcCodigo()) == 1) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Tipo de Cuenta eliminada correctamente"));
        } else {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al eliminar el Tipo de Cuenta"));
        }
    }
    public Tipocuenta getTipocuenta() {
        return tipocuenta;
    }

    public void setTipocuenta(Tipocuenta tipocuenta) {
        this.tipocuenta = tipocuenta;
    }
}
