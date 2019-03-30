package com.home.uldmasterdataservice.manager;

import com.home.uldmasterdataservice.model.Uldshape;
import java.util.List;
import javax.ejb.Local;

/**
 * Uldshape interface for local access.
 */
@Local
public interface UldshapeManager {
    public List<Uldshape> getAll();

    public Uldshape getById(String id);

    public List<Uldshape> queryByShape(String shape);

    public List<Uldshape> queryByShapeDescrRating(String shape, String descr, String rating);

    public void create(Uldshape uldshape);

    public void delete(String id);
}
