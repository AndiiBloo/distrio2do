/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck_fact_conta.backingbean;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import pck_fact_conta.entidades.Usuarios;
import pck_fact_conta.negocio.negocio_usuario;

/**
 *
 * @author Marco Rodriguez
 */
@ManagedBean
@SessionScoped
public class usuarioController {
    private Usuarios usuario;
    private negocio_usuario neg_us;
    private Map<String,BigInteger> roles;
    
    public usuarioController() {
        this.usuario = new Usuarios();
        this.neg_us = new negocio_usuario();
        this.roles = new HashMap<>();
        this.roles.put("Administrador",BigInteger.valueOf(1));
        this.roles.put("Facturacion",BigInteger.valueOf(2));
        this.roles.put("Contabilidad",BigInteger.valueOf(3));
        this.roles.put("Fac-Simple-1",BigInteger.valueOf(4));
        this.roles.put("Fac-Simple-2",BigInteger.valueOf(5));
        this.roles.put("Fac-Compuesta",BigInteger.valueOf(6));
        this.roles.put("Cont-Simple-1",BigInteger.valueOf(7));
        this.roles.put("Cont-Simple-2",BigInteger.valueOf(8));
        this.roles.put("Cont-Compuesta",BigInteger.valueOf(9));
        
    }
    public void insertarUsuario(){
        
        if (neg_us.insertar(this.usuario.getUsNombre(), this.usuario.getUsPassword(), this.usuario.getUsRol())==1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Usuario ingresado correctamente"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al insertar el Usuario"));
        }
    }
    public void modificarUusario() {
        
        if (neg_us.modificar(this.usuario.getUsCodigo(),this.usuario.getUsNombre(), this.usuario.getUsPassword(), this.usuario.getUsRol())==1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Usuario modificado correctamente"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al modificar el Usuario"));
        }
    }
    public void buscarUsuario() {
        
        this.usuario = this.neg_us.buscar(this.usuario.getUsCodigo()).get(0);
        
        if (this.usuario != null) {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Usuario encontrado"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al ancontrar el Usuario"));
            this.usuario= new Usuarios();
        }
    }
    public void eliminarUsuario() {
        
        if (neg_us.eliminar(this.usuario.getUsCodigo())==1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Usuario eliminado correctamente"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al eliminar el Usuario"));
        }
    }
    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Map<String, BigInteger> getRoles() {
        return roles;
    }

    public void setRoles(Map<String, BigInteger> roles) {
        this.roles = roles;
    }
    
    
}
