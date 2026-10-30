package ua.lab2.knives.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import ua.lab2.knives.KnifeException;
import ua.lab2.knives.TestData;

class XmlValidatorTest {

    private static void assertInvalid(String resource) {
        assertThrows(KnifeException.class,
                () -> XmlValidator.validate(TestData.resource(resource), TestData.XSD), resource);
    }

    @Test
    void sampleDocumentIsValid() {
        assertDoesNotThrow(() -> XmlValidator.validate(TestData.XML, TestData.XSD));
    }

    @Test
    void singleKnifeDocumentIsValid() {
        assertDoesNotThrow(() -> XmlValidator.validate(TestData.resource("valid-single.xml"), TestData.XSD));
    }

    @Test
    void unknownEnumerationValueIsInvalid() {
        assertInvalid("invalid-enum.xml");
    }

    @Test
    void lengthBelowMinimumIsInvalid() {
        assertInvalid("invalid-length.xml");
    }

    @Test
    void woodenHandleWithoutWoodTypeIsInvalid() {
        assertInvalid("wood-without-type.xml");
    }

    @Test
    void duplicateIdIsInvalid() {
        assertInvalid("duplicate-id.xml");
    }

    @Test
    void missingIdAttributeIsInvalid() {
        assertInvalid("missing-id.xml");
    }

    @Test
    void missingElementIsInvalid() {
        assertInvalid("missing-origin.xml");
    }

    @Test
    void malformedXmlIsInvalid() {
        assertInvalid("malformed.xml");
    }

    @Test
    void missingXmlFileIsReported() {
        assertThrows(KnifeException.class, () -> XmlValidator.validate(Path.of("nope.xml"), TestData.XSD));
    }

    @Test
    void missingSchemaIsReported() {
        assertThrows(KnifeException.class, () -> XmlValidator.validate(TestData.XML, Path.of("nope.xsd")));
    }
}
