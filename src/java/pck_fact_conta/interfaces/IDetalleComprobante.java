/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck_fact_conta.interfaces;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import pck_fact_conta.entidades.Comprobantecontabilidad;
import pck_fact_conta.entidades.Cuenta;
import pck_fact_conta.entidades.Detallecomprobantecontabilidad;

/**
 *
 * @author Marco Rodriguez
 */
public interface IDetalleComprobante extends Remote{
    
    public int insertar(Comprobantecontabilidad comprobante, Cuenta cuenta, Double debe, Double haber ) throws RemoteException;    
    public List<Detallecomprobantecontabilidad> buscar(Comprobantecontabilidad comprobante)throws RemoteException;
    public int eliminar(BigDecimal codigo) throws RemoteException;
    public int modificar(Comprobantecontabilidad comprobante,BigDecimal codigo, Cuenta cuenta, Double debe, Double haber) throws RemoteException;
    
}
