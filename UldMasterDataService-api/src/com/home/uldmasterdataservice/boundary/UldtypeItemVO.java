package com.home.uldmasterdataservice.boundary;

import java.io.Serializable;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */
@XmlRootElement(name = "UldtypeItemVO")
public class UldtypeItemVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String uldtype;

    public UldtypeItemVO() {
    }

    public UldtypeItemVO(String uldtype) {
        this.uldtype = uldtype;
    }

    public String getUldtype() {
        return uldtype;
    }

    public void setUldtype(String uldtype) {
        this.uldtype = uldtype;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final UldtypeItemVO other = (UldtypeItemVO) obj;

        return Objects.equals(this.uldtype, other.uldtype);
    }

    @Override
    public String toString() {
        return "UldtypeItemVO{" + "uldtype=" + uldtype + '}';
    }
}
