package com.home.uldmasterdatadisplay;

import com.home.uldmasterdataservice.boundary.UldtypeItemVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.DualListModel;

/**
 * Assign ULD types to a shape.
 */
@ManagedBean(name = "uldTypesAssignShape")
@ViewScoped
public class UldTypesAssignShapeBean implements Serializable {
    /**
     * Needed for proper serializable implementation.
     */
    private static final long serialVersionUID = 1L;
    /**
     * A logger.
     */
    private static final Logger LOG = LogManager.getLogger(UldTypesAssignShapeBean.class.getName());
    /**
     * A JaxRs client.
     */
    private final Client jaxRsClient;

    private String selectedShape;

    private DualListModel<String> uldTypes;

    public UldTypesAssignShapeBean() {
        jaxRsClient = ClientBuilder.newClient();
    }

    @PostConstruct
    void init() {
        List<String> sourceUldTypes = new ArrayList<>();
        List<String> targetUldTypes = new ArrayList<>();

        uldTypes = new DualListModel<>(sourceUldTypes, targetUldTypes);
    }

    void load(String shape) {
        if (shape != null && !shape.equals("")) {
            String uldTypesSourceUri = "http://localhost:8080/UldMasterDataService-war/rest/UldMasterDataService/availableTypes/";
            String uldTypesTargetUri = "http://localhost:8080/UldMasterDataService-war/rest/UldMasterDataService/assignedTypes/";
            String calledUri = uldTypesSourceUri + shape;

            try {
                uldTypes.getSource().clear();
                uldTypes.getTarget().clear();

                // Try to load the source uldtypes
                LOG.debug("RESTful call to [" + calledUri + "]...");
                List<UldtypeItemVO> sourceItemList = jaxRsClient.target(calledUri)
                        .request("application/xml").get(new GenericType<List<UldtypeItemVO>>() {
                });

                for (UldtypeItemVO item : sourceItemList) {
                    uldTypes.getSource().add(item.getUldtype());
                }

                // Try to load the target uldtypes
                calledUri = uldTypesTargetUri + shape;
                LOG.debug("RESTful call to [" + calledUri + "]...");
                List<UldtypeItemVO> targetItemList = jaxRsClient.target(calledUri)
                        .request("application/xml").get(new GenericType<List<UldtypeItemVO>>() {
                });

                for (UldtypeItemVO item : targetItemList) {
                    uldTypes.getTarget().add(item.getUldtype());
                }
            }
            catch (Exception ex) {
                LOG.error(calledUri + " : " + ex.getMessage());
                // Clear the picklist in case loading failed
                uldTypes.getSource().clear();
                uldTypes.getTarget().clear();
            }
        }
    }

    public String getSelectedShape() {
        return selectedShape;
    }

    public void setSelectedShape(String selectedShape) {
        if (selectedShape != null && !selectedShape.isEmpty()) {
            this.selectedShape = selectedShape;
        }
    }

    public DualListModel<String> getUldTypes() {
        load(selectedShape);

        return uldTypes;
    }

    public void setUldTypes(DualListModel<String> uldTypes) {
        this.uldTypes = uldTypes;
    }

    public void applyChange() {
        if (selectedShape != null && !selectedShape.isEmpty()) {
            String serviceUri = "http://localhost:8080/UldMasterDataService-war/rest/UldMasterDataService";
            String assignShapePath;
            String deassignShapePath;
            MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
            Response response;

            try {
                for (String uldtype : uldTypes.getTarget()) {
                    formData.clear();
                    formData.add("shape", selectedShape.trim());
                    formData.add("uldtype", uldtype);
                    assignShapePath = "/assignShape/" + selectedShape.trim() + '/' + uldtype;
                    LOG.debug("RESTful call to [" + serviceUri + assignShapePath + "]...");

                    response = jaxRsClient.target(serviceUri).path(assignShapePath).request().put(Entity.form(formData));

                    LOG.debug("RESPONSE: " + response);
                }

                for (String uldtype : uldTypes.getSource()) {
                    formData.clear();
                    formData.add("shape", selectedShape.trim());
                    formData.add("uldtype", uldtype);
                    deassignShapePath = "/deassignShape/" + selectedShape.trim() + '/' + uldtype;
                    LOG.debug("RESTful call to [" + serviceUri + deassignShapePath + "]...");

                    response = jaxRsClient.target(serviceUri).path(deassignShapePath).request().put(Entity.form(formData));

                    LOG.debug("RESPONSE: " + response);
                }
            }
            catch (Exception ex) {
                LOG.error(serviceUri + " : " + ex.getMessage());
            }
        }
    }
}
