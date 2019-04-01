package uldmasterdataloader.ldr;

import com.home.uldmasterdataservice.boundary.UldtypeVO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import uldmasterdataloader.util.DbParameters;
import uldmasterdataloader.util.DbUtil;

/**
 * Load ULD type items from file into DB.
 */
public class UldtypeLoader {
    /**
     * Activate logging
     */
    private static final Logger LOG = Logger.getLogger(UldtypeLoader.class.getName());
    /**
     * The database access and check parameters
     */
    private static DbParameters dp;
    /**
     * The database connection to use
     */
    private static Connection conn;
    /**
     * The database schema to work on
     */
    private final String schema;

    private File path;

    /**
     * Creates a new instance of UldtypeLoader
     *
     * @param path the full file path to the file to load
     *
     * @throws java.lang.Exception in case of any exception
     */
    public UldtypeLoader(File path) throws Exception {
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
     * Load the ULD type items from path
     */
    public void init() {
        BufferedReader in = null;
        String line;
        PreparedStatement pStmt;
        String insertStmt = "INSERT INTO " + schema + "ULDTYPE "
                + "(ULDTYPE, DESCR, DOORSIDE, NELLENG, TAREWGHT, THEOHGHT, THEOLENG, "
                + "UPDATED, UPDTUSER, VERSION, WELLENG, SHAPE) VALUES "
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int count = 0;

        LOG.log(Level.INFO, "Initialize basic Uldtypes in DB from file [{0}]", path);

        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));

            pStmt = conn.prepareStatement(insertStmt);

            while ((line = in.readLine()) != null) {
                LOG.finer(line);

                // Use the UldtypeVO except the shape item
                UldtypeVO uldtypeVO = parseUldtype(line);

                pStmt.setString(1, uldtypeVO.getUldtype());
                pStmt.setString(2, uldtypeVO.getDescr());
                pStmt.setInt(3, uldtypeVO.getDoorside());
                pStmt.setInt(4, uldtypeVO.getNelleng());
                pStmt.setInt(5, uldtypeVO.getTarewght());
                pStmt.setInt(6, uldtypeVO.getTheohght());
                pStmt.setInt(7, uldtypeVO.getTheoleng());
                pStmt.setTimestamp(8, DbUtil.getCurrentTimeStamp());
                pStmt.setString(9, uldtypeVO.getUpdtuser());
                pStmt.setLong(10, 0);
                pStmt.setInt(11, uldtypeVO.getWelleng());

                // This is outside the UldtypeVO
                pStmt.setString(12, line.substring(35, 39).replaceAll("'", "''").trim());

                // execute insert SQL stetement
                pStmt.executeUpdate();

                ++count;
            }
            LOG.log(Level.INFO, "Finished: [{0}] ULDTYPES loaded", count);
        }
        catch (IOException ioex) {
            LOG.log(Level.SEVERE, "An IO error occured. The error message is: {0}", ioex.getMessage());
        }
        catch (SQLException ex) {
            LOG.log(Level.SEVERE, "An SQL error occured. The error message is: {0}", ex.getMessage());
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException e) {
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
     * Parse the line read from file and make an Uldtype VO out of it
     *
     * @param line the line read from the file
     *
     * @return the Uldtype item as VO
     */
    private UldtypeVO parseUldtype(String line) {
        UldtypeVO uldtype = new UldtypeVO();

        uldtype.setUldtype(line.substring(0, 3).replaceAll("'", "''").trim());
        uldtype.setDescr(line.substring(4, 34).replaceAll("'", "''").trim());
        uldtype.setTheoleng(Integer.parseInt(line.substring(40, 42).trim()));
        uldtype.setTheohght(Integer.parseInt(line.substring(43, 45).trim()));
        uldtype.setTarewght(Integer.parseInt(line.substring(46, 48).trim()));
        uldtype.setNelleng(Integer.parseInt(line.substring(49, 51).trim()));
        uldtype.setWelleng(Integer.parseInt(line.substring(52, 54).trim()));
        uldtype.setDoorside(Integer.parseInt(line.substring(55, 57).trim()));
        uldtype.setUpdated(line.substring(58, 72).replaceAll("'", "''").trim());
        uldtype.setUpdtuser(line.substring(73, 87).replaceAll("'", "''").trim());

        LOG.finer(uldtype.toString());

        return uldtype;
    }
}
