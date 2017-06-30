package pck_fact_conta.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
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
@Table(name = "CIUDAD_ENTREGA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CiudadEntrega.findAll", query = "SELECT c FROM CiudadEntrega c")
    , @NamedQuery(name = "CiudadEntrega.findByCiuCodigo", query = "SELECT c FROM CiudadEntrega c WHERE c.ciuCodigo = :ciuCodigo")
    , @NamedQuery(name = "CiudadEntrega.findByCiuNombre", query = "SELECT c FROM CiudadEntrega c WHERE c.ciuNombre = :ciuNombre")
    , @NamedQuery(name = "CiudadEntrega.orderCodigo", query = "SELECT c FROM CiudadEntrega c ORDER BY c.ciuCodigo")
    , @NamedQuery(name = "CiudadEntrega.orderNombre", query = "SELECT c FROM CiudadEntrega c ORDER BY c.ciuNombre")})
public class CiudadEntrega implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CIU_CODIGO")
    private BigDecimal ciuCodigo;
    @Basic(optional = false)
    @Column(name = "CIU_NOMBRE")
    private String ciuNombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ciuCodigo")
    private List<Factura> facturaList;

    public CiudadEntrega() {
    }

    public CiudadEntrega(BigDecimal ciuCodigo) {
        this.ciuCodigo = ciuCodigo;
    }

    public CiudadEntrega(BigDecimal ciuCodigo, String ciuNombre) {
        this.ciuCodigo = ciuCodigo;
        this.ciuNombre = ciuNombre;
    }

    public BigDecimal getCiuCodigo() {
        return ciuCodigo;
    }

    public void setCiuCodigo(BigDecimal ciuCodigo) {
        this.ciuCodigo = ciuCodigo;
    }

    public String getCiuNombre() {
        return ciuNombre;
    }

    public void setCiuNombre(String ciuNombre) {
        this.ciuNombre = ciuNombre;
    }

    @XmlTransient
    public List<Factura> getFacturaList() {
        return facturaList;
    }

    public void setFacturaList(List<Factura> facturaList) {
        this.facturaList = facturaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ciuCodigo != null ? ciuCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CiudadEntrega)) {
            return false;
        }
        CiudadEntrega other = (CiudadEntrega) object;
        if ((this.ciuCodigo == null && other.ciuCodigo != null) || (this.ciuCodigo != null && !this.ciuCodigo.equals(other.ciuCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pck_pdist_fact_conta.entidades.CiudadEntrega[ ciuCodigo=" + ciuCodigo + " ]";
    }
    
}
