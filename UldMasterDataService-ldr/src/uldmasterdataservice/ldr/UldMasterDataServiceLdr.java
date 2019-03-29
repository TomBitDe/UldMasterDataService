package uldmasterdataservice.ldr;

import java.io.File;
import java.util.logging.Logger;
import uldmasterdataservice.util.ArgsEvaluator;

/**
 *
 */
public class UldMasterDataServiceLdr {
    /**
     * Acivate logging for this class.
     */
    private static final Logger LOG = Logger.getLogger(UldMasterDataServiceLdr.class.getName());

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
