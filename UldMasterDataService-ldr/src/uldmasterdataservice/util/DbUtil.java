package uldmasterdataservice.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Check if a given database can be accessed.
 */
public class DbUtil {
    /**
     * The logger for java logging (not log4j)
     */
    private static final Logger LOG = Logger.getLogger(DbUtil.class.getName());

    /**
     * The database access and check parameters
     */
    private static DbParameters dp;
    /**
     * The database connection to use
     */
    private static Connection conn;

    /**
     * Check if a given database can be accessed.<br>
     * Get the database access and check parameters from the property file. Load the configured database driver. Connect
     * to the database using the configured parameters. Access the configured database table one time. Do an application
     * exit if a general exception is raised.<br>
     *
     * @return false on any SQLException, else true
     */
    public static boolean isAccessible() {
        try {
            dp = new DbParameters();
            loadDbDriver(dp.getDriver());
            conn = connectToDb(dp);
            accessTable(conn);
        }
        catch (SQLException ex) {
            LOG.severe(ex.getLocalizedMessage());
            cleanupJdbc();

            return false;
        }
        catch (Exception ex) {
            LOG.severe(ex.getLocalizedMessage());

            System.exit(1);
        }

        return true;
    }

    /**
     * Load the given JDBC database driver.
     *
     * @param driver the JDBC database driver
     *
     * @throws Exception the driver cannot be loaded
     */
    public static void loadDbDriver(String driver) throws Exception {
        LOG.log(Level.FINE, "Versuche den Datenbank Treiber [{0}] zu laden...", driver);
        Class.forName(driver).newInstance();
        LOG.info("Datenbank Treiber geladen");
    }

    /**
     * Establish a database connection for further use.
     *
     * @param dp the database access parameters
     *
     * @return the database connection
     *
     * @throws SQLException on any SQL problem
     */
    public static Connection connectToDb(DbParameters dp) throws SQLException {
        Connection connection;

        LOG.log(Level.FINE, "Versuche die Datenbank [{0}] anzubinden...", dp.getConnectString());
        connection = DriverManager.getConnection(dp.getConnectString(),
                                                 dp.getUserName(),
                                                 dp.getPassword());
        LOG.info("Datenbank angebunden");

        return connection;
    }

    /**
     * Reload the database access parameters and try to connect to the database again.
     *
     * @return the database connection
     *
     * @throws SQLException on any SQL problem
     */
    public static Connection reConnectDb() throws SQLException {
        Connection connection;

        LOG.log(Level.FINE, "Versuche die Datenbank [{0}] erneut anzubinden...", dp.getConnectString());
        dp = new DbParameters();

        if (!conn.isClosed()) {
            conn.close();
        }

        connection = DriverManager.getConnection(dp.getConnectString(),
                                                 dp.getUserName(),
                                                 dp.getPassword());
        LOG.info("Datenbank angebunden");

        return connection;
    }

    /**
     * Access a configured database table using the given connection.<br>
     * It is just a "select 0 from <table>" to check the access. If this is possible the database can be accessed.
     *
     * @param conn the database connection
     *
     * @throws SQLException on any SQL problem
     */
    public static void accessTable(Connection conn) throws SQLException {
        LOG.log(Level.FINE, "Check Tabelle [{0}]...", dp.getDbSchema());

        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery("select 0 from " + dp.getDbSchema());

        LOG.log(Level.INFO, "Check [{0}] OK", dp.getDbSchema());
    }

    /**
     * Cleanup the database connection.<br>
     * Close the connection if any reference exists.
     */
    public static void cleanupJdbc() {
        LOG.fine("Cleanup JDBC...");

        if (conn != null) {
            try {
                conn.close();
            }
            catch (SQLException e) {
                conn = null;
            }
        }
        LOG.fine("JDBC cleanup finished");
    }

    public static Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }
}
