package pck_fact_conta.negocio;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import pck_fact_conta.entidades.Articulos;
import pck_fact_conta.entidades.Factura;

public class negocio_articulo {
    int ok;
    public int insertar(Factura numF, String nombreA, float precioA, BigInteger cantidadA){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();
        pck_fact_conta.entidades.Articulos c1 = new pck_fact_conta.entidades.Articulos();                  
        c1.setArtCodigo(BigDecimal.ZERO);
        c1.setFacNumero(numF);
        c1.setArtNombre(nombreA);
        c1.setArtPrecio(precioA);
        c1.setArtCantidad(cantidadA);
        
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
        pck_fact_conta.entidades.Articulos c1 = new pck_fact_conta.entidades.Articulos();                  
        c1.setArtCodigo(codigo);            
        try{
            c1=em1.find(Articulos.class,codigo);
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
    
    public int modificar(BigDecimal codigo, Factura numF, String nombreA, float precioA, BigInteger cantidadA){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();
        pck_fact_conta.entidades.Articulos c1 = new pck_fact_conta.entidades.Articulos();                  

        try{
            c1 = em1.find(Articulos.class,codigo);
            em1.getTransaction().begin();
            c1.setFacNumero(numF);
            c1.setArtNombre(nombreA);
            c1.setArtPrecio(precioA);
            c1.setArtCantidad(cantidadA);
            
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
    
    public List<Articulos> mostrarArticulos(Factura facNumero){
        List<Articulos> listArticulos = new ArrayList<>();
        
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();
        try{
            listArticulos = em1.createNamedQuery("Articulos.findByFacNumero", Articulos.class).setParameter("facNumero", facNumero).getResultList();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally{
            em1.close();
            factory.close();
        }
        return listArticulos;
    }
    
    public BigDecimal obtenerCodigo(Factura numF, String nombreA){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();
        pck_fact_conta.entidades.Articulos c1 = new pck_fact_conta.entidades.Articulos();                  
        BigDecimal codigo = null;
        try{
        codigo = em1.createNamedQuery("Articulos.findCod", BigDecimal.class)
                            .setParameter("facNum", numF)
                            .setParameter("artNom", nombreA).getSingleResult();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally{
            em1.close();
            factory.close();
        }
        
        return codigo;
    }
    public double total()
    {
        double total=0;
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();        
        total=Double.valueOf(em1.createNamedQuery("Articulos.totalSuma").getSingleResult().toString());        
        em1.close();
        factory.close();
        return total;
    }
}
