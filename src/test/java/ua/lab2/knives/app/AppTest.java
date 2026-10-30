package ua.lab2.knives.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import ua.lab2.knives.TestData;

class AppTest {

    private static String[] args(Path xml, Path output) {
        return new String[] {xml.toString(), TestData.XSD.toString(), TestData.XSL.toString(), output.toString()};
    }

    private static Path tempOutput() throws IOException {
        return Files.createTempDirectory("knives-app").resolve("out.xml");
    }

    @Test
    void successfulRunWritesTransformedFile() throws IOException {
        Path output = tempOutput();
        assertEquals(0, App.run(args(TestData.XML, output)));
        assertTrue(Files.size(output) > 0);
    }

    @Test
    void defaultArgumentsAreUsedWhenNoneGiven() {
        assertEquals(0, App.run(new String[0]));
        assertTrue(Files.exists(Path.of("output/knives-by-type.xml")));
    }

    @Test
    void invalidDocumentFailsWithExitCodeOne() throws IOException {
        Path output = tempOutput();
        assertEquals(1, App.run(args(TestData.resource("invalid-enum.xml"), output)));
        assertTrue(Files.notExists(output));
    }

    @Test
    void missingFileFailsWithExitCodeOne() throws IOException {
        assertEquals(1, App.run(args(Path.of("missing.xml"), tempOutput())));
    }

    @Test
    void mainReturnsNormallyOnSuccess() throws IOException {
        App.main(args(TestData.XML, tempOutput()));
    }
}
