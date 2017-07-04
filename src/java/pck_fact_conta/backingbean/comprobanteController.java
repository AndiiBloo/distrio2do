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
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import pck_fact_conta.entidades.Comprobantecontabilidad;
import pck_fact_conta.entidades.Cuenta;
import pck_fact_conta.entidades.Detallecomprobantecontabilidad;
import pck_fact_conta.negocio.negocio_articulo;
import pck_fact_conta.negocio.negocio_comprobante;
import pck_fact_conta.negocio.negocio_cuenta;
import pck_fact_conta.negocio.negocio_detalle;

@ManagedBean
@SessionScoped
public class comprobanteController implements Serializable {
    
    private Comprobantecontabilidad comprobante;
    private List<Detallecomprobantecontabilidad> detalles;
    private Detallecomprobantecontabilidad detalle;
    private final negocio_cuenta neg_cue;
    private final negocio_comprobante neg_com;
    private List<Cuenta> cuentas;
    private final negocio_articulo neg_art;
    private final negocio_detalle neg_det;
    
    public comprobanteController() {
        this.comprobante = new Comprobantecontabilidad();
        this.detalles = new ArrayList<>();
        this.detalle = new Detallecomprobantecontabilidad();
        this.neg_cue= new negocio_cuenta();
        this.neg_com = new negocio_comprobante();
        this.cuentas = neg_cue.mostrar(); 
        this.neg_art=new negocio_articulo();
        this.neg_det= new negocio_detalle();
    }
    
    public void crearAsiento() {
        this.detalles = new ArrayList<>();
        double total = this.neg_art.total();
        this.detalle.setDccHaber(total);
        this.detalle.setDccDebe(0.0);
        this.detalles.add(this.detalle);
        this.detalle = new Detallecomprobantecontabilidad();
        this.detalle.setDccDebe(total);
        this.detalle.setDccHaber(0.0);
        this.detalles.add(this.detalle);
    }
    
    public void agregarDetalle() {
        this.detalle = new Detallecomprobantecontabilidad();
        this.detalles.add(this.detalle);
    }
    
    public void guardarComprobante(){
        this.detalle = new Detallecomprobantecontabilidad();
        if(this.verificarCuadre()) {
            if(neg_com.insertar(this.comprobante.getComFecha(), this.comprobante.getComObservaciones()) == 1) {
            this.comprobante.setComNumero(this.neg_com.maximo());
            for (int i=0;i<this.detalles.size();i++) {
                
                if (this.detalles.get(i).getDccDebe().equals(0.0) && this.detalles.get(i).getDccHaber().equals(0.0)) {
                    
                } else {
                    this.neg_det.insertar(this.comprobante, this.detalles.get(i).getCueCodigo(), this.detalles.get(i).getDccDebe(), this.detalles.get(i).getDccHaber());

                }
            }
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Correcto!", "Comprobante insertado correctamente"));

            } else {
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error!", "Error al insertar el Comprobante"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error!", "El detalle no esta cuadrado"));
        }
        
    }
    public void modificarComprobante() {
        this.detalle = new Detallecomprobantecontabilidad();
        if(this.verificarCuadre()) {
            if(neg_com.modificar(this.comprobante.getComNumero(),this.comprobante.getComFecha(), this.comprobante.getComObservaciones()) == 1) {                
                for (int i=0;i<this.detalles.size();i++) {
                    
                    if (this.detalles.get(i).getDccDebe().equals(0.0) && this.detalles.get(i).getDccHaber().equals(0.0)) {
                        
                    } else {
                        this.neg_det.modificar(this.comprobante,this.detalles.get(i).getDccCodigo(), this.detalles.get(i).getCueCodigo(), this.detalles.get(i).getDccDebe(), this.detalles.get(i).getDccHaber());
                    }
                }
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Correcto!", "Comprobante modifcado correctamente"));

            } else {
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error!", "Error al modificar el Comprobante"));
            }
        }else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error!", "El detalle no esta cuadrado"));
        }
            
        
    }
    public void buscarComprobante() {
        this.comprobante = this.neg_com.buscar(this.comprobante.getComNumero()).get(0);
        if(this.comprobante != null) {
            this.detalles = this.neg_det.buscar(this.comprobante);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Comprobante encontrado"));
        } else {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al encontrar el Comprobante"));
             this.comprobante = new Comprobantecontabilidad();
        }
    }
    public void eliminarComprobante(){
        
        for(int i =0; i<this.detalles.size(); i++) {
            this.neg_det.eliminar(this.detalles.get(i).getDccCodigo());
        }
        if (this.neg_com.eliminar(this.comprobante.getComNumero())==1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Correcto!", "Comprobante eliminado correctamente"));
           
        } else {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al eliminar el Comprobante"));
        }
    }
    private Boolean verificarCuadre(){
        Double total_debe = 0.0;
        Double total_haber = 0.0;
        Boolean valido = false;
        for(int i = 0; i < this.detalles.size(); i++) {
            total_debe += this.detalles.get(i).getDccDebe();
            total_haber += this.detalles.get(i).getDccHaber();
        }
        if (total_debe.equals(total_haber)) {
            valido = true;
        }
        return valido;
    }
    public Comprobantecontabilidad getComprobante() {
        return comprobante;
    }

    public void setComprobante(Comprobantecontabilidad comprobante) {
        this.comprobante = comprobante;
    }

    public List<Detallecomprobantecontabilidad> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<Detallecomprobantecontabilidad> detalles) {
        this.detalles = detalles;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }
}
