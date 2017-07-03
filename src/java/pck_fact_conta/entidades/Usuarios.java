package pck_fact_conta.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "USUARIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u")
    , @NamedQuery(name = "Usuarios.findByUsCodigo", query = "SELECT u FROM Usuarios u WHERE u.usCodigo = :usCodigo")
    , @NamedQuery(name = "Usuarios.findByUsNombre", query = "SELECT u FROM Usuarios u WHERE u.usNombre = :usNombre")
    , @NamedQuery(name = "Usuarios.findByUsPassword", query = "SELECT u FROM Usuarios u WHERE u.usPassword = :usPassword")
    , @NamedQuery(name = "Usuarios.findByUsRol", query = "SELECT u FROM Usuarios u WHERE u.usRol = :usRol")})
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "US_CODIGO")
    private BigDecimal usCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 20)
    @Column(name = "US_NOMBRE")
    private String usNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 20)
    @Column(name = "US_PASSWORD")
    private String usPassword;
    @Column(name = "US_ROL")
    private BigInteger usRol;

    public Usuarios() {
        this.usCodigo = BigDecimal.ZERO;
        this.usNombre = "";
        this.usPassword = "";
        this.usRol = BigInteger.ZERO;
    }

    public Usuarios(BigDecimal usCodigo) {
        this.usCodigo = usCodigo;
    }

    public Usuarios(BigDecimal usCodigo, String usNombre, String usPassword) {
        this.usCodigo = usCodigo;
        this.usNombre = usNombre;
        this.usPassword = usPassword;
    }

    public BigDecimal getUsCodigo() {
        return usCodigo;
    }

    public void setUsCodigo(BigDecimal usCodigo) {
        this.usCodigo = usCodigo;
    }

    public String getUsNombre() {
        return usNombre;
    }

    public void setUsNombre(String usNombre) {
        this.usNombre = usNombre;
    }

    public String getUsPassword() {
        return usPassword;
    }

    public void setUsPassword(String usPassword) {
        this.usPassword = usPassword;
    }

    public BigInteger getUsRol() {
        return usRol;
    }

    public void setUsRol(BigInteger usRol) {
        this.usRol = usRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usCodigo != null ? usCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.usCodigo == null && other.usCodigo != null) || (this.usCodigo != null && !this.usCodigo.equals(other.usCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pck_pdist_fact_conta.entidades.Usuarios[ usCodigo=" + usCodigo + " ]";
    }
    
}
