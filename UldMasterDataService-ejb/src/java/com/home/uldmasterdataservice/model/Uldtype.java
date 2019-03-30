package com.home.uldmasterdataservice.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * The type of a ULD for example AKE, PMC...
 */
@Entity
@Table(name = "ULDTYPE")
@NamedQueries({
    @NamedQuery(name = "Uldtype.findAll", query = "SELECT u FROM Uldtype u")})
public class Uldtype implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "ULDTYPE")
    private String uldtype;

    @Basic(optional = false)
    @Column(name = "DESCR")
    private String descr;

    @Basic(optional = false)
    @Column(name = "THEOLENG")
    private int theoleng;

    @Basic(optional = false)
    @Column(name = "THEOHGHT")
    private int theohght;

    @Basic(optional = false)
    @Column(name = "TAREWGHT")
    private int tarewght;

    @Basic(optional = false)
    @Column(name = "NELLENG")
    private int nelleng;

    @Basic(optional = false)
    @Column(name = "WELLENG")
    private int welleng;

    @Basic(optional = false)
    @Column(name = "DOORSIDE")
    private int doorside;
    /**
     * Last update timestamp
     */
    @Column(name = "UPDATED", nullable = false)
    private Timestamp updated;
    /**
     * Update user
     */
    @Column(name = "UPDTUSER", nullable = false)
    private String updtuser;
    /**
     * Version for Optimistic Locking
     */
    @Version
    @Basic(optional = false)
    private int version;

    @JoinColumn(name = "SHAPE", referencedColumnName = "SHAPE")
    @ManyToOne(optional = false)
    private Uldshape shape;

    public Uldtype() {
    }

    public Uldtype(String uldtype) {
        this.uldtype = uldtype;
    }

    public Uldtype(String uldtype, String descr, int theoleng, int theohght, int tarewght, int nelleng, int welleng, int doorside, Timestamp updated, String updtuser, Uldshape shape) {
        this.uldtype = uldtype;
        this.descr = descr;
        this.theoleng = theoleng;
        this.theohght = theohght;
        this.tarewght = tarewght;
        this.nelleng = nelleng;
        this.welleng = welleng;
        this.doorside = doorside;
        this.updated = updated;
        this.updtuser = updtuser;
        this.shape = shape;
    }

    public String getUldtype() {
        return uldtype;
    }

    public void setUldtype(String uldtype) {
        this.uldtype = uldtype;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public int getTheoleng() {
        return theoleng;
    }

    public void setTheoleng(int theoleng) {
        this.theoleng = theoleng;
    }

    public int getTheohght() {
        return theohght;
    }

    public void setTheohght(int theohght) {
        this.theohght = theohght;
    }

    public int getTarewght() {
        return tarewght;
    }

    public void setTarewght(int tarewght) {
        this.tarewght = tarewght;
    }

    public int getNelleng() {
        return nelleng;
    }

    public void setNelleng(int nelleng) {
        this.nelleng = nelleng;
    }

    public int getWelleng() {
        return welleng;
    }

    public void setWelleng(int welleng) {
        this.welleng = welleng;
    }

    public int getDoorside() {
        return doorside;
    }

    public void setDoorside(int doorside) {
        this.doorside = doorside;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdtdttm(Timestamp updated) {
        this.updated = updated;
    }

    public String getUpdtuser() {
        return updtuser;
    }

    public void setUpdtuser(String updtuser) {
        this.updtuser = updtuser;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Uldshape getShape() {
        return shape;
    }

    public void setShape(Uldshape shape) {
        this.shape = shape;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.uldtype);
        hash = 79 * hash + this.version;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Uldtype other = (Uldtype) obj;
        if (!Objects.equals(this.uldtype, other.uldtype)) {
            return false;
        }
        return this.version == other.version;
    }

    @Override
    public String toString() {
        return "Uldtype{" + "uldtype=" + uldtype + ", descr=" + descr + ", shape=" + shape.getShape() + '}';
    }
}
