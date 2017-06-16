/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck_fact_conta.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Andrés López
 */
@Entity
@Table(name = "FACTURA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f")
    , @NamedQuery(name = "Factura.findByFacNumero", query = "SELECT f FROM Factura f WHERE f.facNumero = :facNumero")
    , @NamedQuery(name = "Factura.findByFacFecha", query = "SELECT f FROM Factura f WHERE f.facFecha = :facFecha")
    , @NamedQuery(name = "Factura.maxFacNumero", query = "SELECT COALESCE(MAX(f.facNumero) , 0) FROM Factura f")})
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "FAC_NUMERO")
    private BigDecimal facNumero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FAC_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date facFecha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facNumero")
    private List<Articulos> articulosList;
    @JoinColumn(name = "CIU_CODIGO", referencedColumnName = "CIU_CODIGO")
    @ManyToOne(optional = false)
    private CiudadEntrega ciuCodigo;
    @JoinColumn(name = "CLI_RUC", referencedColumnName = "CLI_RUC")
    @ManyToOne(optional = false)
    private Cliente cliRuc;

    public Factura() {
    }

    public Factura(BigDecimal facNumero) {
        this.facNumero = facNumero;
    }

    public Factura(BigDecimal facNumero, Date facFecha) {
        this.facNumero = facNumero;
        this.facFecha = facFecha;
    }

    public BigDecimal getFacNumero() {
        return facNumero;
    }

    public void setFacNumero(BigDecimal facNumero) {
        this.facNumero = facNumero;
    }

    public Date getFacFecha() {
        return facFecha;
    }

    public void setFacFecha(Date facFecha) {
        this.facFecha = facFecha;
    }

    @XmlTransient
    public List<Articulos> getArticulosList() {
        return articulosList;
    }

    public void setArticulosList(List<Articulos> articulosList) {
        this.articulosList = articulosList;
    }

    public CiudadEntrega getCiuCodigo() {
        return ciuCodigo;
    }

    public void setCiuCodigo(CiudadEntrega ciuCodigo) {
        this.ciuCodigo = ciuCodigo;
    }

    public Cliente getCliRuc() {
        return cliRuc;
    }

    public void setCliRuc(Cliente cliRuc) {
        this.cliRuc = cliRuc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facNumero != null ? facNumero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.facNumero == null && other.facNumero != null) || (this.facNumero != null && !this.facNumero.equals(other.facNumero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Factura{" + "facNumero=" + facNumero + ", facFecha=" + facFecha + ", articulosList=" + articulosList + ", ciuCodigo=" + ciuCodigo + ", cliRuc=" + cliRuc + '}';
    }    
}
