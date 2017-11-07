import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.*;

public class DebugLog {
    static Logger logger;
    public Handler fileHandler;
    public DebugLog() throws IOException {
        logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.setLevel(Level.INFO);
        fileHandler = new FileHandler("log.txt");
        SimpleFormatter formatPlainText = new SimpleFormatter();
        fileHandler.setFormatter(formatPlainText);
        logger.addHandler(fileHandler);
    }
    private static Logger getLogger(){
        if(logger == null){
            try {
                new DebugLog();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logger;
    }
    public static void log(Level level, String msg){
        getLogger().log(level, msg);
    }
}
