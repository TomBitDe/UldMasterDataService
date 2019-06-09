package uldmasterdataloader.ldr;

import java.io.File;
import java.util.logging.Logger;
import uldmasterdataloader.util.ArgsEvaluator;

/**
 * The ULD master data loader main application.<br>
 * <br>
 * Currently both DB tables (ULDSHAPE, ULDTYPE) must be empty before execution. Delete content in this order:<br>
 * <br>
 * delete from uldtype;<br>
 * delete from uldshape;<br>
 * <br>
 * If no argument is given then all DB tables (ULDSHAPE, ULDTYPE) are initialized with file data.<br>
 * If argument ULDSHAPES is given then only DB table ULDSHAPE is initialized with file data.<br>
 * If argument ULDTYPES is given then only DB table ULDTYPE is initialized with file data.<br>
 * If both arguments are given then all DB tables (ULDSHAPE, ULDTYPE) are initialized with file data.<br>
 */
public class UldMasterDataLdr {
    /**
     * Acivate logging for this class.
     */
    private static final Logger LOG = Logger.getLogger(UldMasterDataLdr.class.getName());

    /**
     * Initialize ULD Shapes if requested.
     * <p>
     * @param args the arguments to check if initialization is requested
     */
    private static void initUldshapes(final String[] args) throws Exception {
        if (args.length == 0 || ArgsEvaluator.contains("ULDSHAPES", args)) {
            UldshapeLoader uldshapeLoader = new UldshapeLoader(new File("./db/load_shape.dat"), new File("./db/UldShapes"));
            uldshapeLoader.init();
        }
    }

    /**
     * Initialize ULD Types if requested.
     * <p>
     * @param args the arguments to check if initialization is requested
     */
    private static void initUldtypes(final String[] args) throws Exception {
        if (args.length == 0 || ArgsEvaluator.contains("ULDTYPES", args)) {
            UldtypeLoader uldtypeLoader = new UldtypeLoader(new File("./db/load_cart.dat"));
            uldtypeLoader.init();
        }
    }

    /**
     * Start DB initializing.
     * <p>
     * @param args the command line arguments to specify if initialization is requested
     *
     * @throws java.lang.Exception on any exception
     */
    public static void main(String[] args) throws Exception {
        LOG.info("Initialize DB...");
        initUldshapes(args);
        initUldtypes(args);
        LOG.info("Finished");
    }
}
