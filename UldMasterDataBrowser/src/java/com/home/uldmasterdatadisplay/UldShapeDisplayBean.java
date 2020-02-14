package com.home.uldmasterdatadisplay;

import com.home.uldmasterdataservice.boundary.UldshapeVO;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * Uld Shape data display.
 */
@ManagedBean(name = "uldShapeDisplay")
@RequestScoped
public class UldShapeDisplayBean implements Serializable {
    /**
     * Needed for proper serializable implementation.
     */
    private static final long serialVersionUID = 1L;
    /**
     * A logger.
     */
    private static final Logger LOG = LogManager.getLogger(UldShapeDisplayBean.class.getName());
    /**
     * Shape selected in the application
     */
    private UldshapeVO selectedShape;

    public UldShapeDisplayBean() {
    }

    public List<UldshapeVO> getUldShapeList() {
        // Get that from the Session Scope init
        return UldShapeGridDisplayBean.uldShapeList;
    }

    public void setSelectedShape(UldshapeVO selectedShape) {
        this.selectedShape = selectedShape;
    }

    public UldshapeVO getSelectedShape() {
        return selectedShape;
    }

    public String getSelShape() {
        return this.selectedShape.getShape();
    }

    public String getSelRating() {
        return this.selectedShape.getRating();
    }

    public String getSelDescr() {
        return this.selectedShape.getDescr();
    }

    public String getSelMaxgrosswght() {
        return String.valueOf(this.selectedShape.getMaxgrosswght());
    }

    public String getSelTarewght() {
        return String.valueOf(this.selectedShape.getTarewght());
    }

    public String getSelInternalvolume() {
        return String.valueOf(this.selectedShape.getInternalvolume());
    }

    public String getSelIntleng() {
        return String.valueOf(this.selectedShape.getIntleng());
    }

    public String getSelIntwdth() {
        return String.valueOf(this.selectedShape.getIntwdth());
    }

    public String getSelInthght() {
        return String.valueOf(this.selectedShape.getInthght());
    }

    public String getSelAllleng() {
        return String.valueOf(this.selectedShape.getAllleng());
    }

    public String getSelAllwdth() {
        return String.valueOf(this.selectedShape.getAllwdth());
    }

    public String getSelAllhght() {
        return String.valueOf(this.selectedShape.getAllhght());
    }

    public String getSelUpdated() {
        // used a real converter now
        return this.selectedShape.getUpdated();
    }

    public String getSelUpdtuser() {
        return this.selectedShape.getUpdtuser();
    }

    /**
     * Get the thumbnail data as StreamedContent.
     *
     * @return the image as StreamedContent
     */
    public StreamedContent getThumbnail() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String shape = context.getExternalContext().getRequestParameterMap().get("shape");

            for (UldshapeVO item : UldShapeGridDisplayBean.uldShapeList) {
                if (item.getShape().equals(shape)) {
                    return new DefaultStreamedContent(new ByteArrayInputStream(item.getThumbnail()));
                }
            }
        }
        return new DefaultStreamedContent();
    }

    /**
     * Get the BigPic data as StreamedContent.
     *
     * @return the image as StreamedContent
     */
    public StreamedContent getBigpic() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String shape = context.getExternalContext().getRequestParameterMap().get("shape");

            for (UldshapeVO item : UldShapeGridDisplayBean.uldShapeList) {
                if (item.getShape().equals(shape)) {
                    return new DefaultStreamedContent(new ByteArrayInputStream(item.getBigpic()));
                }
            }
        }
        return new DefaultStreamedContent();
    }
}
