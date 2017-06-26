/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck_fact_conta.negocio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import pck_fact_conta.entidades.Comprobantecontabilidad;
import pck_fact_conta.entidades.Detallecomprobantecontabilidad;

/**
 *
 * @author Marco Rodriguez
 */
public class negocio_comprobante 
{
    int validar;
    public int insertar(String fecha, String observaciones)
    {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist_fact_contaPU");
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
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();
        pck_fact_conta.entidades.Detallecomprobantecontabilidad det=new Detallecomprobantecontabilidad();
        em1.createNamedQuery("Detallecomprobantecontabilidad.deleteComNumero").setParameter("comNumero",new Comprobantecontabilidad(codigo)).getResultList();
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
    
     public int modificar(BigDecimal codigo, String fecha, String observaciones)
     {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist_fact_contaPU");
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
     
    public List<String> buscar(BigDecimal codigo)
    {
        List<String> datos = new ArrayList<>();
        String fecha;
        String observaciones;
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();        
        pck_fact_conta.entidades.Comprobantecontabilidad c1 = new pck_fact_conta.entidades.Comprobantecontabilidad();                  
        
        try{
            c1 = em1.find(Comprobantecontabilidad.class,codigo);
            fecha=c1.getComFecha();
            observaciones=c1.getComObservaciones();
            datos.add(fecha);
            datos.add(observaciones);
        }catch 
                (Exception ex)
        {
            System.out.println(ex.getMessage()); 
            fecha=null;
            observaciones=null;
            datos.add(fecha);
            datos.add(observaciones);
        } 
        em1.close();
        factory.close();
        return datos;
    }
    public BigDecimal maximo()
    {
        BigDecimal max=BigDecimal.ZERO;
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist_fact_contaPU");
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


