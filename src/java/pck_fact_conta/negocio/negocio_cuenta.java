package pck_fact_conta.negocio;

import java.math.BigDecimal;
import pck_fact_conta.entidades.Cuenta;
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
import pck_fact_conta.entidades.Tipocuenta;

@MessageDriven(mappedName = "destino_jms", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
}) 
public class negocio_cuenta implements MessageListener{
    
    private String estado;
    private String mensaje; 
    
    public void insertar(String nombre, Tipocuenta tipo)
    {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();
        pck_fact_conta.entidades.Cuenta c1 = new pck_fact_conta.entidades.Cuenta();                  
        c1.setCueCodigo(BigDecimal.ZERO);
        c1.setCueNombre(nombre);
        c1.setTdcCodigo(tipo);
        
        try{   
            em1.getTransaction().begin();
            em1.persist(c1);
            em1.getTransaction().commit();
            estado="Correcto!";
            mensaje="Cuenta ingresada correctamente";
            
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            estado="Error!";
            mensaje="Error al insertar la Cuenta";
        }
        em1.close();
        factory.close();
        imprimirMensaje();
    }
    
    public void eliminar(BigDecimal codigo){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();           
        pck_fact_conta.entidades.Cuenta c1 = new pck_fact_conta.entidades.Cuenta();                  
        c1.setCueCodigo(codigo);            
        try{
            c1=em1.find(Cuenta.class,codigo);
            em1.getTransaction().begin();
            em1.remove(c1);
            em1.getTransaction().commit();
            estado="Correcto!";
            mensaje="Cuenta eliminada correctamente";
            
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            estado="Error!";
            mensaje="Error al eliminar la Cuenta";
        }
        em1.close();
        factory.close();
        imprimirMensaje();
     }
    
     public void modificar(BigDecimal codigo, String nombre, Tipocuenta tipo)
     {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();             
        pck_fact_conta.entidades.Cuenta c1 = new pck_fact_conta.entidades.Cuenta();                  

        try{
            c1 = em1.find(Cuenta.class,codigo);
            em1.getTransaction().begin();
            c1.setCueNombre(nombre);
            c1.setTdcCodigo(tipo);
            
            em1.persist(c1);
            em1.getTransaction().commit();
            estado="Correcto!";
            mensaje="Cuenta modificada correctamente";
            
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            estado="Error!";
            mensaje="Error al modificar la Cuenta";
        }
        em1.close();
        factory.close();
        imprimirMensaje();
     }
     
    public List<Cuenta> buscar(BigDecimal codigo)
    {
        List<Cuenta> datos = new ArrayList<>();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();        
        pck_fact_conta.entidades.Cuenta c1;                  
        
        try{
            c1 = em1.find(Cuenta.class,codigo);
            datos.add(c1);
        }catch 
                (Exception ex)
        {
            System.out.println(ex.getMessage()); 
            c1 = null;
            datos.add(c1);
        } 
        em1.close();
        factory.close();
        return datos;
     }
     public List<Cuenta> mostrar()
    {
        List<Cuenta> datos = new ArrayList<>();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();        
        datos=em1.createNamedQuery("Cuenta.findAll").getResultList();        
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
            if(ventana.equals("cuenta")) {                
                String accion = msg.getString("accion");
                Cuenta cuenta = new Cuenta();
                cuenta.setCueCodigo(BigDecimal.valueOf(Double.valueOf(msg.getString("codigo"))));
                cuenta.setCueNombre(msg.getString("nombre"));
                Tipocuenta tipocuenta= new Tipocuenta();
                tipocuenta.setTdcCodigo(BigDecimal.valueOf(Double.valueOf(msg.getString("codigotc"))));
                tipocuenta.setTdcNombre(msg.getString("nombre"));                
                switch(accion) {                
                    case "insertar":
                        this.insertar(cuenta.getCueNombre(), tipocuenta);
                        break;
                    case "eliminar":
                        this.eliminar(cuenta.getCueCodigo());
                        break;
                    case "modificar":
                        this.modificar(cuenta.getCueCodigo(), cuenta.getCueNombre(), tipocuenta);
                        break;
                } 
            }
        }
        catch (Exception ex){
            
            ex.printStackTrace();
            
        }
    }
}
