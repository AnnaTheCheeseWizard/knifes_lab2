package ua.lab2.knives.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ua.lab2.knives.model.BladeMaterial;
import ua.lab2.knives.model.HandType;

class XmlEnumsTest {

    @Test
    void convertsHyphenatedLiteral() {
        assertEquals(BladeMaterial.CAST_IRON, XmlEnums.parse(BladeMaterial.class, "cast-iron"));
        assertEquals(HandType.TWO_HANDED, XmlEnums.parse(HandType.class, " two-handed "));
    }

    @Test
    void unknownLiteralIsRejected() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> XmlEnums.parse(HandType.class, "three-handed"));
        assertTrue(e.getMessage().contains("three-handed"));
    }

    @Test
    void parsesBooleanLiterals() {
        assertTrue(XmlEnums.parseBoolean("true"));
        assertTrue(XmlEnums.parseBoolean(" 1 "));
        assertFalse(XmlEnums.parseBoolean("false"));
        assertFalse(XmlEnums.parseBoolean("0"));
    }

    @Test
    void invalidBooleanIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> XmlEnums.parseBoolean("yes"));
    }
}
