/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck_fact_conta.backingbean;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import pck_fact_conta.entidades.Tipocuenta;

/**
 *
 * @author Marco Rodriguez
 */
@FacesConverter("tipoCuentaConverter")
public class tipoCuentaConverter implements Converter{
    
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            cuentaController controller = (cuentaController) fc.getExternalContext().getApplicationMap().get("cuentaController"); 
            return controller.getTiposcuenta().get(Integer.parseInt(value));
        } else {
            return null;
        }
    }
    
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null) {
            String value = ((Tipocuenta)o).getTdcCodigo().toString();
            return value;
        } else {
            return null;
        }
    }
    
}
