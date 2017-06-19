package pck_fact_conta.negocio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import pck_fact_conta.entidades.CiudadEntrega;
import pck_fact_conta.entidades.Cliente;
import pck_fact_conta.entidades.Factura;

public class negocio_factura {
    int ok;
    public int insertar(CiudadEntrega codigoC, Cliente rucCl, Date fecha){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();
        pck_fact_conta.entidades.Factura c1 = new pck_fact_conta.entidades.Factura();
        BigDecimal aux = (BigDecimal) em1.createNamedQuery("Factura.maxFacNumero", BigDecimal.class).getSingleResult();
        c1.setFacNumero(aux.add(BigDecimal.ONE));
        c1.setCiuCodigo(codigoC);
        c1.setCliRuc(rucCl);
        c1.setFacFecha(fecha);
        
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
        pck_fact_conta.entidades.Factura c1 = new pck_fact_conta.entidades.Factura();                  
        c1.setFacNumero(codigo);            
        try{
            c1=em1.find(Factura.class,codigo);
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
    
    public int modificar(BigDecimal codigo, CiudadEntrega codigoC, Cliente rucCl, Date fecha){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();             
        pck_fact_conta.entidades.Factura c1 = new pck_fact_conta.entidades.Factura();                  

        try{
            c1 = em1.find(Factura.class,codigo);
            em1.getTransaction().begin();
            
            c1.setCiuCodigo(codigoC);
            c1.setCliRuc(rucCl);
            c1.setFacFecha(fecha);
            
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
     
    public List<Object> buscar(BigDecimal codigo){
        List<Object> datos = new ArrayList<>();
        CiudadEntrega codigoC;
        Cliente rucCl;
        Date fecha;
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();        
        pck_fact_conta.entidades.Factura c1 = new pck_fact_conta.entidades.Factura();                  
        
        try{
            c1 = em1.find(Factura.class,codigo);
            codigoC = c1.getCiuCodigo();
            rucCl = c1.getCliRuc();
            fecha = c1.getFacFecha();
            datos.add(codigoC);
            datos.add(rucCl);
            datos.add(fecha);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            datos.add(null);
            datos.add(null);
            datos.add(null);
        }finally{
            em1.close();
            factory.close();
        }
        return datos;
    }
    
    public Factura obtenerNum(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();
        BigDecimal aux = (BigDecimal) em1.createNamedQuery("Factura.maxFacNumero", BigDecimal.class).getSingleResult();
        pck_fact_conta.entidades.Factura fac = 
                new pck_fact_conta.entidades.Factura(aux.add(BigDecimal.ONE));
        em1.close();
        factory.close();
        return fac;
    }
}
