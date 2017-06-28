package pck_fact_conta.entidades;

import java.io.Serializable;
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
@Table(name = "CLIENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c")
    , @NamedQuery(name = "Cliente.findByCliRuc", query = "SELECT c FROM Cliente c WHERE c.cliRuc = :cliRuc")
    , @NamedQuery(name = "Cliente.findByCliNombre", query = "SELECT c FROM Cliente c WHERE c.cliNombre = :cliNombre")
    , @NamedQuery(name = "Cliente.findByCliDireccion", query = "SELECT c FROM Cliente c WHERE c.cliDireccion = :cliDireccion")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "CLI_RUC")
    private String cliRuc;
    @Basic(optional = false)
    @Column(name = "CLI_NOMBRE")
    private String cliNombre;
    @Basic(optional = false)
    @Column(name = "CLI_DIRECCION")
    private String cliDireccion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliRuc")
    private List<Factura> facturaList;

    public Cliente() {
    }

    public Cliente(String cliRuc) {
        this.cliRuc = cliRuc;
    }

    public Cliente(String cliRuc, String cliNombre, String cliDireccion) {
        this.cliRuc = cliRuc;
        this.cliNombre = cliNombre;
        this.cliDireccion = cliDireccion;
    }

    public String getCliRuc() {
        return cliRuc;
    }

    public void setCliRuc(String cliRuc) {
        this.cliRuc = cliRuc;
    }

    public String getCliNombre() {
        return cliNombre;
    }

    public void setCliNombre(String cliNombre) {
        this.cliNombre = cliNombre;
    }

    public String getCliDireccion() {
        return cliDireccion;
    }

    public void setCliDireccion(String cliDireccion) {
        this.cliDireccion = cliDireccion;
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
        hash += (cliRuc != null ? cliRuc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.cliRuc == null && other.cliRuc != null) || (this.cliRuc != null && !this.cliRuc.equals(other.cliRuc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cliente{" + "cliRuc=" + cliRuc + ", cliNombre=" + cliNombre + ", cliDireccion=" + cliDireccion + ", facturaList=" + facturaList + '}';
    }
}
