package com.home.uldmasterdataservice.service;

import com.home.uldmasterdataservice.boundary.UldshapeVO;
import java.util.List;
import javax.ejb.Remote;

/**
 * Remote uldshape services.
 */
@Remote
public interface UldshapeService {
    /**
     * Get the content of uldshape entity (all uldshapes in DB table).
     *
     * @return a list of all uldshapes
     */
    public List<UldshapeVO> getUldshapeContent();

    /**
     * Get the content of uldshape entity starting at row offset fetching count rows.
     *
     * @param offset the offset to start fetching
     * @param count  the number of rows to fetch
     *
     * @return a list of uldshapes
     */
    public List<UldshapeVO> getUldshapeContent(int offset, int count);

    /**
     * Get a uldshape by its Primary Key shape.
     *
     * @param id the Primary Key (id) of the uldshape
     *
     * @return the matching uldshape
     */
    public UldshapeVO getByShape(final String id);

    /**
     * Check if a given uldshape exists by its id.
     *
     * @param id the uldshapes id
     *
     * @return true if the uldshape exists else false
     */
    boolean exists(final String id);

    /**
     * Get the number of uldshapes.
     *
     * @return the number of uldshapes
     */
    long countUldshapes();
}
