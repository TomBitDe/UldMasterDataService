package com.home.uldmasterdataservice.manager;

import com.home.uldmasterdataservice.model.Uldtype;
import java.util.List;
import javax.ejb.Local;

/**
 * Uldtype interface for local access.
 */
@Local
public interface UldtypeManager {
    public List<Uldtype> getAll();

    public Uldtype getById(String id);

    public List<Uldtype> queryByUldtype(String uldtype);

    public List<Uldtype> queryByUldtypeDescrShape(String uldtype, String descr, String shape);

    public void create(Uldtype uldtype);

    public void delete(String id);
}
