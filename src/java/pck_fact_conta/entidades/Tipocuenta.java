/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck_fact_conta.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Marco Rodriguez
 */
@Entity
@Table(name = "TIPOCUENTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipocuenta.findAll", query = "SELECT t FROM Tipocuenta t")
    , @NamedQuery(name = "Tipocuenta.findByTdcCodigo", query = "SELECT t FROM Tipocuenta t WHERE t.tdcCodigo = :tdcCodigo")
    , @NamedQuery(name = "Tipocuenta.findByTdcNombre", query = "SELECT t FROM Tipocuenta t WHERE t.tdcNombre = :tdcNombre")})
public class Tipocuenta implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TDC_CODIGO")
    private BigDecimal tdcCodigo;
    @Size(max = 100)
    @Column(name = "TDC_NOMBRE")
    private String tdcNombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tdcCodigo")
    private Collection<Cuenta> cuentaCollection;

    public Tipocuenta() {
    }

    public Tipocuenta(BigDecimal tdcCodigo) {
        this.tdcCodigo = tdcCodigo;
    }

    public BigDecimal getTdcCodigo() {
        return tdcCodigo;
    }

    public void setTdcCodigo(BigDecimal tdcCodigo) {
        this.tdcCodigo = tdcCodigo;
    }

    public String getTdcNombre() {
        return tdcNombre;
    }

    public void setTdcNombre(String tdcNombre) {
        this.tdcNombre = tdcNombre;
    }

    @XmlTransient
    public Collection<Cuenta> getCuentaCollection() {
        return cuentaCollection;
    }

    public void setCuentaCollection(Collection<Cuenta> cuentaCollection) {
        this.cuentaCollection = cuentaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tdcCodigo != null ? tdcCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipocuenta)) {
            return false;
        }
        Tipocuenta other = (Tipocuenta) object;
        if ((this.tdcCodigo == null && other.tdcCodigo != null) || (this.tdcCodigo != null && !this.tdcCodigo.equals(other.tdcCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pck_pdist_fact_conta.entidades.Tipocuenta[ tdcCodigo=" + tdcCodigo + " ]";
    }
    
}
