package pck_fact_conta.backingbean;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import pck_fact_conta.entidades.Tipocuenta;
import pck_fact_conta.negocio.negocio_tipocuenta;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.faces.bean.SessionScoped;
import javax.jms.Connection;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;

@ManagedBean
@SessionScoped
public class tipocuentaController implements Serializable {
    @Resource(mappedName="conexion_jms")
    javax.jms.QueueConnectionFactory queueConnection;
    @Resource(mappedName="destino_jms")
    javax.jms.Queue queue;

    private Tipocuenta tipocuenta = new Tipocuenta();
    final private negocio_tipocuenta negotipcue;
    private List<Tipocuenta> tiposcuenta;
    private String campo;    
    private Map<String,String> campos;
    private int indice; 

    public tipocuentaController() {
        this.negotipcue = new negocio_tipocuenta();
        this.tiposcuenta = this.negotipcue.mostrar();
        this.campo = "";
        this.campos = new HashMap<String,String>();
        this.campos.put("codigo", "codigo");
        this.campos.put("nombre", "nombre");
        this.indice=0;
    }
    
    public void guardarTipoCuenta(){
        
        this.enviarMensaje("insertar");
        this.cargarTiposCuenta();        
        /*
        if (negotipcue.insertar(tipocuenta.getTdcNombre())==1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Tipo de Cuenta ingresada correctamente"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al insertar el Tipo de Cuenta"));
        }*/
    }
    
    public void buscarTipoCuenta(){
         
        tipocuenta.setTdcNombre(negotipcue.buscar(tipocuenta.getTdcCodigo()));

         if(tipocuenta.getTdcNombre() != null) {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Tipo de Cuenta encontrada"));
        } else {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al encontrar el Tipo de Cuenta"));
        }
        this.cargarTiposCuenta();
    }
    
    public void modificarTipoCuenta(){
        
        this.enviarMensaje("modificar");
        this.cargarTiposCuenta();
        /*if (negotipcue.modificar(tipocuenta.getTdcCodigo(),tipocuenta.getTdcNombre())==1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Tipo de Cuenta modificada correctamente"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al modificar el Tipo de Cuenta"));
        }*/
    }
    public void eliminarTipoCuenta(){
        
        this.enviarMensaje("eliminar");
        this.cargarTiposCuenta();
        /*if(negotipcue.eliminar(tipocuenta.getTdcCodigo()) == 1) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto!", "Tipo de Cuenta eliminada correctamente"));
        } else {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "Error al eliminar el Tipo de Cuenta"));
        }*/
    }
    public void cargarTiposCuenta(){
        tiposcuenta = negotipcue.mostrar();
    }
    public void ordenar() {
        if (this.campo.equals("codigo")) {
            
            this.tiposcuenta = this.negotipcue.mostrarPorCodigo();
        }
        if (this.campo.equals("nombre")) {
            
            this.tiposcuenta = this.negotipcue.mostrarPorNombre();
        }
        this.indice =0;
        //this.tipocuenta = this.tiposcuenta.get(0);
    }
    public void siguiente() {
        
        if( (this.indice+1) >= this.tiposcuenta.size()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "No existen elementos despues de este"));
            
        } else {
            this.indice ++;
           //this.tipocuenta = this.tiposcuenta.get(this.indice);
        }
    }
    public void anterior() {
        
        if( (this.indice-1) < 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error!", "No existen elementos antes de este"));
            
        } else {
            this.indice --;
            //this.tipocuenta = this.tiposcuenta.get(this.indice);
        }
    }
    private void enviarMensaje(String accion) {
        try {
            Connection  connection = queueConnection.createConnection();
            Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer=session.createProducer(queue);
            MapMessage message=session.createMapMessage();
            message.setString("ventana","tipocuenta");
            message.setString("accion",accion);
            message.setString("codigo",String.valueOf(tipocuenta.getTdcCodigo()));
            message.setString("nombre",tipocuenta.getTdcNombre());
            producer.send(message);
            producer.close();
            session.close();
            connection.close();        
        }
        catch(Exception ex){            
            ex.printStackTrace();
        }
    } 
    public Tipocuenta getTipocuenta() {
        return tipocuenta;
    }
    public List<Tipocuenta> getTiposcuenta() {
        return tiposcuenta;
    }

    public void setTiposcuenta(List<Tipocuenta> tiposcuenta) {
        this.tiposcuenta = tiposcuenta;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public Map<String, String> getCampos() {
        return campos;
    }

    public void setCampos(Map<String, String> campos) {
        this.campos = campos;
    }    
}
