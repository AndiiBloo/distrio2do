package pck_fact_conta.backingbean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import pck_fact_conta.entidades.CiudadEntrega;
import pck_fact_conta.entidades.Cliente;
import pck_fact_conta.negocio.negocio_ciudad;

@ManagedBean
@ViewScoped
public class ciudadController implements Serializable{
    @Resource(mappedName="conexion_jms_al")
    javax.jms.QueueConnectionFactory queueConnection;
    @Resource(mappedName="destino_jms_al")
    javax.jms.Queue queue;
    
    static final String CIUDAD = "ciudad";
    private CiudadEntrega ciudad;
    private final negocio_ciudad nciu = new negocio_ciudad();
    private List<CiudadEntrega> ciudades = new ArrayList<>();
    private int orden;

    public CiudadEntrega getCiudad() {
        return ciudad;
    }

    public void setCiudad(CiudadEntrega ciudad) {
        this.ciudad = ciudad;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
    
    public List<CiudadEntrega> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<CiudadEntrega> ciudades) {
        this.ciudades = ciudades;
    }
    
    public ciudadController() {
        ciudad = new CiudadEntrega();
        cargarCiudades();
    }
    
    public void cargarCiudades(){
        ciudades = nciu.mostrarCiudades();
    }
    
    private void enviarMensaje(Integer tipo, String clase){
        try{
            Connection  connection =queueConnection.createConnection();
            Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer=session.createProducer(queue);
            ObjectMessage message=session.createObjectMessage();
            message.setIntProperty("tipo",tipo);
            message.setStringProperty("clase",clienteController.CLASE);
            message.setObject(ciudad);

            producer.send(message);

            producer.close();
            session.close();
            connection.close();
            switch(tipo){
                case 1:
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Cliente ingresado correctamente"));
                break;
                case 2:
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Cliente eliminado correctamente"));
                break;
                case 3:
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Cliente modificado correctamente"));
                break;
                default:
                    System.out.println("Fail backing bean");
                break;
            }
            
        }catch(Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error en la transacción"));
        }
    }
    
    public void ingresar(){
        /*if (nciu.insertar(this.ciudad.getCiuNombre())==1)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Ciudad ingresada correctamente"));
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al insertar la ciudad"));*/
        this.enviarMensaje(1, ciudadController.CIUDAD);
        ciudad = new CiudadEntrega();
        cargarCiudades();
    }
    
    public void eliminar(){
        /*if (nciu.eliminar(this.ciudad.getCiuCodigo())==1)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Ciudad eliminada correctamente"));
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al eliminar la ciudad"));*/
        this.enviarMensaje(2, ciudadController.CIUDAD);
        ciudad = new CiudadEntrega();
        cargarCiudades();
    }
    
    public void modificar(){
        /*if (nciu.modificar(this.ciudad.getCiuCodigo(), this.ciudad.getCiuNombre())==1)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Ciuadad modificada correctamente"));
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al modificar la ciudad"));*/
        this.enviarMensaje(3, ciudadController.CIUDAD);
        ciudad = new CiudadEntrega();
        cargarCiudades();
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
        cargarCiudades();
    }
    
    private void ordenarL(int orden){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();
        switch(orden){
            case 1:
                
                try{
                    ciudades = em1.createNamedQuery("CiudadEntrega.orderCodigo",CiudadEntrega.class).getResultList();
                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                }finally{
                    em1.close();
                    factory.close();
                }
                break;
            case 2:
                try{
                    ciudades = em1.createNamedQuery("CiudadEntrega.orderNombre",CiudadEntrega.class).getResultList();
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
