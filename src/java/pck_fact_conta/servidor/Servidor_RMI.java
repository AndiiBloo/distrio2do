/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck_fact_conta.servidor;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import pck_fact_conta.negocio.negocio_comprobante;
import pck_fact_conta.negocio.negocio_detalle;

/**
 *
 * @author Marco Rodriguez
 */
public class Servidor_RMI {
    public Servidor_RMI() throws RemoteException
    {    
        try{
            Registry registro=LocateRegistry.createRegistry(2500);
            registro.rebind("rmi://localhost:2500/RMI_Comprobate", new negocio_comprobante());
            registro.rebind("rmi://localhost:2500/RMI_DetalleCom", new negocio_detalle());            
            System.out.println("Servidor activo");   
        }
        catch(Exception e)
        {               
        }
     }   
  
    public static void main(String[] Args)
    { 
        try
        {
            new Servidor_RMI();
        }
        catch (Exception ex)
        {
        }
    }
}
