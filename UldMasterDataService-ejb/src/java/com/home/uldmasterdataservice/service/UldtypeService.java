package com.home.uldmasterdataservice.service;

import com.home.uldmasterdataservice.boundary.UldtypeItemVO;
import com.home.uldmasterdataservice.boundary.UldtypeVO;
import java.util.List;
import javax.ejb.Remote;

/**
 * Remote uldtype services.
 */
@Remote
public interface UldtypeService {
    /**
     * Get the content of uldtype entity (all uldtypes in DB table).
     *
     * @return a list of all uldtypes
     */
    public List<UldtypeVO> getUldtypeContent();

    /**
     * Get the content of uldtype entity starting at row offset fetching count rows.
     *
     * @param offset the offset to start fetching
     * @param count  the number of rows to fetch
     *
     * @return a list of uldtypes
     */
    public List<UldtypeVO> getUldtypeContent(int offset, int count);

    /**
     * Get a List of uldtypes that are already assigned to a given shape.
     *
     * @param shape the shape to look after
     *
     * @return a list of uldtype items
     */
    public List<UldtypeItemVO> getUldtypesAssigned(String shape);

    /**
     * Get a List of uldtypes that are availavle for assignement to a given shape.
     *
     * @param shape the shape to look after
     *
     * @return a list of uldtype items
     */
    public List<UldtypeItemVO> getUldtypesAvailable(String shape);

    /**
     * Assign a shape to an uldtype.
     *
     * @param shape   the shape to assign to the uldtype
     * @param uldtype the uldtype to update
     *
     * @return the VO of the updated uldtype
     */
    public UldtypeVO assignShape(String shape, String uldtype);

    /**
     * Deassign a shape from an uldtype. This means assign shape "LDxx" to the uldtype.
     *
     * @param shape   the shape to deassign from the uldtype
     * @param uldtype the uldtype to update
     *
     * @return the VO of the updated uldtype
     */
    public UldtypeVO deassignShape(String shape, String uldtype);

    /**
     * Get a uldtype by its Primary Key uldtype.
     *
     * @param id the Primary Key (id) of the uldtype
     *
     * @return the matching uldtype
     */
    public UldtypeVO getByUldtype(final String id);

    /**
     * Check if a given uldtype exists by its id.
     *
     * @param id the uldtypes id
     *
     * @return true if the uldtype exists else false
     */
    boolean exists(final String id);

    /**
     * Get the number of uldtypes.
     *
     * @return the number of uldtypes
     */
    long countUldtypes();
}
