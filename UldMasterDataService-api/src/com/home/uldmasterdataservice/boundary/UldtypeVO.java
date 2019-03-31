package com.home.uldmasterdataservice.boundary;

import java.io.Serializable;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */
@XmlRootElement(name = "UldtypeVO")
public class UldtypeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String uldtype;
    private String descr;
    private int theoleng;
    private int theohght;
    private int tarewght;
    private int nelleng;
    private int welleng;
    private int doorside;
    private String updated;
    private String updtuser;
    private UldshapeVO shape;

    public UldtypeVO() {
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

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUpdtuser() {
        return updtuser;
    }

    public void setUpdtuser(String updtuser) {
        this.updtuser = updtuser;
    }

    public UldshapeVO getShape() {
        return shape;
    }

    public void setShape(UldshapeVO shape) {
        this.shape = shape;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.uldtype);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UldtypeVO other = (UldtypeVO) obj;
        if (!Objects.equals(this.uldtype, other.uldtype)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UldtypeVO{" + "uldtype=" + uldtype + ", descr=" + descr + ", theoleng=" + theoleng + ", theohght=" + theohght + ", tarewght=" + tarewght + ", nelleng=" + nelleng + ", welleng=" + welleng + ", doorside=" + doorside + ", updated=" + updated + ", updtuser=" + updtuser + '}';
    }
}
