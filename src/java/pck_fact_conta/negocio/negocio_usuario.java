package pck_fact_conta.negocio;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import pck_fact_conta.entidades.Usuarios;

public class negocio_usuario {
    int ok;
    public int insertar(String nombre, String password, BigInteger rol){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();
        pck_fact_conta.entidades.Usuarios c1 = new pck_fact_conta.entidades.Usuarios();                  
        c1.setUsCodigo(BigDecimal.ZERO);
        c1.setUsNombre(nombre);
        c1.setUsPassword(password);
        c1.setUsRol(rol);
        
        try{   
            em1.getTransaction().begin();
            em1.persist(c1);
            em1.getTransaction().commit();
            ok = 1;
            
        }catch (Exception ex){
            ex.printStackTrace();
            //System.out.println(ex.getMessage());
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
        pck_fact_conta.entidades.Usuarios c1 = new pck_fact_conta.entidades.Usuarios();                  
        c1.setUsCodigo(codigo);            
        try{
            c1=em1.find(Usuarios.class,codigo);
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
    
    public int modificar(BigDecimal codigo, String nombre, String password, BigInteger rol){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();             
        pck_fact_conta.entidades.Usuarios c1 = new pck_fact_conta.entidades.Usuarios();                  

        try{
            c1 = em1.find(Usuarios.class,codigo);
            em1.getTransaction().begin();
            c1.setUsCodigo(codigo);
            c1.setUsNombre(nombre);
            c1.setUsPassword(password);
            c1.setUsRol(rol);
            
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
    
    public List<Usuarios> buscar(BigDecimal codigo){
        List<Usuarios> us = new ArrayList<>();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();        
        pck_fact_conta.entidades.Usuarios c1 = new pck_fact_conta.entidades.Usuarios();                  
        
        try{
            c1 = em1.find(Usuarios.class,codigo);
            us.add(c1);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            us = null;
        }finally{
            em1.close();
            factory.close();
        }
        return us;
    }

    
    public Usuarios buscarUs(String nombre, String password){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();
        pck_fact_conta.entidades.Usuarios c1 = new pck_fact_conta.entidades.Usuarios();
        try{
            c1 = (Usuarios)em1.createQuery("SELECT u FROM Usuarios u "
                    + "WHERE u.usNombre LIKE :nombre").setParameter("nombre", nombre).getSingleResult();
            if(c1.getUsNombre().equals(nombre) && c1.getUsPassword().equals(password))
            {
                return c1;
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            ok = 0;
        }finally{
            em1.close();
            factory.close();
        }
        return null;
     }
}