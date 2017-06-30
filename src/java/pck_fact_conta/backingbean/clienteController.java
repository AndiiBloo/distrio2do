package pck_fact_conta.backingbean;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import pck_fact_conta.entidades.Cliente;
import pck_fact_conta.negocio.negocio_cliente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

@ManagedBean
@ViewScoped
public class clienteController implements Serializable {
    
    private Cliente cliente;
    private final negocio_cliente ncli = new negocio_cliente();
    private List<Cliente> clientes = new ArrayList<>();
    private int orden;

    public List<Cliente> getClientes() {
        return clientes;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
    
    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
    
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public clienteController() {
        cliente = new Cliente();
        cargarClientes();
    }
    
    public void cargarClientes(){
        clientes = ncli.mostrarClientes();
    }
    
    public void ingresar(){
        if (ncli.insertar(this.cliente.getCliRuc(), this.cliente.getCliNombre(),this.cliente.getCliDireccion())==1)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Cliente ingresado correctamente"));
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al insertar el cliente"));
        cliente = new Cliente();
        cargarClientes();
    }
    
    public void eliminar(){
        if (ncli.eliminar(this.cliente.getCliRuc())==1)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Cliente eliminado correctamente"));
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al eliminar el cliente"));
        cliente = new Cliente();
        cargarClientes();
    }
    
    public void modificar(){
        if (ncli.modificar(this.cliente.getCliRuc(), this.cliente.getCliNombre(),this.cliente.getCliDireccion())==1)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Cliente modificado correctamente"));
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al modificar el cliente"));
        cliente = new Cliente();
        cargarClientes();
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
        cargarClientes();
    }
    
    private void ordenarL(int orden){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();
        switch(orden){
            case 1:
                
                try{
                    clientes = em1.createNamedQuery("Cliente.orderRuc",Cliente.class).getResultList();
                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                }finally{
                    em1.close();
                    factory.close();
                }
                break;
            case 2:
                try{
                    clientes = em1.createNamedQuery("Cliente.orderNombre",Cliente.class).getResultList();
                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                }finally{
                    em1.close();
                    factory.close();
                }
                break;
            case 3:
                try{
                    clientes = em1.createNamedQuery("Cliente.orderDireccion",Cliente.class).getResultList();
                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                }finally{
                    em1.close();
                    factory.close();
                }
                break;
        }
        
    }
    
    public void ordenarLista(){
        ordenarL(orden);
        
    }
}
