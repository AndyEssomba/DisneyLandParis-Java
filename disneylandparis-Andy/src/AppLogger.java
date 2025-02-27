package src;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class AppLogger { // Renamed to avoid conflicts
    // Singleton instance
    private static volatile AppLogger instance; // Volatile ensures proper synchronization

    private LogLevel currentLogLevel = LogLevel.INFO; // Default log level
    private String logFilePath = "application.log";  // Default log file path

    // Private constructor to prevent instantiation
    private AppLogger() {}

    // Thread-safe Singleton implementation
    public static AppLogger getInstance() {
        if (instance == null) {
            synchronized (AppLogger.class) {
                if (instance == null) {
                    instance = new AppLogger();
                }
            }
        }
        return instance;
    }

    // Set the log level
    public void setLogLevel(LogLevel level) {
        this.currentLogLevel = level;
    }

    // Log an INFO-level message
    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    // Log a WARN-level message
    public void warn(String message) {
        log(LogLevel.WARN, message);
    }

    // Log an ERROR-level message
    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    // General logging method with level control
    private void log(LogLevel level, String message) {
        if (level.ordinal() >= currentLogLevel.ordinal()) {
            // Only log messages greater or equal to the current log level
            String timeStampedMessage = String.format("%s [%s] %s",
                    LocalDateTime.now(), level, message);
            if (level == LogLevel.ERROR) {
                System.err.println(timeStampedMessage); // Use standard error for errors
            } else {
                System.out.println(timeStampedMessage);
            }
            logToFile(timeStampedMessage); // Optionally log to file
        }
    }

    // Write log messages to a file
    private void logToFile(String message) {
        try (FileWriter writer = new FileWriter(logFilePath, true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to write to the log file: " + e.getMessage());
        }
    }
}

// Enum for log levels
enum LogLevel {
    DEBUG,
    INFO,
    WARN,
    ERROR
}