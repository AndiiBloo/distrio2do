/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck_fact_conta.negocio;

import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import pck_fact_conta.entidades.Comprobantecontabilidad;
import pck_fact_conta.entidades.Cuenta;

/**
 *
 * @author Marco Rodriguez
 */
public class negocio_detalle 
{
    int validar;
    public int insertar(Comprobantecontabilidad comprobante, Cuenta cuenta, Double debe, Double haber )
    {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist_fact_contaPU");
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
    
}
