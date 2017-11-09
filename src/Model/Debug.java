package Model; /**
 * Class Responsibility: Logging
 *@Author Thomas Sarlin - id15tsn, thomas.sarlin@gmail.com
 */

import java.io.IOException;
import java.util.logging.*;

public class Debug {
    static Logger logger;
    public Handler fileHandler;

    /**
     * Handles the initiation of the Logger, specifies output file aswell.
     */
    public Debug() throws IOException {
        logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.setLevel(Level.INFO);
        fileHandler = new FileHandler("log.txt");
        SimpleFormatter formatPlainText = new SimpleFormatter();
        fileHandler.setFormatter(formatPlainText);
        logger.addHandler(fileHandler);
    }

    private static Logger getLogger(){
        if(logger == null) {
            try {
                new Debug();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logger;
    }

    /**
     * Called for actual logging.
     * @param level - Level of "threat"
     * @param msg - Message to be logged.
     */
    public static void log(Level level, String msg){
        getLogger().log(level, msg);
    }

    public static void shutDown(){
        Handler[] handlers =getLogger().getHandlers();
        for(Handler handler:handlers)
            handler.close();
    }
}
