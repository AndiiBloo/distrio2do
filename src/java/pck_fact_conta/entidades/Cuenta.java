package pck_fact_conta.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "CUENTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuenta.findAll", query = "SELECT c FROM Cuenta c")
    , @NamedQuery(name = "Cuenta.findByCueCodigo", query = "SELECT c FROM Cuenta c WHERE c.cueCodigo = :cueCodigo")
    , @NamedQuery(name = "Cuenta.findByCueNombre", query = "SELECT c FROM Cuenta c WHERE c.cueNombre = :cueNombre")})
public class Cuenta implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cueCodigo")
    private Collection<Detallecomprobantecontabilidad> detallecomprobantecontabilidadCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CUE_CODIGO")
    private BigDecimal cueCodigo;
    @Size(max = 100)
    @Column(name = "CUE_NOMBRE")
    private String cueNombre;
    @JoinColumn(name = "TDC_CODIGO", referencedColumnName = "TDC_CODIGO")
    @ManyToOne(optional = false)
    private Tipocuenta tdcCodigo;

    public Cuenta() {
        this.cueCodigo = BigDecimal.ZERO;
        this.cueNombre = "";
        this.tdcCodigo = new Tipocuenta();
    }

    public Cuenta(BigDecimal cueCodigo) {
        this.cueCodigo = cueCodigo;
    }

    public BigDecimal getCueCodigo() {
        return cueCodigo;
    }

    public void setCueCodigo(BigDecimal cueCodigo) {
        this.cueCodigo = cueCodigo;
    }

    public String getCueNombre() {
        return cueNombre;
    }

    public void setCueNombre(String cueNombre) {
        this.cueNombre = cueNombre;
    }

    public Tipocuenta getTdcCodigo() {
        return tdcCodigo;
    }

    public void setTdcCodigo(Tipocuenta tdcCodigo) {
        this.tdcCodigo = tdcCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cueCodigo != null ? cueCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuenta)) {
            return false;
        }
        Cuenta other = (Cuenta) object;
        if ((this.cueCodigo == null && other.cueCodigo != null) || (this.cueCodigo != null && !this.cueCodigo.equals(other.cueCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pck_pdist_fact_conta.entidades.Cuenta[ cueCodigo=" + cueCodigo + " ]";
    }

    @XmlTransient
    public Collection<Detallecomprobantecontabilidad> getDetallecomprobantecontabilidadCollection() {
        return detallecomprobantecontabilidadCollection;
    }

    public void setDetallecomprobantecontabilidadCollection(Collection<Detallecomprobantecontabilidad> detallecomprobantecontabilidadCollection) {
        this.detallecomprobantecontabilidadCollection = detallecomprobantecontabilidadCollection;
    }
    
}
