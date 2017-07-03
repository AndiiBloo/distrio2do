package pck_fact_conta.backingbean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;
import pck_fact_conta.entidades.Articulos;
import pck_fact_conta.entidades.CiudadEntrega;
import pck_fact_conta.entidades.Cliente;
import pck_fact_conta.entidades.Factura;
import pck_fact_conta.negocio.negocio_articulo;
import pck_fact_conta.negocio.negocio_ciudad;
import pck_fact_conta.negocio.negocio_cliente;
import pck_fact_conta.negocio.negocio_factura;

@ManagedBean
@SessionScoped
public class facturacionController implements Serializable{
    private Factura factura;
    private Cliente cliente;
    private List<Cliente> clientes;
    private CiudadEntrega ciudad;
    private Date fecha;
    private List<CiudadEntrega> ciudades;
    private Articulos articulo;
    private List<Articulos> articulos;
    
    private int aux;
    private final negocio_factura nfac;
    private final negocio_cliente ncli;
    private final negocio_ciudad nciu;
    private final negocio_articulo nart;

    public int getAux() {
        return aux;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public CiudadEntrega getCiudad() {
        return ciudad;
    }

    public void setCiudad(CiudadEntrega ciudad) {
        this.ciudad = ciudad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<CiudadEntrega> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<CiudadEntrega> ciudades) {
        this.ciudades = ciudades;
    }

    public Articulos getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulos articulo) {
        this.articulo = articulo;
    }

    public List<Articulos> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<Articulos> articulos) {
        this.articulos = articulos;
    }   
    
    public facturacionController() {
        ncli = new negocio_cliente();
        nfac = new negocio_factura();
        nciu = new negocio_ciudad();
        nart = new negocio_articulo();
         
        factura = new Factura();
        articulo = new Articulos();        
        cliente = new Cliente();
        fecha = new Date();
        clientes = ncli.mostrarClientes();
        ciudad = new CiudadEntrega();
        ciudades = nciu.mostrarCiudades();
        articulos = new ArrayList<>();
        aux = 1;
    }
    
    public void agregarArticulo(){
        articulo.setArtCodigo(new BigDecimal(aux));
        articulos.add(articulo);
        System.out.println("dialog: "+articulo.toString());
        this.articulo = null;
        articulo = new Articulos();
        aux++;
    }
    public void eliminarArticulo(){
        articulos.remove(articulo);
        articulo = null;
    }
    
    public void ingresar(){
        Factura numFact = nfac.obtenerNum();
        if(nfac.insertar(ciudad, cliente, fecha)==1){
            articulos.forEach((art) -> {
                nart.insertar(numFact, art.getArtNombre(), art.getArtPrecio(), art.getArtCantidad());
            });
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Factura ingresada correctamente"));
        }else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al insertar la factura"));
        
        ciudad = new CiudadEntrega();
        cliente = new Cliente();
        fecha = new Date();
        articulos = new ArrayList<>();
    }
    
    public void eliminar(){
        if(nfac.eliminar(factura.getFacNumero()) == 1){
            nart.mostrarArticulos(factura).forEach((a) -> {
                nart.eliminar(a.getArtCodigo());
            });
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
            "Correcto!", "Factura eliminada correctamente"));
        }else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al eliminar la factura"));
        factura = new Factura();
        ciudad = new CiudadEntrega();
        cliente = new Cliente();
        fecha = new Date();
        articulos = new ArrayList<>();
    }
    
    public void modificar(){
        int i = 0;
        List<Articulos> aux = nart.mostrarArticulos(factura);
        System.out.println("modf:"+articulos.toString());
        if(nfac.modificar(factura.getFacNumero(), ciudad, cliente, fecha) == 1){
            for(Articulos a: articulos){
                BigDecimal cod = nart.obtenerCodigo(factura, aux.get(i).getArtNombre());
                System.out.println(a.toString());
                if(nart.modificar(cod, factura, a.getArtNombre(), a.getArtPrecio(), a.getArtCantidad()) == 1){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Correcto!", "Factura modificada correctamente"));
                }
                i++;
            }
        }else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al modificar la factura"));
    }
    
    public void buscar(){
        ciudad = (CiudadEntrega) nfac.buscar(factura.getFacNumero()).get(0);
        cliente = (Cliente) nfac.buscar(factura.getFacNumero()).get(1);
        fecha = (Date) nfac.buscar(factura.getFacNumero()).get(2);
        
        if(ciudad != null && cliente != null && fecha != null){
            articulos = nart.mostrarArticulos(factura);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
            "Correcto!", "Factura encontrada"));
        }else{
            ciudad = new CiudadEntrega();
            cliente = new Cliente();
            fecha = new Date();
            articulos = new ArrayList<>();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al encontrar la factura"));
        }
    }
    
    public void editarFila(RowEditEvent event){
        FacesMessage msg = new FacesMessage("Articulo Editado", ((Articulos) event.getObject()).getArtNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void cancelarEditar(RowEditEvent event){
        FacesMessage msg = new FacesMessage("Cancelar edicion", ((Articulos) event.getObject()).getArtNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
