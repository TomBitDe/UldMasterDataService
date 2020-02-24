package com.home.uldmasterdataservice.manager;

import com.home.uldmasterdataservice.boundary.UldtypeItemVO;
import com.home.uldmasterdataservice.boundary.UldtypeVO;
import com.home.uldmasterdataservice.model.Uldshape;
import com.home.uldmasterdataservice.model.Uldtype;
import com.home.uldmasterdataservice.service.UldtypeService;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Uldtype bean implementation.
 */
@Stateless
public class UldtypeManagerBean implements UldtypeManager, UldtypeService {
    private static final Logger LOG = Logger.getLogger(UldtypeManagerBean.class.getName());

    @EJB
    private UldshapeManager uldshapeManager;

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

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<UldtypeVO> getUldtypeContent() {
        List<Uldtype> uldtypeList = getAll();

        List<UldtypeVO> uldtypeVOList = new ArrayList<>();
        for (Uldtype uldtype : uldtypeList) {
            UldtypeVO uldtypeVO = buildUldtypeVO(uldtype);
            uldtypeVOList.add(uldtypeVO);
            LOG.finer(uldtypeVO.toString());
        }

        return uldtypeVOList;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<UldtypeVO> getUldtypeContent(int offset, int count) {
        if (offset < 0) {
            throw new IllegalArgumentException("offset < 0");
        }
        if (count < 1) {
            throw new IllegalArgumentException("count < 1");
        }

        LOG.log(Level.FINER, "Offset=[{0}] count=[{1}]", new Object[]{offset, count});

        Query query = em.createQuery("select t FROM Uldtype t");
        query.setFirstResult(offset);
        query.setMaxResults(count);

        List<Uldtype> uldtypeList = query.getResultList();
        LOG.log(Level.FINER, "Return [{0}] uldtype(s)", uldtypeList.size());

        List<UldtypeVO> uldtypeVOList = new ArrayList<>();
        for (Uldtype uldtype : uldtypeList) {
            UldtypeVO uldtypeVO = buildUldtypeVO(uldtype);
            uldtypeVOList.add(uldtypeVO);
            LOG.finer(uldtypeVO.toString());
        }

        return uldtypeVOList;
    }

    @Override
    public UldtypeVO getByUldtype(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id is invalid");
        }

        Uldtype uldtype = getById(id);

        UldtypeVO uldtypeVO = buildUldtypeVO(uldtype);

        return uldtypeVO;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<UldtypeItemVO> getUldtypesAssigned(String shape) {
        List<UldtypeItemVO> uldtypeItemVOList = new ArrayList<>();

        Query query = em.createQuery("select u FROM Uldtype u where u.shape.shape = :shape");
        query.setParameter("shape", shape);

        List<Uldtype> uldtypeList = query.getResultList();
        LOG.log(Level.INFO, "Found Uldtypes: {0}", uldtypeList.size());

        for (Uldtype uldtype : uldtypeList) {
            UldtypeItemVO uldtypeItemVO = buildUldtypeItemVO(uldtype.getUldtype());
            uldtypeItemVOList.add(uldtypeItemVO);
            LOG.finer(uldtypeItemVO.toString());
        }

        return uldtypeItemVOList;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<UldtypeItemVO> getUldtypesAvailable(String shape) {
        List<UldtypeItemVO> uldtypeItemVOList = new ArrayList<>();

        Query query = em.createQuery("select u FROM Uldtype u where u.shape.shape <> :shape");
        query.setParameter("shape", shape);

        List<Uldtype> uldtypeList = query.getResultList();
        LOG.log(Level.INFO, "Found Uldtypes: {0}", uldtypeList.size());

        for (Uldtype uldtype : uldtypeList) {
            UldtypeItemVO uldtypeItemVO = buildUldtypeItemVO(uldtype.getUldtype());
            uldtypeItemVOList.add(uldtypeItemVO);
            LOG.finer(uldtypeItemVO.toString());
        }

        return uldtypeItemVOList;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public UldtypeVO assignShape(String shape, String uldtype) {
        Uldtype toUpdate = getById(uldtype);
        Uldshape toAssign = uldshapeManager.getById(shape);

        toUpdate.setShape(toAssign);

        return buildUldtypeVO(toUpdate);
    }

    @Override
    public boolean exists(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id is invalid");
        }

        LOG.log(Level.FINER, "Search for [{0}]", id);

        if (em.find(Uldtype.class, id) != null) {
            LOG.log(Level.FINER, "Return [true]");
            return true;
        }
        else {
            LOG.log(Level.FINER, "Return [false]");
            return false;
        }
    }

    @Override
    public long countUldtypes() {
        Query query;

        query = em.createQuery("SELECT count(t) FROM Uldtype t");
        long count = (long) query.getSingleResult();
        LOG.log(Level.FINER, "Return [{0}]", count);

        return count;
    }

    private UldtypeItemVO buildUldtypeItemVO(String uldtype) {
        UldtypeItemVO uldtypeItemVO = new UldtypeItemVO(uldtype);

        return uldtypeItemVO;
    }

    private UldtypeVO buildUldtypeVO(Uldtype uldtype) {
        UldtypeVO uldtypeVO = new UldtypeVO();

        uldtypeVO.setUldtype(uldtype.getUldtype());
        uldtypeVO.setDescr(uldtype.getDescr());
        uldtypeVO.setDoorside(uldtype.getDoorside());
        uldtypeVO.setNelleng(uldtype.getNelleng());
        uldtypeVO.setTarewght(uldtype.getTarewght());
        uldtypeVO.setTheohght(uldtype.getTheohght());
        uldtypeVO.setTheoleng(uldtype.getTheoleng());
        uldtypeVO.setWelleng(uldtype.getWelleng());
        uldtypeVO.setShape(UldshapeManagerBean.buildUldshapeVO(uldtype.getShape()));

        uldtypeVO.setUpdated(convert2String(uldtype.getUpdated(), "yyyyMMddHHmmssS"));
        uldtypeVO.setUpdtuser(uldtype.getUpdtuser());

        return uldtypeVO;
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
