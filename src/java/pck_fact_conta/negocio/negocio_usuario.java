/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck_fact_conta.negocio;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import pck_fact_conta.entidades.Usuarios;

/**
 *
 * @author Andrés López
 */
public class negocio_usuario {
    int ok;
    public Usuarios buscar(String nombre, String password){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pdist_fact_contaPU");
        EntityManager em1 = factory.createEntityManager();
        pck_fact_conta.entidades.Usuarios c1 = new pck_fact_conta.entidades.Usuarios();
        try{
            c1 = (Usuarios)em1.createQuery("SELECT u FROM Usuarios u "
                    + "WHERE u.usNombre LIKE :nombre").setParameter("nombre", nombre).getSingleResult();
            System.out.println(c1.getUsPassword());
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
