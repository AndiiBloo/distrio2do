package pck_fact_conta.backingbean;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import pck_fact_conta.entidades.CiudadEntrega;
import pck_fact_conta.negocio.negocio_ciudad;

@ManagedBean
@SessionScoped
public class ciudadController implements Serializable{
    
    private CiudadEntrega ciudad;
    private final negocio_ciudad nciu = new negocio_ciudad();

    public CiudadEntrega getCiudad() {
        return ciudad;
    }

    public void setCiudad(CiudadEntrega ciudad) {
        this.ciudad = ciudad;
    }
    
    public ciudadController() {
        ciudad = new CiudadEntrega();
    }
    
    public void ingresar(){
        if (nciu.insertar(this.ciudad.getCiuNombre())==1)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Ciudad ingresada correctamente"));
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al insertar la ciudad"));
        ciudad = new CiudadEntrega();
    }
    
    public void eliminar(){
        if (nciu.eliminar(this.ciudad.getCiuCodigo())==1)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Ciudad eliminada correctamente"));
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al eliminar la ciudad"));
        ciudad = new CiudadEntrega();
    }
    
    public void modificar(){
        if (nciu.modificar(this.ciudad.getCiuCodigo(), this.ciudad.getCiuNombre())==1)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Ciuadad modificada correctamente"));
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al modificar la ciudad"));
        ciudad = new CiudadEntrega();
    }
    
    public void buscar(){
        this.ciudad = nciu.buscar(this.ciudad.getCiuCodigo());
        if(this.ciudad != null)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Ciudad encontrada"));
        else{
            ciudad = new CiudadEntrega();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al encontrar la ciudad"));
        }
    }
    
}
