package pck_fact_conta.negocio;

import java.math.BigDecimal;
import pck_fact_conta.entidades.Cuenta;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.ArrayList;
import pck_fact_conta.entidades.Tipocuenta;

public class negocio_cuenta {
    int validar;
    public int insertar(String nombre, Tipocuenta tipo)
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
            validar = 1;
            
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            validar = 0;
        }
        em1.close();
        factory.close();
        return validar;
    }
    
    public int eliminar(BigDecimal codigo){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist2_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();           
        pck_fact_conta.entidades.Cuenta c1 = new pck_fact_conta.entidades.Cuenta();                  
        c1.setCueCodigo(codigo);            
        try{
            c1=em1.find(Cuenta.class,codigo);
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
    
     public int modificar(BigDecimal codigo, String nombre, Tipocuenta tipo)
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
            validar = 1;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            validar = 0;
        } 
        em1.close();
        factory.close();
        return validar;
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
     public void procesar()
     {
	// programar el código de la regla de negocio         
     }    
}
