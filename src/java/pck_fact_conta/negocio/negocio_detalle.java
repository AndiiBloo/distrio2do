package pck_fact_conta.negocio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import pck_fact_conta.entidades.Comprobantecontabilidad;
import pck_fact_conta.entidades.Cuenta;
import pck_fact_conta.entidades.Detallecomprobantecontabilidad;

public class negocio_detalle 
{
    int validar;
    public int insertar(Comprobantecontabilidad comprobante, Cuenta cuenta, Double debe, Double haber )
    {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();
        pck_fact_conta.entidades.Detallecomprobantecontabilidad c1 = new pck_fact_conta.entidades.Detallecomprobantecontabilidad();                  
        c1.setComNumero(comprobante);
        c1.setCueCodigo(cuenta);
        c1.setDccDebe(debe);
        c1.setDccHaber(haber);
        
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
   public List<Detallecomprobantecontabilidad> buscar(Comprobantecontabilidad comprobante) {
        List<Detallecomprobantecontabilidad> datos = new ArrayList<>();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();        
        datos=em1.createNamedQuery("Detallecomprobantecontabilidad.findByComNumero").setParameter("comNumero",comprobante).getResultList();        
        em1.close();
        factory.close();
        return datos;
    }
   public int eliminar(BigDecimal codigo) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();
        pck_fact_conta.entidades.Detallecomprobantecontabilidad c1 = new pck_fact_conta.entidades.Detallecomprobantecontabilidad();                  
        c1.setDccCodigo(codigo);    
        try{
            c1=em1.find(Detallecomprobantecontabilidad.class,codigo);
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
   public int modificar(Comprobantecontabilidad comprobante,BigDecimal codigo, Cuenta cuenta, Double debe, Double haber) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();             
        pck_fact_conta.entidades.Detallecomprobantecontabilidad c1 = new pck_fact_conta.entidades.Detallecomprobantecontabilidad();                  

        try{
            c1 = em1.find(Detallecomprobantecontabilidad.class,codigo);
            em1.getTransaction().begin();
            c1.setComNumero(comprobante);
            c1.setCueCodigo(cuenta);
            c1.setDccDebe(debe);
            c1.setDccHaber(haber);           
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
}
