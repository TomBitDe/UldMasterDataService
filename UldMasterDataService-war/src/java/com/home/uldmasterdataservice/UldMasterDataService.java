package com.home.uldmasterdataservice;

import com.home.uldmasterdataservice.boundary.UldshapeVO;
import com.home.uldmasterdataservice.boundary.UldtypeItemVO;
import com.home.uldmasterdataservice.boundary.UldtypeVO;
import com.home.uldmasterdataservice.service.UldshapeService;
import com.home.uldmasterdataservice.service.UldtypeService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.PUT;
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
    @EJB
    UldtypeService uldtypeService;

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
     * Get a list of all uld types in a range.
     *
     * @param offset the position to start fetching
     * @param count  the number of fetches to do
     *
     * @return the uld type list based on offset and count
     */
    @PermitAll
    @GET
    @Path("/uldtypes/{offset}/{count}")
    @Produces({MediaType.APPLICATION_XML})
    public Response getAllUldtypes(@PathParam("offset") String offset, @PathParam("count") String count) {
        int intOffset = Integer.valueOf(offset);
        int intCount = Integer.valueOf(count);

        List<UldtypeVO> uldtypeList = uldtypeService.getUldtypeContent(intOffset, intCount);

        GenericEntity<List<UldtypeVO>> entity
                = new GenericEntity<List<UldtypeVO>>(new ArrayList(uldtypeList)) {
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
     * Get a uldstype by its id (type).
     *
     * @param type the type of the uld
     *
     * @return the matching uld type
     */
    @PermitAll
    @GET
    @Path("/uldtype/{type}")
    @Produces({MediaType.APPLICATION_XML})
    public Response getUldtypeById(@PathParam("type") String type) {
        UldtypeVO uldtypeVO = uldtypeService.getByUldtype(type);

        Response response = Response.ok().entity(uldtypeVO).build();

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
    public Response uldshapeExists(@PathParam("shape") String shape) {
        UldshapeVO uldshapeVO = uldshapeService.getByShape(shape);

        if (uldshapeVO != null) {
            return Response.ok().entity("true").build();
        }

        return Response.ok().entity("false").build();
    }

    /**
     * Check if a uld type exists by its id (type).
     *
     * @param type the type to check
     *
     * @return true if the uld type exists otherwise false
     */
    @PermitAll
    @GET
    @Path("/uldtypeExists/{type}")
    @Produces({MediaType.APPLICATION_XML})
    public Response uldtypeExists(@PathParam("type") String type) {
        UldtypeVO uldtypeVO = uldtypeService.getByUldtype(type);

        if (uldtypeVO != null) {
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
     * Count the uld types.
     *
     * @return the number of uld types
     */
    @PermitAll
    @GET
    @Path("/countUldtypes")
    @Produces({MediaType.APPLICATION_XML})
    public Response countUldtypes() {
        long val = uldtypeService.countUldtypes();

        Response response;

        response = Response.ok().entity(String.valueOf(val)).build();

        return response;
    }

    /**
     * Give a list of all uld types already assigned to a shape.
     *
     * @param shape the shape to look for
     *
     * @return the matching uld type list
     */
    @PermitAll
    @GET
    @Path("/assignedTypes/{shape}")
    @Produces({MediaType.APPLICATION_XML})
    public Response getAssignedTypes(@PathParam("shape") String shape) {
        List<UldtypeItemVO> uldtypeItemList = uldtypeService.getUldtypesAssigned(shape);

        GenericEntity<List<UldtypeItemVO>> entity
                = new GenericEntity<List<UldtypeItemVO>>(new ArrayList(uldtypeItemList)) {
        };

        Response response = Response.ok(entity).build();

        return response;
    }

    /**
     * Give a list of all uld types already assigned to a shape.
     *
     * @param shape the shape to look for
     *
     * @return the matching uld type list
     */
    @PermitAll
    @GET
    @Path("/availableTypes/{shape}")
    @Produces({MediaType.APPLICATION_XML})
    public Response getAvailableTypes(@PathParam("shape") String shape) {
        List<UldtypeItemVO> uldtypeItemList = uldtypeService.getUldtypesAvailable(shape);

        GenericEntity<List<UldtypeItemVO>> entity
                = new GenericEntity<List<UldtypeItemVO>>(new ArrayList(uldtypeItemList)) {
        };

        Response response = Response.ok(entity).build();

        return response;
    }

    /**
     * Assign a shape to an uldtype.
     *
     * @param shape   the shape to assign to the uldtype
     * @param uldtype the uldtype to update
     *
     * @return the UldtypeVO of the updated uldtype
     */
    @PermitAll
    @PUT
    @Path("/assignShape/{shape}/{uldtype}")
    @Produces({MediaType.APPLICATION_XML})
    public Response assignShape(@PathParam("shape") String shape, @PathParam("uldtype") String uldtype) {
        UldtypeVO uldtypeVO = uldtypeService.assignShape(shape, uldtype);

        Response response = Response.ok().entity(uldtypeVO).build();

        return response;
    }

    /**
     * Deassign a shape from an uldtype. Means to assign shape LDxx as replacement.
     *
     * @param shape   the shape to deassign from the uldtype
     * @param uldtype the uldtype to update
     *
     * @return the UldtypeVO of the updated uldtype
     */
    @PermitAll
    @PUT
    @Path("/deassignShape/{shape}/{uldtype}")
    @Produces({MediaType.APPLICATION_XML})
    public Response deassignShape(@PathParam("shape") String shape, @PathParam("uldtype") String uldtype) {
        UldtypeVO uldtypeVO = uldtypeService.deassignShape(shape, uldtype);

        Response response = Response.ok().entity(uldtypeVO).build();

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
