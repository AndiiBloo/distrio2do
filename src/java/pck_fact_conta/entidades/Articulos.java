package pck_fact_conta.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "ARTICULOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Articulos.findAll", query = "SELECT a FROM Articulos a")
    , @NamedQuery(name = "Articulos.findByArtCodigo", query = "SELECT a FROM Articulos a WHERE a.artCodigo = :artCodigo")
    , @NamedQuery(name = "Articulos.findByArtNombre", query = "SELECT a FROM Articulos a WHERE a.artNombre = :artNombre")
    , @NamedQuery(name = "Articulos.findByArtPrecio", query = "SELECT a FROM Articulos a WHERE a.artPrecio = :artPrecio")
    , @NamedQuery(name = "Articulos.findByArtCantidad", query = "SELECT a FROM Articulos a WHERE a.artCantidad = :artCantidad")
    , @NamedQuery(name = "Articulos.findByFacNumero", query= "SELECT a FROM Articulos a WHERE a.facNumero = :facNumero")
    , @NamedQuery(name = "Articulos.findCod", query="SELECT a.artCodigo FROM Articulos a WHERE a.facNumero = :facNum AND a.artNombre = :artNom")
    , @NamedQuery(name = "Articulos.totalSuma", query = "SELECT COALESCE(Sum(a.artPrecio*a.artCantidad), 0) as Total FROM Articulos a")})
public class Articulos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ART_CODIGO")
    private BigDecimal artCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ART_NOMBRE")
    private String artNombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ART_PRECIO")
    private double artPrecio;
    @Column(name = "ART_CANTIDAD")
    private BigInteger artCantidad;
    @JoinColumn(name = "FAC_NUMERO", referencedColumnName = "FAC_NUMERO")
    @ManyToOne(optional = false)
    private Factura facNumero;

    public Articulos() {
    }

    public Articulos(BigDecimal artCodigo) {
        this.artCodigo = artCodigo;
    }

    public Articulos(BigDecimal artCodigo, String artNombre, double artPrecio) {
        this.artCodigo = artCodigo;
        this.artNombre = artNombre;
        this.artPrecio = artPrecio;
    }

    public BigDecimal getArtCodigo() {
        return artCodigo;
    }

    public void setArtCodigo(BigDecimal artCodigo) {
        this.artCodigo = artCodigo;
    }

    public String getArtNombre() {
        return artNombre;
    }

    public void setArtNombre(String artNombre) {
        this.artNombre = artNombre;
    }

    public double getArtPrecio() {
        return artPrecio;
    }

    public void setArtPrecio(double artPrecio) {
        this.artPrecio = artPrecio;
    }

    public BigInteger getArtCantidad() {
        return artCantidad;
    }

    public void setArtCantidad(BigInteger artCantidad) {
        this.artCantidad = artCantidad;
    }

    public Factura getFacNumero() {
        return facNumero;
    }

    public void setFacNumero(Factura facNumero) {
        this.facNumero = facNumero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (artCodigo != null ? artCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Articulos)) {
            return false;
        }
        Articulos other = (Articulos) object;
        if ((this.artCodigo == null && other.artCodigo != null) || (this.artCodigo != null && !this.artCodigo.equals(other.artCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pck_pdist_fact_conta.entidades.Articulos[ artCodigo=" + artCodigo + " ]";
    }
    
}
