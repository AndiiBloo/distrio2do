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

@Entity
@Table(name = "COMPROBANTECONTABILIDAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comprobantecontabilidad.findAll", query = "SELECT c FROM Comprobantecontabilidad c")
    , @NamedQuery(name = "Comprobantecontabilidad.findByComNumero", query = "SELECT c FROM Comprobantecontabilidad c WHERE c.comNumero = :comNumero")
    , @NamedQuery(name = "Comprobantecontabilidad.findByComFecha", query = "SELECT c FROM Comprobantecontabilidad c WHERE c.comFecha = :comFecha")
    , @NamedQuery(name = "Comprobantecontabilidad.findByComObservaciones", query = "SELECT c FROM Comprobantecontabilidad c WHERE c.comObservaciones = :comObservaciones")
    , @NamedQuery(name = "Comprobantecontabilidad.maxComNumero", query = "SELECT COALESCE(MAX(c.comNumero) , 0) as Maximo FROM Comprobantecontabilidad c")})
public class Comprobantecontabilidad implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comNumero")
    private Collection<Detallecomprobantecontabilidad> detallecomprobantecontabilidadCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "COM_NUMERO")
    private BigDecimal comNumero;
    @Size(max = 10)
    @Column(name = "COM_FECHA")
    private String comFecha;
    @Size(max = 200)
    @Column(name = "COM_OBSERVACIONES")
    private String comObservaciones;

    public Comprobantecontabilidad() {
    }

    public Comprobantecontabilidad(BigDecimal comNumero) {
        this.comNumero = comNumero;
    }

    public BigDecimal getComNumero() {
        return comNumero;
    }

    public void setComNumero(BigDecimal comNumero) {
        this.comNumero = comNumero;
    }

    public String getComFecha() {
        return comFecha;
    }

    public void setComFecha(String comFecha) {
        this.comFecha = comFecha;
    }

    public String getComObservaciones() {
        return comObservaciones;
    }

    public void setComObservaciones(String comObservaciones) {
        this.comObservaciones = comObservaciones;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (comNumero != null ? comNumero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comprobantecontabilidad)) {
            return false;
        }
        Comprobantecontabilidad other = (Comprobantecontabilidad) object;
        if ((this.comNumero == null && other.comNumero != null) || (this.comNumero != null && !this.comNumero.equals(other.comNumero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pck_pdist_fact_conta.entidades.Comprobantecontabilidad[ comNumero=" + comNumero + " ]";
    }

    @XmlTransient
    public Collection<Detallecomprobantecontabilidad> getDetallecomprobantecontabilidadCollection() {
        return detallecomprobantecontabilidadCollection;
    }

    public void setDetallecomprobantecontabilidadCollection(Collection<Detallecomprobantecontabilidad> detallecomprobantecontabilidadCollection) {
        this.detallecomprobantecontabilidadCollection = detallecomprobantecontabilidadCollection;
    }
    
}
