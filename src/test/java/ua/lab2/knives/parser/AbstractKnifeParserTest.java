package ua.lab2.knives.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.lab2.knives.KnifeException;
import ua.lab2.knives.TestData;
import ua.lab2.knives.model.HandleMaterial;
import ua.lab2.knives.model.Knife;

/** Contract every parser implementation must satisfy; subclasses only supply the parser. */
abstract class AbstractKnifeParserTest {

    private KnifeParser parser;

    protected abstract KnifeParser createParser();

    @BeforeEach
    void setUp() {
        parser = createParser();
    }

    @Test
    void parsesAllKnives() throws KnifeException {
        List<Knife> knives = parser.parse(TestData.XML);
        assertEquals(TestData.KNIFE_COUNT, knives.size());
        assertEquals("k001", knives.get(0).id());
    }

    @Test
    void mapsAllFieldsOfAKnife() throws KnifeException {
        List<Knife> knives = parser.parse(TestData.XML);
        assertTrue(knives.contains(TestData.saber()));
    }

    @Test
    void mapsHandlesOfAllKinds() throws KnifeException {
        List<Knife> knives = parser.parse(TestData.XML);
        for (HandleMaterial material : HandleMaterial.values()) {
            assertTrue(knives.stream().anyMatch(k -> k.visual().handle().material() == material),
                    "no knife with handle " + material);
        }
    }

    @Test
    void parsesSingleKnifeDocument() throws KnifeException {
        assertEquals(1, parser.parse(TestData.resource("valid-single.xml")).size());
    }

    @Test
    void missingFileIsReported() {
        assertThrows(KnifeException.class, () -> parser.parse(Path.of("no-such-file.xml")));
    }

    @Test
    void malformedXmlIsReported() {
        assertThrows(KnifeException.class, () -> parser.parse(TestData.resource("malformed.xml")));
    }

    @Test
    void unknownEnumLiteralIsReported() {
        assertThrows(KnifeException.class, () -> parser.parse(TestData.resource("invalid-enum.xml")));
    }

    @Test
    void missingElementIsReported() {
        assertThrows(KnifeException.class, () -> parser.parse(TestData.resource("missing-origin.xml")));
    }

    @Test
    void missingIdIsReported() {
        assertThrows(KnifeException.class, () -> parser.parse(TestData.resource("missing-id.xml")));
    }

    @Test
    void invalidNumberIsReported() {
        assertThrows(KnifeException.class, () -> parser.parse(TestData.resource("bad-number.xml")));
    }

    @Test
    void invalidBooleanIsReported() {
        assertThrows(KnifeException.class, () -> parser.parse(TestData.resource("bad-boolean.xml")));
    }

    @Test
    void wrongUnitIsReported() {
        assertThrows(KnifeException.class, () -> parser.parse(TestData.resource("invalid-unit.xml")));
    }

    @Test
    void missingUnitIsReported() {
        assertThrows(KnifeException.class, () -> parser.parse(TestData.resource("missing-unit.xml")));
    }

    @Test
    void duplicateIdIsReported() {
        assertThrows(KnifeException.class, () -> parser.parse(TestData.resource("duplicate-id.xml")));
    }

    @Test
    void elementOrderDoesNotMatter() throws KnifeException {
        List<Knife> knives = parser.parse(TestData.resource("reordered.xml"));
        assertEquals(1, knives.size());
        Knife knife = knives.get(0);
        assertEquals("k002", knife.id());
        assertEquals("Italy", knife.origin());
        assertEquals(30, knife.visual().blade().lengthCm());
        assertEquals(35, knife.visual().blade().widthMm());
        assertTrue(knife.visual().fuller());
        assertTrue(knife.collectible());
    }

    @Test
    void woodenHandleWithoutWoodTypeIsReported() {
        assertThrows(KnifeException.class, () -> parser.parse(TestData.resource("wood-without-type.xml")));
    }
}
