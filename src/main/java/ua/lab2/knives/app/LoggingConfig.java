package ua.lab2.knives.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.LogManager;

/** Configures java.util.logging from {@code /logging.properties}. */
public final class LoggingConfig {

    private static final String CONFIG = "/logging.properties";

    private LoggingConfig() {
    }

    public static void init() {
        try (InputStream in = LoggingConfig.class.getResourceAsStream(CONFIG)) {
            Files.createDirectories(Path.of("logs"));
            LogManager.getLogManager().readConfiguration(in);
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot configure logging", e);
        }
    }
}
