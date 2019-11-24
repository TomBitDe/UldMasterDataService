package com.home.uldmasterdatadisplay;

import com.home.uldmasterdataservice.boundary.UldshapeVO;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
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
     * A JaxRs client.
     */
    private final Client jaxRsClient;

    private List<UldshapeVO> uldShapeList;

    private UldshapeVO selectedShape;

    public UldShapeDisplayBean() {
        jaxRsClient = ClientBuilder.newClient();
        uldShapeList = new ArrayList<>();
    }

    public List<UldshapeVO> getUldShapeList() {
        return uldShapeList;
    }

    /**
     * Initialize with data and create the datagrid data after bean construction.
     */
    @PostConstruct
    public void init() {
        LOG.debug("Load uldshapes...");
        loadUldShapes();
        LOG.debug("Finished loading uldshapes...");
    }

    /**
     * JAX-RS client to load the uldshapes data.
     */
    private void loadUldShapes() {
        String uldShapesUri = "http://localhost:8080/UldMasterDataService-war/rest/UldMasterDataService/uldshapes/0/100";
        try {
            // Try to load the uldshapes
            // getAllUldshapes (GET http://localhost:8080/UldMasterDataService-war/rest/UldMasterDataService/uldshapes/0/100)
            LOG.debug("RESTful call to [" + uldShapesUri + "]...");
            uldShapeList = jaxRsClient.target(uldShapesUri)
                    .request("application/xml").get(new GenericType<List<UldshapeVO>>() {
            });
        }
        catch (Exception ex) {
            LOG.error(uldShapesUri + " : " + ex.getMessage());
            // Clear the uldshapes list in case loading failed
            uldShapeList.clear();
        }
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

            for (UldshapeVO item : uldShapeList) {
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

            for (UldshapeVO item : uldShapeList) {
                if (item.getShape().equals(shape)) {
                    return new DefaultStreamedContent(new ByteArrayInputStream(item.getBigpic()));
                }
            }
        }
        return new DefaultStreamedContent();
    }
}
