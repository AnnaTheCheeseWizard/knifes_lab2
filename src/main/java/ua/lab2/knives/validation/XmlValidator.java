package ua.lab2.knives.validation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;
import ua.lab2.knives.KnifeException;

/** Validates an XML document against an XSD schema. */
public final class XmlValidator {

    private static final Logger LOG = Logger.getLogger(XmlValidator.class.getName());

    private XmlValidator() {
    }

    /**
     * @throws KnifeException if the schema cannot be read or the document is not valid
     */
    public static void validate(Path xmlFile, Path xsdFile) throws KnifeException {
        LOG.fine(() -> "Validating " + xmlFile + " against " + xsdFile);
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(xsdFile.toFile());
            schema.newValidator().validate(new StreamSource(xmlFile.toFile()));
            LOG.info(() -> xmlFile + " is valid against " + xsdFile);
        } catch (SAXException | IOException e) {
            throw new KnifeException("Validation of " + xmlFile + " failed: " + e.getMessage(), e);
        }
    }
}
