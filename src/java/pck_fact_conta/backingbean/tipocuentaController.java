package pck_fact_conta.backingbean;

import java.io.Serializable;
import java.util.Collections;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import pck_fact_conta.entidades.Tipocuenta;
import pck_fact_conta.negocio.negocio_tipocuenta;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean
@ViewScoped
public class tipocuentaController implements Serializable {
    private Tipocuenta tipocuenta;
    final private negocio_tipocuenta negotipcue;
    private List<Tipocuenta> tiposcuenta;
    private String campo;    
    private Map<String,String> campos;
    private int indice;   

    public tipocuentaController() {
        this.tipocuenta = new Tipocuenta();
        this.negotipcue = new negocio_tipocuenta();
        this.tiposcuenta = this.negotipcue.mostrar();
        this.campo = "";
        this.campos = new HashMap<String,String>();
        this.campos.put("codigo", "codigo");
        this.campos.put("nombre", "nombre");
        this.indice=0;
                
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
    public void ordenar() {
        if (this.campo.equals("codigo")) {
            
            this.tiposcuenta = this.negotipcue.mostrarPorCodigo();
        }
        if (this.campo.equals("nombre")) {
            
            this.tiposcuenta = this.negotipcue.mostrarPorNombre();
        }
        this.indice =0;
        this.tipocuenta = this.tiposcuenta.get(0);
    }
    public void siguiente() {
        
        if( (this.indice+1) >= this.tiposcuenta.size()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "No existen elementos despues de este"));
            
        } else {
            this.indice ++;
            this.tipocuenta = this.tiposcuenta.get(this.indice);
        }
    }
    public void anterior() {
        
        if( (this.indice-1) < 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "No existen elementos antes de este"));
            
        } else {
            this.indice --;
            this.tipocuenta = this.tiposcuenta.get(this.indice);
        }
    }
    public Tipocuenta getTipocuenta() {
        return tipocuenta;
    }

    public void setTipocuenta(Tipocuenta tipocuenta) {
        this.tipocuenta = tipocuenta;
    }

    public List<Tipocuenta> getTiposcuenta() {
        return tiposcuenta;
    }

    public void setTiposcuenta(List<Tipocuenta> tiposcuenta) {
        this.tiposcuenta = tiposcuenta;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public Map<String, String> getCampos() {
        return campos;
    }

    public void setCampos(Map<String, String> campos) {
        this.campos = campos;
    }
    
    
}
