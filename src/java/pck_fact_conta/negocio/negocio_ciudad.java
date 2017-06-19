/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck_fact_conta.negocio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import pck_fact_conta.entidades.CiudadEntrega;

/**
 *
 * @author Andrés López
 */
public class negocio_ciudad {
    int ok;
    public int insertar(String nombre){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist_fact_contaPU");
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
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist_fact_contaPU");
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
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist_fact_contaPU");
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
     
    public String buscar(BigDecimal codigo){
        String nombre;
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();        
        pck_fact_conta.entidades.CiudadEntrega c1 = new pck_fact_conta.entidades.CiudadEntrega();                  
        
        try{
            c1 = em1.find(CiudadEntrega.class,codigo);
            nombre = c1.getCiuNombre();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            nombre = null;
        }finally{
            em1.close();
            factory.close();
        }
        return nombre;
    }
    
    public List<CiudadEntrega> mostrarCiudades(){
        List<CiudadEntrega> listCiudades = new ArrayList<>();
        
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist_fact_contaPU");
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
