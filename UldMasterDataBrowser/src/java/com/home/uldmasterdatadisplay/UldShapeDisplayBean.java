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

    private List<UldshapeVO> uldShapes;

    public UldShapeDisplayBean() {
        jaxRsClient = ClientBuilder.newClient();
        uldShapes = new ArrayList<>();
    }

    public List<UldshapeVO> getUldShapes() {
        return uldShapes;
    }

    /**
     * Initialize with data and create the datagrid data after bean construction.
     */
    @PostConstruct
    public void init() {
        LOG.debug("Load uldshapes...");
        loadUldShapes();
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
            uldShapes = jaxRsClient.target(uldShapesUri)
                    .request("application/xml").get(new GenericType<List<UldshapeVO>>() {
            });
        }
        catch (Exception ex) {
            LOG.error(uldShapesUri + " : " + ex.getMessage());
            // Clear the uldshapes list in case loading failed
            uldShapes.clear();
        }
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

            for (UldshapeVO item : uldShapes) {
                if (item.getShape().equals(shape)) {
                    return new DefaultStreamedContent(new ByteArrayInputStream(item.getThumbnail()));
                }
            }
        }
        return new DefaultStreamedContent();
    }
}
