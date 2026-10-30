package ua.lab2.knives.transform;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import ua.lab2.knives.KnifeException;

/** Applies an XSL stylesheet to an XML file. */
public final class XslTransformer {

    private static final Logger LOG = Logger.getLogger(XslTransformer.class.getName());

    private XslTransformer() {
    }

    /**
     * Transforms {@code xmlFile} with {@code xslFile} and writes the result to {@code outputFile}
     * (missing parent directories are created).
     */
    public static void transform(Path xmlFile, Path xslFile, Path outputFile) throws KnifeException {
        LOG.fine(() -> "Transforming " + xmlFile + " with " + xslFile);
        try {
            Files.createDirectories(outputFile.toAbsolutePath().getParent());
            TransformerFactory factory = TransformerFactory.newInstance();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            Transformer transformer = factory.newTransformer(new StreamSource(xslFile.toFile()));
            transformer.transform(new StreamSource(xmlFile.toFile()),
                    new StreamResult(outputFile.toFile()));
            LOG.info(() -> "Transformation result written to " + outputFile);
        } catch (TransformerException | IOException e) {
            throw new KnifeException("Transformation of " + xmlFile + " failed: " + e.getMessage(), e);
        }
    }
}
