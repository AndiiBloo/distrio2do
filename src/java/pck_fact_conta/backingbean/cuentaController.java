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
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import pck_fact_conta.entidades.Cuenta;
import pck_fact_conta.negocio.negocio_cuenta;
import pck_fact_conta.negocio.negocio_tipocuenta;
import pck_fact_conta.entidades.Tipocuenta;

@ManagedBean
@SessionScoped
public class cuentaController implements Serializable {
    
    private Cuenta cuenta;
    final private negocio_tipocuenta negotipcue;
    final private negocio_cuenta negocue;
    private List<Tipocuenta> tiposcuenta;

    public cuentaController() {
        this.cuenta = new Cuenta();
        this.negotipcue = new negocio_tipocuenta();
        this.negocue = new negocio_cuenta();
        this.tiposcuenta = this.negotipcue.mostrar();
    }
    
    public void guardarCuenta() {
        
        if(negocue.insertar(this.cuenta.getCueNombre(), this.cuenta.getTdcCodigo()) == 1) {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Cuenta insertada correctamente"));
        } else {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al insertar la Cuenta"));
        }
    }
    public void obtenerCuenta() {
        this.cuenta = this.negocue.buscar(this.cuenta.getCueCodigo()).get(0);        
        if(this.cuenta != null) {
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Cuenta encontrada"));
        } else {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al encontrar la Cuenta"));
             this.cuenta = new Cuenta();
        }
    }
    public void modificarCuenta() {
       
        if (negocue.modificar(this.cuenta.getCueCodigo(),this.cuenta.getCueNombre(),this.cuenta.getTdcCodigo())==1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Cuenta modificada correctamente"));
        } else {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al modificar la Cuenta"));
        }
    }
    public void eliminarCuenta() {
        
        if (negocue.eliminar(this.cuenta.getCueCodigo())==1) {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Cuenta eliminada correctamente"));
        } else {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al eliminar la Cuenta"));
        }
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
    
    public List<Tipocuenta> getTiposcuenta() {
        return tiposcuenta;
    }

    public void setTiposcuenta(List<Tipocuenta> tiposcuenta) {
        this.tiposcuenta = tiposcuenta;
    }
}
