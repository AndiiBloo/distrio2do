package pck_fact_conta.negocio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import pck_fact_conta.entidades.CiudadEntrega;
@MessageDriven(mappedName = "destino_jms_al", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class negocio_ciudad implements MessageListener{
    int ok;
    
    @Override
    public void onMessage(Message message) {
        try
        {
            
            ObjectMessage msg =(ObjectMessage) message;
            
            if(msg.getStringProperty("clase").equals("ciudad")){
                CiudadEntrega ciu = new CiudadEntrega();
                int tipo = msg.getIntProperty("tipo");
                ciu = (CiudadEntrega) msg.getObject();
            
                switch(tipo){
                    case 1: 
                        this.insertar(ciu.getCiuNombre());
                        break;
                    case 2:
                        this.eliminar(ciu.getCiuCodigo());
                        break;
                    case 3:
                        this.modificar(ciu.getCiuCodigo(), ciu.getCiuNombre());
                        break;
                    default:
                        System.out.println("Error msj");
                        break;
                }
                System.out.print("Todo ok ciudad");
            }

        }
        catch (Exception ex)
        {
            System.out.println("epic fail ciduad");
            ex.printStackTrace();
        }
    }
    public int insertar(String nombre){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();
        pck_fact_conta.entidades.CiudadEntrega c1 = new pck_fact_conta.entidades.CiudadEntrega();                  
        c1.setCiuCodigo(BigDecimal.ZERO);
        c1.setCiuNombre(nombre);
        
        try{   
            em1.getTransaction().begin();
            em1.persist(c1);
            em1.getTransaction().commit();
            ok = 1;
            
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            ok = 0;
        }finally{
            em1.close();
            factory.close();
        }
        return ok;
    }
    
    public int eliminar(BigDecimal codigo){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();           
        pck_fact_conta.entidades.CiudadEntrega c1 = new pck_fact_conta.entidades.CiudadEntrega();                  
        c1.setCiuCodigo(codigo);            
        try{
            c1=em1.find(CiudadEntrega.class,codigo);
            em1.getTransaction().begin();
            em1.remove(c1);
            em1.getTransaction().commit();
            ok = 1;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            ok = 0;
        }finally{
            em1.close();
            factory.close();
        }
        return ok;
    }
    
    public int modificar(BigDecimal codigo, String nombre){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();             
        pck_fact_conta.entidades.CiudadEntrega c1 = new pck_fact_conta.entidades.CiudadEntrega();                  

        try{
            c1 = em1.find(CiudadEntrega.class,codigo);
            em1.getTransaction().begin();
            c1.setCiuNombre(nombre);
            
            em1.persist(c1);
            em1.getTransaction().commit();
            ok = 1;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            ok = 0;
        }finally{
            em1.close();
            factory.close();
        }
        return ok;
    }
     
    public CiudadEntrega buscar(BigDecimal codigo){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();        
        pck_fact_conta.entidades.CiudadEntrega c1 = new pck_fact_conta.entidades.CiudadEntrega();                  
        
        try{
            c1 = em1.find(CiudadEntrega.class,codigo);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            c1 = null;
        }finally{
            em1.close();
            factory.close();
        }
        return c1;
    }
    
    public List<CiudadEntrega> mostrarCiudades(){
        List<CiudadEntrega> listCiudades = new ArrayList<>();
        
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();
        
        try{
            listCiudades = em1.createNamedQuery("CiudadEntrega.findAll",CiudadEntrega.class).getResultList();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally{
            em1.close();
            factory.close();
        }
        return listCiudades;
    }
}
