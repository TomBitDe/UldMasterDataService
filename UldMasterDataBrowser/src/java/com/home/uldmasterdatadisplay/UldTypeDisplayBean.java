package com.home.uldmasterdatadisplay;

import com.home.uldmasterdataservice.boundary.UldshapeVO;
import com.home.uldmasterdataservice.boundary.UldtypeVO;
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
@ManagedBean(name = "uldTypeDisplay")
@RequestScoped
public class UldTypeDisplayBean implements Serializable {
    /**
     * Needed for proper serializable implementation.
     */
    private static final long serialVersionUID = 1L;
    /**
     * A logger.
     */
    private static final Logger LOG = LogManager.getLogger(UldTypeDisplayBean.class.getName());
    /**
     * Shape selected in the application
     */
    private UldtypeVO selectedType;

    public UldTypeDisplayBean() {
    }

    public List<UldtypeVO> getUldTypeList() {
        // Get that from the Session Scope init
        return UldTypeGridDisplayBean.uldTypeList;
    }

    public void setSelectedType(UldtypeVO selectedType) {
        this.selectedType = selectedType;
    }

    public UldtypeVO getSelectedType() {
        return selectedType;
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
}
