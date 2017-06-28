package pck_fact_conta.backingbean;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import pck_fact_conta.entidades.Cliente;
import pck_fact_conta.negocio.negocio_cliente;

@ManagedBean
@SessionScoped
public class clienteController implements Serializable {
    
    private Cliente cliente;
    private final negocio_cliente ncli = new negocio_cliente();

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public clienteController() {
        cliente = new Cliente();
    }
    
    public void ingresar(){
        if (ncli.insertar(this.cliente.getCliRuc(), this.cliente.getCliNombre(),this.cliente.getCliDireccion())==1)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Cliente ingresado correctamente"));
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al insertar el cliente"));
        cliente = new Cliente();
    }
    
    public void eliminar(){
        if (ncli.eliminar(this.cliente.getCliRuc())==1)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Cliente eliminado correctamente"));
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al eliminar el cliente"));
        cliente = new Cliente();
    }
    
    public void modificar(){
        if (ncli.modificar(this.cliente.getCliRuc(), this.cliente.getCliNombre(),this.cliente.getCliDireccion())==1)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Cliente modificado correctamente"));
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al modificar el cliente"));
        cliente = new Cliente();
    }
    
    public void buscar(){
        this.cliente = ncli.buscar(this.cliente.getCliRuc());
        if(this.cliente!=null)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Cliente encontrado"));
        else{
            cliente = new Cliente();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al encontrar el cliente"));
        }
    }
}
