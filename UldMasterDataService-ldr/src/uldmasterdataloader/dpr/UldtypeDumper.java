package uldmasterdataloader.dpr;

import com.home.uldmasterdataservice.boundary.UldshapeVO;
import com.home.uldmasterdataservice.boundary.UldtypeVO;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import uldmasterdataloader.util.ArgsEvaluator;
import uldmasterdataloader.util.DbParameters;
import uldmasterdataloader.util.DbUtil;
import uldmasterdataloader.util.DttmMakeHelper;

/**
 * Dump ULD type items from DB to file.
 */
public class UldtypeDumper {
    /**
     * Acivate logging for this class.
     */
    private static final Logger LOG = Logger.getLogger(UldtypeDumper.class.getName());

    /**
     * The database access and check parameters.
     */
    private static DbParameters dp;
    /**
     * The database connection to use.
     */
    private static Connection conn;
    /**
     * The database schema to work on.
     */
    private final String schema;

    private File path;

    /**
     * Creates a new instance of UldtypeDumper.
     *
     * @param path the full file path to the file to dump to
     *
     * @throws java.lang.Exception in case of any exception
     */
    public UldtypeDumper(File path) throws Exception {
        dp = new DbParameters();

        if (dp.getDbSchema().trim().isEmpty()) {
            schema = "";
        }
        else {
            schema = dp.getDbSchema().trim() + ".";
        }

        DbUtil.loadDbDriver(dp.getDriver());
        conn = DbUtil.connectToDb(dp);
        this.path = path;
    }

    /**
     * Load the ULD type items from DB and dump to file.
     */
    public void init() {
        PreparedStatement sStmt;
        String selectStmt = "SELECT * FROM " + schema + "ULDTYPE";
        PrintWriter out = null;
        String line;
        int count = 0;

        LOG.log(Level.INFO, "Dump basic Uldtypes from DB to file [{0}]", path);

        try {
            out = new PrintWriter(path);
            sStmt = conn.prepareStatement(selectStmt);
            ResultSet rSet = sStmt.executeQuery();
            UldtypeVO uldtypeVO = new UldtypeVO();
            UldshapeVO uldshapeVO = new UldshapeVO();

            while (rSet.next()) {
                uldtypeVO.setUldtype(rSet.getString("ULDTYPE"));
                uldtypeVO.setDescr(rSet.getString("DESCR"));
                uldtypeVO.setDoorside(rSet.getInt("DOORSIDE"));
                uldtypeVO.setNelleng(rSet.getInt("NELLENG"));
                uldtypeVO.setTarewght(rSet.getInt("TAREWGHT"));
                uldtypeVO.setTheohght(rSet.getInt("THEOHGHT"));
                uldtypeVO.setTheoleng(rSet.getInt("THEOLENG"));
                uldtypeVO.setWelleng(rSet.getInt("WELLENG"));
                uldtypeVO.setUpdated(DttmMakeHelper.makeDttm(rSet.getTimestamp("UPDATED")));
                uldtypeVO.setUpdtuser(rSet.getString("UPDTUSER"));

                uldshapeVO.setShape(rSet.getString("SHAPE"));
                uldtypeVO.setShape(uldshapeVO);

                line = String.format("%-3s|%-30s|%-4s|%2s|%2s|%2s|%2s|%2s|%2s|%-14s|%-14s|%-14s|%-14s",
                                     uldtypeVO.getUldtype(),
                                     uldtypeVO.getDescr(),
                                     uldtypeVO.getShape().getShape(),
                                     uldtypeVO.getTheoleng(),
                                     uldtypeVO.getTheohght(),
                                     uldtypeVO.getTarewght(),
                                     uldtypeVO.getNelleng(),
                                     uldtypeVO.getWelleng(),
                                     uldtypeVO.getDoorside(),
                                     uldtypeVO.getUpdated(),
                                     uldtypeVO.getUpdtuser(),
                                     uldtypeVO.getUpdated(),
                                     uldtypeVO.getUpdtuser());

                out.println(line);

                ++count;
            }
            LOG.log(Level.INFO, "Finished: [{0}] ULDTYPES dumped", count);
        }
        catch (IOException ioex) {
            LOG.log(Level.SEVERE, "An IO error occured. The error message is: {0}", ioex.getMessage());
        }
        catch (SQLException ex) {
            LOG.log(Level.SEVERE, "An SQL error occured. The error message is: {0}", ex.getMessage());
        }
        finally {
            if (out != null) {
                try {
                    out.close();
                }
                catch (Exception e) {
                    LOG.log(Level.SEVERE, "Final file close failed. The error message is: {0}", e.getMessage());
                }
            }
        }
    }

    public File getPath() {
        return path;
    }

    public void setPath(File path) {
        this.path = path;
    }

    /**
     * Initialize ULD Types if requested.
     * <p>
     * @param args the arguments to check if initialization is requested
     */
    private static void dumpUldtypes(final String[] args) throws Exception {
        if (args.length == 0 || ArgsEvaluator.contains("ULDTYPES", args)) {
            UldtypeDumper uldtypeDumper = new UldtypeDumper(new File("./db/load_cart.dat"));
            uldtypeDumper.init();
        }
    }

    /**
     * Start DB dumping.
     * <p>
     * @param args the command line arguments to specify if dumping is requested
     *
     * @throws java.lang.Exception on any exception
     */
    public static void main(String[] args) throws Exception {
        LOG.info("Initialize DB...");
        dumpUldtypes(args);
        LOG.info("Finished");
    }
}
