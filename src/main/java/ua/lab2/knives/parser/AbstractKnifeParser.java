package ua.lab2.knives.parser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import org.xml.sax.SAXException;
import ua.lab2.knives.KnifeException;
import ua.lab2.knives.model.Knife;

/** Template for all parsers: opens the file, logs, and unifies error handling. */
public abstract class AbstractKnifeParser implements KnifeParser {

    private static final Logger LOG = Logger.getLogger(AbstractKnifeParser.class.getName());

    @Override
    public final List<Knife> parse(Path xmlFile) throws KnifeException {
        String name = getClass().getSimpleName();
        LOG.fine(() -> name + ": parsing " + xmlFile);
        try (InputStream in = Files.newInputStream(xmlFile)) {
            List<Knife> knives = doParse(in);
            LOG.info(() -> name + ": parsed " + knives.size() + " knives from " + xmlFile);
            return knives;
        } catch (IOException | SAXException | ParserConfigurationException | XMLStreamException
                 | IllegalArgumentException | IllegalStateException e) {
            throw new KnifeException(name + " failed to parse " + xmlFile + ": " + e.getMessage(), e);
        }
    }

    /** Parser-specific reading of the stream. */
    protected abstract List<Knife> doParse(InputStream in)
            throws IOException, SAXException, ParserConfigurationException, XMLStreamException;
}
