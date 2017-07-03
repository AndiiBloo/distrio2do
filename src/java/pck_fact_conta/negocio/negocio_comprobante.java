package pck_fact_conta.negocio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import pck_fact_conta.entidades.Comprobantecontabilidad;
import pck_fact_conta.entidades.Detallecomprobantecontabilidad;

public class negocio_comprobante 
{
    int validar;
    public int insertar(Date fecha, String observaciones)
    {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();
        pck_fact_conta.entidades.Comprobantecontabilidad c1 = new pck_fact_conta.entidades.Comprobantecontabilidad();                  
        c1.setComNumero(BigDecimal.ZERO);
        c1.setComFecha(fecha);
        c1.setComObservaciones(observaciones);
        
        try{   
            em1.getTransaction().begin();
            em1.persist(c1);
            em1.getTransaction().commit();
            validar = 1;
            
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            validar = 0;
        }        
        em1.close();
        factory.close();
        return validar;
    }
    
    public int eliminar(BigDecimal codigo)
    {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();
        pck_fact_conta.entidades.Comprobantecontabilidad c1 = new pck_fact_conta.entidades.Comprobantecontabilidad();                  
        c1.setComNumero(codigo);            
        try{
            c1=em1.find(Comprobantecontabilidad.class,codigo);
            em1.getTransaction().begin();
            em1.remove(c1);
            em1.getTransaction().commit();
            validar = 1;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            validar = 0;
        }             
        em1.close();
        factory.close();
        return validar;
     }
    
     public int modificar(BigDecimal codigo, Date fecha, String observaciones)
     {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();             
        pck_fact_conta.entidades.Comprobantecontabilidad c1 = new pck_fact_conta.entidades.Comprobantecontabilidad();                  

        try{
            c1 = em1.find(Comprobantecontabilidad.class,codigo);
            em1.getTransaction().begin();
            c1.setComFecha(fecha);
            c1.setComObservaciones(observaciones);            
            em1.persist(c1);
            em1.getTransaction().commit();
            validar = 1;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            validar = 0;
        }        
        em1.close();
        factory.close();
        return validar;
     }
     
    public List<Comprobantecontabilidad> buscar(BigDecimal codigo)
    {
        List<Comprobantecontabilidad> datos = new ArrayList<>();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();        
        pck_fact_conta.entidades.Comprobantecontabilidad c1 = new pck_fact_conta.entidades.Comprobantecontabilidad();                  
        
        try{
            c1 = em1.find(Comprobantecontabilidad.class,codigo);            
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
    public BigDecimal maximo()
    {
        BigDecimal max=BigDecimal.ZERO;
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();        
        max= BigDecimal.valueOf(Double.valueOf(em1.createNamedQuery("Comprobantecontabilidad.maxComNumero").getSingleResult().toString()));        
        em1.close();
        factory.close();
        
        return max;
    }
     public void procesar()
     {
	// programar el código de la regla de negocio         
     }    
}


