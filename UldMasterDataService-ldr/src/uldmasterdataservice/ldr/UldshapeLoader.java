package uldmasterdataservice.ldr;

import com.home.uldmasterdataservice.boundary.UldshapeVO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import uldmasterdataservice.util.DbParameters;
import uldmasterdataservice.util.DbUtil;

/**
 * Load ULD shape items from file into DB
 */
public class UldshapeLoader {
    /**
     * Activate logging
     */
    private static final Logger LOG = Logger.getLogger(UldshapeLoader.class.getName());
    /**
     * The database access and check parameters
     */
    private static DbParameters dp;
    /**
     * The database connection to use
     */
    private static Connection conn;

    private String schema;

    private File path = null;
    private File picPath = null;

    /**
     * Creates a new instance of UldshapeLoader
     *
     * @param path    the full file path to the file to load
     * @param picPath the file path to the place where pictures of ULD shapes are to find
     *
     * @throws java.lang.Exception in case of any exception
     */
    public UldshapeLoader(File path, File picPath) throws Exception {
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
        this.picPath = picPath;
    }

    /**
     * Load the ULD shape items from path
     */
    public void init() {
        BufferedReader in = null;
        String line;
        PreparedStatement pStmt;
        String insertStmt = "INSERT INTO " + schema + "ULDSHAPE "
                + "(SHAPE, ALLHGHT, ALLLENG, ALLWDTH, BIGPIC, DESCR, INTERNALVOLUME, INTHGHT, INTLENG, INTWDTH, "
                + "MAXGROSSWGHT, RATING, TAREWGHT, THUMBNAIL, UPDATED, UPDTUSER, VERSION) VALUES "
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int count = 0;

        LOG.log(Level.INFO, "Initialize basic Uldshapes in DB from file [{0}]", path);

        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));

            pStmt = conn.prepareStatement(insertStmt);

            while ((line = in.readLine()) != null) {
                LOG.finer(line);

                UldshapeVO uldshapeVO = parseUldshape(line);

                pStmt.setString(1, uldshapeVO.getShape());
                pStmt.setInt(2, uldshapeVO.getAllhght());
                pStmt.setInt(3, uldshapeVO.getAllleng());
                pStmt.setInt(4, uldshapeVO.getAllwdth());
                pStmt.setBytes(5, uldshapeVO.getBigpic());
                pStmt.setString(6, uldshapeVO.getDescr());
                pStmt.setInt(7, uldshapeVO.getInternalvolume());
                pStmt.setInt(8, uldshapeVO.getInthght());
                pStmt.setInt(9, uldshapeVO.getIntleng());
                pStmt.setInt(10, uldshapeVO.getIntwdth());
                pStmt.setInt(11, uldshapeVO.getMaxgrosswght());
                pStmt.setString(12, uldshapeVO.getRating());
                pStmt.setInt(13, uldshapeVO.getTarewght());
                pStmt.setBytes(14, uldshapeVO.getThumbnail());
                pStmt.setTimestamp(15, DbUtil.getCurrentTimeStamp());
                pStmt.setString(16, uldshapeVO.getUpdtuser());
                pStmt.setLong(17, 0);

                // execute insert SQL stetement
                pStmt.executeUpdate();

                ++count;
            }
            LOG.log(Level.INFO, "Finished: [{0}] ULDSHAPES loaded", count);

            DbUtil.cleanupJdbc();
        }
        catch (IOException ioex) {
            LOG.log(Level.SEVERE, "An IO error occured. The error message is: {0}", ioex.getMessage());
        }
        catch (Exception ex) {
            LOG.log(Level.SEVERE, "A GENERAL error occured. The error message is: {0}", ex.getMessage());
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
        return this.path;
    }

    public void setPath(File path) {
        this.path = path;
    }

    public File getPicPath() {
        return this.picPath;
    }

    public void setPicPath(File path) {
        this.picPath = path;
    }

    /**
     * Returns the content of the file in a byte array
     *
     * @param file the file to load as a byte array
     *
     * @return the byte array
     */
    public static byte[] getBytesFromFile(File file) {
        byte[] bytes;
        try (InputStream is = new FileInputStream(file)) {
            long length = file.length();
            if (length > Integer.MAX_VALUE) {
                LOG.log(Level.SEVERE, "File {0} is too large", file.getName());
                bytes = new byte[0];
                return bytes;
            }
            bytes = new byte[(int) length];
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length
                    && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
            if (offset < bytes.length) {
                LOG.log(Level.SEVERE, "Could not completely read file {0}", file.getName());
                bytes = new byte[0];
                return bytes;
            }
        }
        catch (IOException ioex) {
            LOG.log(Level.SEVERE, "{0} {1}", new Object[]{ioex.getMessage(), file.getName()});
            bytes = new byte[0];
            return bytes;
        }

        return bytes;
    }

    /**
     * Parse the line read from file and make an Uldshape VO out of it
     *
     * @param line the line read from the file
     *
     * @return the Uldshape item as VO
     */
    private UldshapeVO parseUldshape(String line) {
        UldshapeVO uldshape = new UldshapeVO();

        uldshape.setShape(line.substring(0, 5).replaceAll("'", "''").trim());
        uldshape.setDescr(line.substring(6, 160).replaceAll("'", "''").trim());
        uldshape.setRating(line.substring(161, 170).replaceAll("'", "''").trim());
        uldshape.setMaxgrosswght(Integer.parseInt(line.substring(171, 176).trim()));
        uldshape.setTarewght(Integer.parseInt(line.substring(177, 181).trim()));
        uldshape.setInternalvolume(Integer.parseInt(line.substring(182, 184).trim()));
        uldshape.setIntleng(Integer.parseInt(line.substring(185, 187).trim()));
        uldshape.setIntwdth(Integer.parseInt(line.substring(188, 190).trim()));
        uldshape.setInthght(Integer.parseInt(line.substring(191, 193).trim()));
        uldshape.setAllleng(Integer.parseInt(line.substring(194, 196).trim()));
        uldshape.setAllwdth(Integer.parseInt(line.substring(197, 199).trim()));
        uldshape.setAllhght(Integer.parseInt(line.substring(200, 202).trim()));
        uldshape.setUpdated(line.substring(203, 217).replaceAll("'", "''").trim());
        uldshape.setUpdtuser(line.substring(218, 232).replaceAll("'", "''").trim());
        uldshape.setThumbnail(getBytesFromFile(new File(picPath.toString() + "/" + line.substring(233, 241).replaceAll("'", "''").trim() + "mini.gif")));
        uldshape.setBigpic(getBytesFromFile(new File(picPath.toString() + "/" + line.substring(233, 241).replaceAll("'", "''").trim() + ".gif")));

        LOG.log(Level.FINER, "ULDSHAPE=[{0}]", uldshape.toString());

        return uldshape;
    }
}
