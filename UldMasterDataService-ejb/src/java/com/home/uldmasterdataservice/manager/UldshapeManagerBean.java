package com.home.uldmasterdataservice.manager;

import com.home.uldmasterdataservice.boundary.UldshapeVO;
import com.home.uldmasterdataservice.model.Uldshape;
import com.home.uldmasterdataservice.service.UldshapeService;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Uldshape bean implementation.
 */
@Stateless
public class UldshapeManagerBean implements UldshapeManager, UldshapeService {
    private static final Logger LOG = Logger.getLogger(UldshapeManagerBean.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Uldshape> getAll() {
        List resultList = em.createQuery("select u FROM Uldshape u").getResultList();
        LOG.log(Level.FINE, "Number of found Uldshapes: {0}", resultList.size());

        return resultList;
    }

    @Override
    public Uldshape getById(String id) {
        return em.find(Uldshape.class, id);
    }

    @Override
    public List<Uldshape> queryByShape(String shape) {
        Query query = em.createQuery("select u from Uldshape u where u.shape like :shape");
        query.setParameter("shape", shape);

        List<Uldshape> uldshapes = query.getResultList();
        return uldshapes;
    }

    @Override
    public List<Uldshape> queryByShapeDescrRating(String shape, String descr, String rating) {
        Query query = em.createQuery("select u from Uldshape u where u.shape like :shape and u.descr like :descr and u.rating like :rating");
        query.setParameter("shape", shape);
        query.setParameter("descr", descr);
        query.setParameter("rating", rating);

        List<Uldshape> uldshapes = query.getResultList();
        return uldshapes;
    }

    @Override
    public void create(Uldshape uldshape) {
        em.persist(uldshape);
    }

    @Override
    public void delete(String id) {
        Uldshape uldshape = em.find(Uldshape.class, id);

        try {
            if (uldshape == null) {
                LOG.warning("Uldshape is null");
            }
            else {
                LOG.fine("Uldshape is not null");
            }
            em.remove(uldshape);
            LOG.log(Level.FINE, "Uldshape [{0}] deleted", id);
        }
        catch (Exception ex) {
            LOG.severe(ex.getMessage());
            throw new EJBException("Error while deleting Uldshape [" + id + ']');
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<UldshapeVO> getUldshapeContent() {
        List<Uldshape> uldshapeList = getAll();

        List<UldshapeVO> uldshapeVOList = new ArrayList<>();
        for (Uldshape uldshape : uldshapeList) {
            UldshapeVO uldshapeVO = buildUldshapeVO(uldshape);
            uldshapeVOList.add(uldshapeVO);
            LOG.finer(uldshapeVO.toString());
        }

        return uldshapeVOList;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<UldshapeVO> getUldshapeContent(int offset, int count) {
        if (offset < 0) {
            throw new IllegalArgumentException("offset < 0");
        }
        if (count < 1) {
            throw new IllegalArgumentException("count < 1");
        }

        LOG.log(Level.FINER, "Offset=[{0}] count=[{1}]", new Object[]{offset, count});

        Query query = em.createQuery("select u FROM Uldshape u");
        query.setFirstResult(offset);
        query.setMaxResults(count);

        List<Uldshape> uldshapeList = query.getResultList();
        LOG.log(Level.FINER, "Return [{0}] uldshape(s)", uldshapeList.size());

        List<UldshapeVO> uldshapeVOList = new ArrayList<>();
        for (Uldshape uldshape : uldshapeList) {
            UldshapeVO uldshapeVO = buildUldshapeVO(uldshape);
            uldshapeVOList.add(uldshapeVO);
            LOG.finer(uldshapeVO.toString());
        }

        return uldshapeVOList;
    }

    @Override
    public UldshapeVO getByShape(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id is invalid");
        }

        Uldshape uldshape = getById(id);

        UldshapeVO uldshapeVO = buildUldshapeVO(uldshape);

        return uldshapeVO;
    }

    @Override
    public boolean exists(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id is invalid");
        }

        LOG.log(Level.FINER, "Search for [{0}]", id);

        if (em.find(Uldshape.class, id) != null) {
            LOG.log(Level.FINER, "Return [true]");
            return true;
        }
        else {
            LOG.log(Level.FINER, "Return [false]");
            return false;
        }
    }

    @Override
    public long countUldshapes() {
        Query query;

        query = em.createQuery("SELECT count(u) FROM Uldshape u");
        long count = (long) query.getSingleResult();
        LOG.log(Level.FINER, "Return [{0}]", count);

        return count;
    }

    /**
     * Build a uldshape VO from the entity.
     *
     * @param uldshape the shape entity
     *
     * @return the shape VO
     */
    public static UldshapeVO buildUldshapeVO(Uldshape uldshape) {
        UldshapeVO uldshapeVO = new UldshapeVO();

        uldshapeVO.setShape(uldshape.getShape());
        uldshapeVO.setAllhght(uldshape.getAllhght());
        uldshapeVO.setAllleng(uldshape.getAllleng());
        uldshapeVO.setAllwdth(uldshape.getAllwdth());
        uldshapeVO.setBigpic(uldshape.getBigpic());
        uldshapeVO.setDescr(uldshape.getDescr());
        uldshapeVO.setInternalvolume(uldshape.getInternalvolume());
        uldshapeVO.setInthght(uldshape.getInthght());
        uldshapeVO.setIntleng(uldshape.getIntleng());
        uldshapeVO.setIntwdth(uldshape.getIntwdth());
        uldshapeVO.setMaxgrosswght(uldshape.getMaxgrosswght());
        uldshapeVO.setRating(uldshape.getRating());
        uldshapeVO.setTarewght(uldshape.getTarewght());
        uldshapeVO.setThumbnail(uldshape.getThumbnail());

        uldshapeVO.setUpdated(convert2String(uldshape.getUpdated(), "yyyyMMddHHmmssS"));
        uldshapeVO.setUpdtuser(uldshape.getUpdtuser());

        return uldshapeVO;
    }

    /**
     * Convert a Timestamp to a String using the given format.
     *
     * @param ts  the timestamp
     * @param fmt the format to use for conversion
     *
     * @return the resulting string
     */
    private static String convert2String(Timestamp ts, String fmt) {
        Date date = new Date();
        date.setTime(ts.getTime());
        String formattedDate = new SimpleDateFormat(fmt).format(date);

        return formattedDate;
    }
}
