package com.home.uldmasterdataservice;

import com.home.uldmasterdataservice.boundary.UldshapeVO;
import com.home.uldmasterdataservice.service.UldshapeService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * RESTful Uld Master Data Service.
 */
@Path("/UldMasterDataService")
@Stateless
public class UldMasterDataService {
    @EJB
    UldshapeService uldshapeService;

    /**
     * Get a list of all uld shapes in a range.
     *
     * @param offset the position to start fetching
     * @param count  the number of fetches to do
     *
     * @return the uld shape list based on offset and count
     */
    @PermitAll
    @GET
    @Path("/uldshapes/{offset}/{count}")
    @Produces({MediaType.APPLICATION_XML})
    public Response getAllUldshapes(@PathParam("offset") String offset, @PathParam("count") String count) {
        int intOffset = Integer.valueOf(offset);
        int intCount = Integer.valueOf(count);

        List<UldshapeVO> uldshapeList = uldshapeService.getUldshapeContent(intOffset, intCount);

        GenericEntity<List<UldshapeVO>> entity
                = new GenericEntity<List<UldshapeVO>>(new ArrayList(uldshapeList)) {
        };

        Response response = Response.ok(entity).build();

        return response;
    }

    /**
     * Get a uldshape by its id (shape).
     *
     * @param shape the shape of the uld
     *
     * @return the matching uld shape
     */
    @PermitAll
    @GET
    @Path("/uldshape/{shape}")
    @Produces({MediaType.APPLICATION_XML})
    public Response getUldshapeById(@PathParam("shape") String shape) {
        UldshapeVO uldshapeVO = uldshapeService.getByShape(shape);

        Response response = Response.ok().entity(uldshapeVO).build();

        return response;
    }

    /**
     * Check if a uld shape exists by its id (shape).
     *
     * @param shape the shape to check
     *
     * @return true if the uld shape exists otherwise false
     */
    @PermitAll
    @GET
    @Path("/uldshapeExists/{shape}")
    @Produces({MediaType.APPLICATION_XML})
    public Response uldShapeExists(@PathParam("shape") String shape) {
        UldshapeVO uldshapeVO = uldshapeService.getByShape(shape);

        if (uldshapeVO != null) {
            return Response.ok().entity("true").build();
        }
        return Response.ok().entity("false").build();

    }

    /**
     * Count the uld shapes.
     *
     * @return the number of uld shapes
     */
    @PermitAll
    @GET
    @Path("/countUldshapes")
    @Produces({MediaType.APPLICATION_XML})
    public Response countUldshapes() {
        long val = uldshapeService.countUldshapes();

        Response response;

        response = Response.ok().entity(String.valueOf(val)).build();

        return response;
    }

    /**
     * Give a list of all supported service operations.
     *
     * @return a list of service operations
     */
    @OPTIONS
    @Produces({MediaType.TEXT_PLAIN})
    public String getSupportedOperations() {
        return "OPTIONS GET";
    }
}
