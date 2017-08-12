/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck_fact_conta.interfaces;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import pck_fact_conta.entidades.Comprobantecontabilidad;

/**
 *
 * @author Marco Rodriguez
 */
public interface IComprobante extends Remote {
    public int insertar(Date fecha, String observaciones) throws RemoteException;
    public int eliminar(BigDecimal codigo) throws RemoteException;
    public int modificar(BigDecimal codigo, Date fecha, String observaciones) throws RemoteException;
    public List<Comprobantecontabilidad> buscar(BigDecimal codigo) throws RemoteException;
    public BigDecimal maximo() throws RemoteException;
}

