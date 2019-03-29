package com.home.uldmasterdataservice.manager;

import com.home.uldmasterdataservice.model.Uldtype;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 */
@Stateless
public class UldtypeManagerBean implements UldtypeManager {
    private static final Logger LOG = Logger.getLogger(UldtypeManagerBean.class.getName());

    @Resource
    SessionContext ctx;

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Uldtype> getAll() {
        List<Uldtype> resultList = em.createQuery("select u FROM Uldtype u").getResultList();
        LOG.log(Level.INFO, "Found Uldtypes: {0}", resultList.size());

        return resultList;
    }

    @Override
    public Uldtype getById(String id) {
        return em.find(Uldtype.class, id);
    }

    @Override
    public List<Uldtype> queryByUldtype(String uldtype) {
        Query query = em.createQuery("select u FROM Uldtype u where u.uldtype like :uldtype");
        query.setParameter("uldtype", uldtype);

        List<Uldtype> uldtypes = query.getResultList();
        LOG.log(Level.INFO, "Found Uldtypes: {0}", uldtypes.size());

        return uldtypes;
    }

    @Override
    public List<Uldtype> queryByUldtypeDescrShape(String uldtype, String descr, String shape) {
        Query query = em.createQuery("select u FROM Uldtype u where u.uldtype like :uldtype and u.descr like :descr and u.shape.shape like :shape");
        query.setParameter("uldtype", uldtype);
        query.setParameter("descr", descr);
        query.setParameter("shape", shape);

        List<Uldtype> uldtypes = query.getResultList();
        LOG.log(Level.INFO, "Found Uldtypes: {0}", uldtypes.size());

        return uldtypes;
    }

    @Override
    public void create(Uldtype uldtype) {
        em.persist(uldtype);
    }

    @Override
    public void delete(String id) {
        Uldtype uldtype = em.find(Uldtype.class, id);

        try {
            if (uldtype == null) {
                LOG.warning("Uldtype is null");
            }
            else {
                LOG.fine("Uldtype is not null");
            }
            em.remove(uldtype);
            LOG.log(Level.INFO, "Uldtype [{0}] deleted", id);
        }
        catch (Exception ex) {
            LOG.severe(ex.getMessage());
            throw new EJBException("Error while deleting Uldtype [" + id + ']');
        }
    }
}
