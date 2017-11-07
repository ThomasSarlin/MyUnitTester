/**
 * Class Respnsibility: Logging
 *@Author Thomas Sarlin - id15tsn, thomas.sarlin@gmail.com
 */

import java.io.IOException;
import java.util.logging.*;

public class DebugLog {
    static Logger logger;
    public Handler fileHandler;

    /**
     * Handles the initiation of the Logger, specifies output file aswell.
     * @throws IOException if unable to create file or write to file.
     */
    public DebugLog() throws IOException {
        logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.setLevel(Level.INFO);
        fileHandler = new FileHandler("log.txt");
        SimpleFormatter formatPlainText = new SimpleFormatter();
        fileHandler.setFormatter(formatPlainText);
        logger.addHandler(fileHandler);
    }

    private static Logger getLogger() throws IOException {
        if(logger == null)new DebugLog();
        return logger;
    }

    /**
     * Called for actual logging.
     * @param level - Level of "threat"
     * @param msg - Message to be logged.
     */
    public static void log(Level level, String msg) throws IOException {
        getLogger().log(level, msg);
    }
}
