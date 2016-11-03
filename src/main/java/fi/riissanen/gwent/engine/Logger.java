package fi.riissanen.gwent.engine;

import java.util.EnumMap;

/**
 * Engine's own logger
 * @author Daniel
 */
public class Logger {
    public enum LogLevel {
        INFO, WARN, ERROR;
    }
    
    private final EnumMap<LogLevel, Integer> logLevels =
            new EnumMap<>(LogLevel.class);
    private LogLevel logLevelFilter = LogLevel.INFO;
    
    public Logger() {
        logLevels.put(LogLevel.INFO, 0);
        logLevels.put(LogLevel.WARN, 1);
        logLevels.put(LogLevel.ERROR, 2);
    }
    
    public void write(LogLevel logLevel, String message) {
        int level = logLevels.get(logLevel);
        int filter = logLevels.get(logLevelFilter);
        if (level < filter) return;
        System.out.printf("[%s] %s\n", logLevel.toString(), message);
    }
    
    public void setLogLevelFilter(LogLevel filter) {
        logLevelFilter = filter;
    }
}
