package pck_fact_conta.backingbean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.jms.Connection;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;
import pck_fact_conta.entidades.Cuenta;
import pck_fact_conta.negocio.negocio_cuenta;
import pck_fact_conta.negocio.negocio_tipocuenta;
import pck_fact_conta.entidades.Tipocuenta;

@ManagedBean
@ViewScoped
public class cuentaController implements Serializable {
    @Resource(mappedName="conexion_jms")
    javax.jms.QueueConnectionFactory queueConnection;
    @Resource(mappedName="destino_jms")
    javax.jms.Queue queue;
    
    private Cuenta cuenta;
    final private negocio_tipocuenta negotipcue;
    final private negocio_cuenta negocue;
    private List<Tipocuenta> tiposcuenta;
    private List<Cuenta> cuentas;
    public cuentaController() {
        this.cuenta = new Cuenta();
        this.negotipcue = new negocio_tipocuenta();
        this.negocue = new negocio_cuenta();
        this.tiposcuenta = this.negotipcue.mostrar();
        this.cargarCuentas();
    }
    
    public void guardarCuenta() {
        
        this.enviarMensaje("insertar");
        this.cargarCuentas();
        /*if(negocue.insertar(this.cuenta.getCueNombre(), this.cuenta.getTdcCodigo()) == 1) {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Cuenta insertada correctamente"));
        } else {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al insertar la Cuenta"));
             
        }*/
    }
    public void obtenerCuenta() {
        this.cuenta = this.negocue.buscar(this.cuenta.getCueCodigo()).get(0);        
        if(this.cuenta != null) {
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Cuenta encontrada"));
        } else {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al encontrar la Cuenta"));
             this.cuenta = new Cuenta();
        }        
        this.cargarCuentas();
    }
    public void modificarCuenta() {
       
        this.enviarMensaje("modificar");
        this.cargarCuentas();
        /*if (negocue.modificar(this.cuenta.getCueCodigo(),this.cuenta.getCueNombre(),this.cuenta.getTdcCodigo())==1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Cuenta modificada correctamente"));
        } else {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al modificar la Cuenta"));
        }*/
    }
    public void eliminarCuenta() {
        
        this.enviarMensaje("eliminar");
        this.cargarCuentas();
        /*if (negocue.eliminar(this.cuenta.getCueCodigo())==1) {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Cuenta eliminada correctamente"));
        } else {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al eliminar la Cuenta"));
        }*/
    }
    public void cargarCuentas(){
        cuentas = negocue.mostrar();
    }
    private void enviarMensaje(String accion) {
        try {
            Connection  connection = queueConnection.createConnection();
            Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer=session.createProducer(queue);
            MapMessage message=session.createMapMessage();
            message.setString("ventana","cuenta");
            message.setString("accion",accion);
            message.setString("codigo",String.valueOf(cuenta.getCueCodigo()));
            message.setString("nombre",cuenta.getCueNombre());
            message.setString("codigotc",String.valueOf(cuenta.getTdcCodigo().getTdcCodigo()));
            producer.send(message);
            producer.close();
            session.close();
            connection.close();        
        }
        catch(Exception ex){            
            ex.printStackTrace();
        }
    }
    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
    
    public List<Tipocuenta> getTiposcuenta() {
        return tiposcuenta;
    }

    public void setTiposcuenta(List<Tipocuenta> tiposcuenta) {
        this.tiposcuenta = tiposcuenta;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }
    
    
}
