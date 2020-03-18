package com.home.uldmasterdatadisplay;

import com.home.uldmasterdataservice.boundary.UldshapeVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Uld Shape grid data display.
 */
@ManagedBean(name = "uldShapeGridDisplay")
@ViewScoped
public class UldShapeGridDisplayBean implements Serializable {
    /**
     * Needed for proper serializable implementation.
     */
    private static final long serialVersionUID = 1L;
    /**
     * A logger.
     */
    private static final Logger LOG = LogManager.getLogger(UldShapeGridDisplayBean.class.getName());
    /**
     * A JaxRs client.
     */
    private final Client jaxRsClient;

    public static List<UldshapeVO> uldShapeList;

    public UldShapeGridDisplayBean() {
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
}
