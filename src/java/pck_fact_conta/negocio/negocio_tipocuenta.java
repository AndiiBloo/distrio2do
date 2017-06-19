package pck_fact_conta.negocio;

import java.math.BigDecimal;
import pck_fact_conta.entidades.Tipocuenta;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.Query;

public class negocio_tipocuenta 
{
    int validar;
    public int insertar(String nombre)
    {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();
        pck_fact_conta.entidades.Tipocuenta c1 = new pck_fact_conta.entidades.Tipocuenta();
        c1.setTdcCodigo(BigDecimal.ZERO);
        c1.setTdcNombre(nombre);
        
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
        pck_fact_conta.entidades.Tipocuenta c1 = new pck_fact_conta.entidades.Tipocuenta();                  
        c1.setTdcCodigo(codigo);            
        try{
            c1=em1.find(Tipocuenta.class,codigo);
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
    
     public int modificar(BigDecimal codigo, String nombre)
     {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();             
        pck_fact_conta.entidades.Tipocuenta c1 = new pck_fact_conta.entidades.Tipocuenta();                  

        try{
            c1 = em1.find(Tipocuenta.class,codigo);
            em1.getTransaction().begin();
            c1.setTdcNombre(nombre);            
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
        String nombre;
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist_fact_contaPU");
        EntityManager em1 = factory.createEntityManager(); 
        pck_fact_conta.entidades.Tipocuenta c1 = new pck_fact_conta.entidades.Tipocuenta();                  
        
        try{
            c1 = em1.find(Tipocuenta.class,codigo);
            nombre = c1.getTdcNombre();
            datos.add(nombre);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            nombre = null;
            datos.add(nombre);
        } 
        em1.close();
        factory.close();
        return datos;
    }
    public List<Tipocuenta> mostrar()
    {
        List<Tipocuenta> datos = new ArrayList<>();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();        
        datos=em1.createNamedQuery("Tipocuenta.findAll").getResultList();        
        em1.close();
        factory.close();
        return datos;
    }
     public void procesar()
     {
	// programar el código de la regla de negocio         
     }    
}
