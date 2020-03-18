package com.home.uldmasterdatadisplay;

import com.home.uldmasterdataservice.boundary.UldtypeVO;
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
 * Uld Type grid data display.
 */
@ManagedBean(name = "uldTypeGridDisplay")
@ViewScoped
public class UldTypeGridDisplayBean implements Serializable {
    /**
     * Needed for proper serializable implementation.
     */
    private static final long serialVersionUID = 1L;
    /**
     * A logger.
     */
    private static final Logger LOG = LogManager.getLogger(UldTypeGridDisplayBean.class.getName());
    /**
     * A JaxRs client.
     */
    private final Client jaxRsClient;

    public static List<UldtypeVO> uldTypeList;

    public UldTypeGridDisplayBean() {
        jaxRsClient = ClientBuilder.newClient();
        uldTypeList = new ArrayList<>();
    }

    public List<UldtypeVO> getUldTypeList() {
        return uldTypeList;
    }

    /**
     * Initialize with data and create the datagrid data after bean construction.
     */
    @PostConstruct
    public void init() {
        LOG.debug("Load uldtypes...");
        loadUldTypes();
        LOG.debug("Finished loading uldtypes...");
    }

    /**
     * JAX-RS client to load the uldtypes data.
     */
    private void loadUldTypes() {
        String uldTypesUri = "http://localhost:8080/UldMasterDataService-war/rest/UldMasterDataService/uldtypes/0/300";
        try {
            // Try to load the uldtypes
            LOG.debug("RESTful call to [" + uldTypesUri + "]...");
            uldTypeList = jaxRsClient.target(uldTypesUri)
                    .request("application/xml").get(new GenericType<List<UldtypeVO>>() {
            });
        }
        catch (Exception ex) {
            LOG.error(uldTypesUri + " : " + ex.getMessage());
            // Clear the uldshapes list in case loading failed
            uldTypeList.clear();
        }
    }
}
