package ua.lab2.knives.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;

class LoggingConfigTest {

    @Test
    void configuresLoggersAndCreatesLogDirectory() {
        LoggingConfig.init();
        assertTrue(Files.isDirectory(Path.of("logs")));
        assertEquals(Level.FINE, Logger.getLogger("ua.lab2").getLevel());
        assertEquals(2, Logger.getLogger("").getHandlers().length);
    }
}
