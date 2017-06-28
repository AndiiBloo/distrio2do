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
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import pck_fact_conta.entidades.Tipocuenta;
import pck_fact_conta.negocio.negocio_tipocuenta;

@ManagedBean
@SessionScoped
public class tipocuentaController implements Serializable {
    private Tipocuenta tipocuenta;
    private negocio_tipocuenta negotipcue;
    private String mensaje;

    public tipocuentaController() {
        this.tipocuenta = new Tipocuenta();
        this.negotipcue = new negocio_tipocuenta();
        this.mensaje = "";
    }
    
    public void guardarTipoCuenta(){
        if (negotipcue.insertar(tipocuenta.getTdcNombre())==1) {
            mensaje="Se insertó";
        }
       else {
            mensaje="No se pudo insertar";
        }
    }
    
    public void buscarTipoCuenta(){
         
        tipocuenta.setTdcNombre(negotipcue.buscar(tipocuenta.getTdcCodigo()).get(0));

         if(tipocuenta.getTdcNombre() != null)
         {
             mensaje="Se encontró";
         }
         else
         {
             mensaje="No se encontró";
         } 
    }
    
    public void modificarTipoCuenta(){
        
        if (negotipcue.modificar(tipocuenta.getTdcCodigo(),tipocuenta.getTdcNombre())==1) {
            mensaje="Se modificó";
        }
        else {
            mensaje="No se pudo modificar" + tipocuenta.getTdcCodigo();
        }
    }
    public void eliminarTipoCuenta(){
        
        if(negotipcue.eliminar(tipocuenta.getTdcCodigo()) == 1) {
            
            mensaje="Se eliminó";
            
        } 
        else {
           mensaje="No se pudo eliminar" ;
        }
    }
    public Tipocuenta getTipocuenta() {
        return tipocuenta;
    }

    public void setTipocuenta(Tipocuenta tipocuenta) {
        this.tipocuenta = tipocuenta;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
    
    
}
