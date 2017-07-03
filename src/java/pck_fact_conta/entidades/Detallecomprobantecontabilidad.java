package pck_fact_conta.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "DETALLECOMPROBANTECONTABILIDAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Detallecomprobantecontabilidad.findAll", query = "SELECT d FROM Detallecomprobantecontabilidad d")
    , @NamedQuery(name = "Detallecomprobantecontabilidad.findByComNumero", query = "SELECT d FROM Detallecomprobantecontabilidad d WHERE d.comNumero = :comNumero")
    , @NamedQuery(name = "Detallecomprobantecontabilidad.findByDccCodigo", query = "SELECT d FROM Detallecomprobantecontabilidad d WHERE d.dccCodigo = :dccCodigo")
    , @NamedQuery(name = "Detallecomprobantecontabilidad.findByDccDebe", query = "SELECT d FROM Detallecomprobantecontabilidad d WHERE d.dccDebe = :dccDebe")
    , @NamedQuery(name = "Detallecomprobantecontabilidad.findByDccHaber", query = "SELECT d FROM Detallecomprobantecontabilidad d WHERE d.dccHaber = :dccHaber")
    , @NamedQuery(name = "Detallecomprobantecontabilidad.deleteComNumero", query = "DELETE FROM Detallecomprobantecontabilidad d WHERE d.comNumero = :comNumero")    })

public class Detallecomprobantecontabilidad implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DCC_CODIGO")
    private BigDecimal dccCodigo;
    @Column(name = "DCC_DEBE")
    private Double dccDebe;
    @Column(name = "DCC_HABER")
    private Double dccHaber;
    @JoinColumn(name = "COM_NUMERO", referencedColumnName = "COM_NUMERO")
    @ManyToOne(optional = false)
    private Comprobantecontabilidad comNumero;
    @JoinColumn(name = "CUE_CODIGO", referencedColumnName = "CUE_CODIGO")
    @ManyToOne(optional = false)
    private Cuenta cueCodigo;

    public Detallecomprobantecontabilidad() 
    {
        dccCodigo=BigDecimal.ZERO;
        dccHaber=0.0;
        dccDebe=0.0;
        comNumero = new Comprobantecontabilidad();
        cueCodigo = new Cuenta();
    }

    public Detallecomprobantecontabilidad(BigDecimal dccCodigo) {
        this.dccCodigo = dccCodigo;
    }

    public BigDecimal getDccCodigo() {
        return dccCodigo;
    }

    public void setDccCodigo(BigDecimal dccCodigo) {
        this.dccCodigo = dccCodigo;
    }

    public Double getDccDebe() {
        return dccDebe;
    }

    public void setDccDebe(Double dccDebe) {
        this.dccDebe = dccDebe;
    }

    public Double getDccHaber() {
        return dccHaber;
    }

    public void setDccHaber(Double dccHaber) {
        this.dccHaber = dccHaber;
    }

    public Comprobantecontabilidad getComNumero() {
        return comNumero;
    }

    public void setComNumero(Comprobantecontabilidad comNumero) {
        this.comNumero = comNumero;
    }

    public Cuenta getCueCodigo() {
        return cueCodigo;
    }

    public void setCueCodigo(Cuenta cueCodigo) {
        this.cueCodigo = cueCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dccCodigo != null ? dccCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Detallecomprobantecontabilidad)) {
            return false;
        }
        Detallecomprobantecontabilidad other = (Detallecomprobantecontabilidad) object;
        if ((this.dccCodigo == null && other.dccCodigo != null) || (this.dccCodigo != null && !this.dccCodigo.equals(other.dccCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pck_pdist_fact_conta.entidades.Detallecomprobantecontabilidad[ dccCodigo=" + dccCodigo + " ]";
    }
    
}
