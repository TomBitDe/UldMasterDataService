package com.home.uldmasterdataservice.boundary;

import java.io.Serializable;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */
@XmlRootElement(name = "UldshapeVO")
public class UldshapeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String shape;
    private String descr;
    private String rating;
    private int maxgrosswght;
    private int tarewght;
    private int internalvolume;
    private int intleng;
    private int intwdth;
    private int inthght;
    private int allleng;
    private int allwdth;
    private int allhght;
    private String updated;
    private String updtuser;
    private byte[] thumbnail;
    private byte[] bigpic;

    public UldshapeVO() {
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getMaxgrosswght() {
        return maxgrosswght;
    }

    public void setMaxgrosswght(int maxgrosswght) {
        this.maxgrosswght = maxgrosswght;
    }

    public int getTarewght() {
        return tarewght;
    }

    public void setTarewght(int tarewght) {
        this.tarewght = tarewght;
    }

    public int getInternalvolume() {
        return internalvolume;
    }

    public void setInternalvolume(int internalvolume) {
        this.internalvolume = internalvolume;
    }

    public int getIntleng() {
        return intleng;
    }

    public void setIntleng(int intleng) {
        this.intleng = intleng;
    }

    public int getIntwdth() {
        return intwdth;
    }

    public void setIntwdth(int intwdth) {
        this.intwdth = intwdth;
    }

    public int getInthght() {
        return inthght;
    }

    public void setInthght(int inthght) {
        this.inthght = inthght;
    }

    public int getAllleng() {
        return allleng;
    }

    public void setAllleng(int allleng) {
        this.allleng = allleng;
    }

    public int getAllwdth() {
        return allwdth;
    }

    public void setAllwdth(int allwdth) {
        this.allwdth = allwdth;
    }

    public int getAllhght() {
        return allhght;
    }

    public void setAllhght(int allhght) {
        this.allhght = allhght;
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

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public byte[] getBigpic() {
        return bigpic;
    }

    public void setBigpic(byte[] bigpic) {
        this.bigpic = bigpic;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.shape);
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
        final UldshapeVO other = (UldshapeVO) obj;
        if (!Objects.equals(this.shape, other.shape)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UldshapeVO{" + "shape=" + shape + ", descr=" + descr + ", rating=" + rating + ", maxgrosswght=" + maxgrosswght + ", tarewght=" + tarewght + ", internalvolume=" + internalvolume + ", intleng=" + intleng + ", intwdth=" + intwdth + ", inthght=" + inthght + ", allleng=" + allleng + ", allwdth=" + allwdth + ", allhght=" + allhght + ", updated=" + updated + ", updtuser=" + updtuser + '}';
    }

}
