package pck_fact_conta.backingbean;

import java.io.Serializable;
import java.math.BigDecimal;
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
    private Tipocuenta tipocuenta;
    private String codigo;
    private List<Tipocuenta> tiposcuenta;

    public cuentaController() {
        this.cuenta = new Cuenta();
        this.negotipcue = new negocio_tipocuenta();
        this.negocue = new negocio_cuenta();
        this.tiposcuenta = this.negotipcue.mostrar();
        this.tipocuenta = new Tipocuenta();
        this.codigo = "";
    }
    
    public void guardarCuenta() {
        tipocuenta.setTdcCodigo(BigDecimal.valueOf(Double.valueOf(codigo)));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", " "+ this.tipocuenta.getTdcCodigo()));
        if(negocue.insertar(this.cuenta.getCueNombre(), this.tipocuenta) == 1) {
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
            this.tipocuenta = this.cuenta.getTdcCodigo();
            this.codigo = this.tipocuenta.getTdcNombre();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Cuenta encontrada"));
        } else {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al encontrar la Cuenta"));
             cuenta = new Cuenta();
        }
    }
    public void modificarCuenta() {
        tipocuenta.setTdcCodigo(BigDecimal.valueOf(Double.valueOf(codigo)));
        if (negocue.modificar(this.cuenta.getCueCodigo(),this.cuenta.getCueNombre(),this.tipocuenta)==1) {
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

    public Tipocuenta getTipocuenta() {
        return tipocuenta;
    }

    public void setTipocuenta(Tipocuenta tipocuenta) {
        this.tipocuenta = tipocuenta;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
}
