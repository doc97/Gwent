package fi.riissanen.gwent.engine;

import java.util.EnumMap;

/**
 * {@code Engine}'s own logger.
 * @author Daniel
 */
public class Logger {
    public enum LogLevel {
        INFO, WARN, ERROR;
    }
    
    private final EnumMap<LogLevel, Integer> logLevels =
            new EnumMap<>(LogLevel.class);
    private LogLevel logLevelFilter = LogLevel.INFO;
    
    /**
     * Initialized log levels.
     */
    public Logger() {
        logLevels.put(LogLevel.INFO, 0);
        logLevels.put(LogLevel.WARN, 1);
        logLevels.put(LogLevel.ERROR, 2);
    }
    
    /**
     * Writes a log message on a certain log level.
     * 
     * <p>
     * The logger filters output by log level
     * @param logLevel The log level to write on
     * @param message The log message
     */
    public void write(LogLevel logLevel, String message) {
        int level = logLevels.get(logLevel);
        int filter = logLevels.get(logLevelFilter);
        if (level < filter) {
            return;
        }
        System.out.printf("[%s] %s\n", logLevel.toString(), message);
    }
    
    /**
     * Sets the log level filter.
     * @param filter The filter
     */
    public void setLogLevelFilter(LogLevel filter) {
        logLevelFilter = filter;
    }
}
