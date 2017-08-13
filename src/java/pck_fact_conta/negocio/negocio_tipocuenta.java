package pck_fact_conta.negocio;

import java.math.BigDecimal;
import pck_fact_conta.entidades.Tipocuenta;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.ArrayList;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(mappedName = "destino_jms", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
}) 
public class negocio_tipocuenta implements MessageListener
{
    
    private String estado;
    private String mensaje;  
    
    public void insertar(String nombre)
    {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();
        pck_fact_conta.entidades.Tipocuenta c1 = new pck_fact_conta.entidades.Tipocuenta();
        c1.setTdcCodigo(BigDecimal.ZERO);
        c1.setTdcNombre(nombre);
        
        try{   
            em1.getTransaction().begin();
            em1.persist(c1);
            em1.getTransaction().commit();
            estado="Correcto!";
            mensaje="Tipo de Cuenta ingresada correctamente";
            
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            estado="Error!";
            mensaje="Error al insertar el Tipo de Cuenta";
        }
        em1.close();
        factory.close();
        imprimirMensaje();
    }
    
    public void eliminar(BigDecimal codigo)
    {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();           
        pck_fact_conta.entidades.Tipocuenta c1 = new pck_fact_conta.entidades.Tipocuenta();                  
        c1.setTdcCodigo(codigo);            
        try{
            c1=em1.find(Tipocuenta.class,codigo);
            em1.getTransaction().begin();
            em1.remove(c1);
            em1.getTransaction().commit();
            estado="Correcto!";
            mensaje="Tipo de Cuenta eliminada correctamente";
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            estado="Error!";
            mensaje="Error al eliminar el Tipo de Cuenta";
        }
        em1.close();
        factory.close();
        imprimirMensaje();
     }
    
     public void modificar(BigDecimal codigo, String nombre)
     {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();             
        pck_fact_conta.entidades.Tipocuenta c1 = new pck_fact_conta.entidades.Tipocuenta();                  

        try{
            c1 = em1.find(Tipocuenta.class,codigo);
            em1.getTransaction().begin();
            c1.setTdcNombre(nombre);            
            em1.persist(c1);
            em1.getTransaction().commit();
            estado="Correcto!";
            mensaje="Tipo de Cuenta modificada correctamente";
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            estado="Error!";
            mensaje="Error al modificar el Tipo de Cuenta";
        } 
        em1.close();
        factory.close();
        imprimirMensaje();
     }
     
    
    public String buscar(BigDecimal codigo)
    {
        
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager(); 
        pck_fact_conta.entidades.Tipocuenta c1 = new pck_fact_conta.entidades.Tipocuenta();                  
        String nombre = null;
        try{
            c1 = em1.find(Tipocuenta.class,codigo);
            nombre = c1.getTdcNombre();
            estado="Correcto!";
            mensaje="Tipo de Cuenta encontrada correctamente";
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            nombre = null;
            estado="Error!";
            mensaje="Error al modificar el Tipo de Cuenta";
        } 
        em1.close();
        factory.close();
        imprimirMensaje();
        return nombre;
    }
    public List<Tipocuenta> mostrar()
    {
        List<Tipocuenta> datos = new ArrayList<>();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();        
        datos=em1.createNamedQuery("Tipocuenta.findAll").getResultList();        
        em1.close();
        factory.close();
        return datos;
    }
    public List<Tipocuenta> mostrarPorCodigo()
    {
        List<Tipocuenta> datos = new ArrayList<>();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();        
        datos=em1.createNamedQuery("Tipocuenta.sortCodigo").getResultList();        
        em1.close();
        factory.close();
        return datos;
    }
    public List<Tipocuenta> mostrarPorNombre()
    {
        List<Tipocuenta> datos = new ArrayList<>();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();        
        datos=em1.createNamedQuery("Tipocuenta.sortName").getResultList();        
        em1.close();
        factory.close();
        return datos;
    }  
    private void imprimirMensaje() {
        System.out.println(estado +", "+mensaje);
    }
    @Override
    public void onMessage(Message message) {
        try
        {   
            MapMessage msg=(MapMessage ) message;
            String ventana = msg.getString("ventana");
            if(ventana.equals("tipocuenta")) {
               String accion = msg.getString("accion");
                Tipocuenta tipocuenta= new Tipocuenta();
                tipocuenta.setTdcCodigo(BigDecimal.valueOf(Double.valueOf(msg.getString("codigo"))));
                tipocuenta.setTdcNombre(msg.getString("nombre"));
                switch(accion) {                
                    case "insertar":
                        this.insertar(tipocuenta.getTdcNombre());
                        break;
                    case "eliminar":
                        this.eliminar(tipocuenta.getTdcCodigo());
                        break;
                    case "modificar":
                        this.modificar(tipocuenta.getTdcCodigo(), tipocuenta.getTdcNombre());
                        break;
                } 
            }
        }
        catch (Exception ex){
            
            ex.printStackTrace();
            
        }
    }
}
