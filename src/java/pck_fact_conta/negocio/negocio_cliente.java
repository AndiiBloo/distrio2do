package pck_fact_conta.negocio;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.ArrayList;
import pck_fact_conta.entidades.Cliente;

public class negocio_cliente {
    int ok;
    public int insertar(String ruc, String nombre, String direccion){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();
        pck_fact_conta.entidades.Cliente c1 = new pck_fact_conta.entidades.Cliente(ruc);                  
        c1.setCliRuc(ruc);
        c1.setCliNombre(nombre);
        c1.setCliDireccion(direccion);
        
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
    
    public int eliminar(String ruc){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();           
        pck_fact_conta.entidades.Cliente c1 = new pck_fact_conta.entidades.Cliente();                  
        c1.setCliRuc(ruc);            
        try{
            c1=em1.find(Cliente.class,ruc);
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
    
    public int modificar(String ruc, String nombre, String direccion){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();             
        pck_fact_conta.entidades.Cliente c1 = new pck_fact_conta.entidades.Cliente();                  

        try{
            c1 = em1.find(Cliente.class,ruc);
            em1.getTransaction().begin();
            c1.setCliNombre(nombre);
            c1.setCliDireccion(direccion);
            
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
     
    public Cliente buscar(String ruc){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();        
        pck_fact_conta.entidades.Cliente c1 = new pck_fact_conta.entidades.Cliente();                  
        
        try{
            c1 = em1.find(Cliente.class,ruc); 
            c1.setCliRuc(ruc);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            c1 = null;
        }finally{
            em1.close();
            factory.close();
        }
        return c1;
    }
    
    public List<Cliente> mostrarClientes(){
        List<Cliente> listClientes = new ArrayList<>();
        
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();
        
        try{
            listClientes = em1.createNamedQuery("Cliente.findAll",Cliente.class).getResultList();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally{
            em1.close();
            factory.close();
        }
        return listClientes;
    }  
}
