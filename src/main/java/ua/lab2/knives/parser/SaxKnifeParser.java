package ua.lab2.knives.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ua.lab2.knives.model.Knife;

/** SAX implementation. */
public class SaxKnifeParser extends AbstractKnifeParser {

    @Override
    protected List<Knife> doParse(InputStream in)
            throws IOException, SAXException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        Handler handler = new Handler();
        factory.newSAXParser().parse(in, handler);
        return handler.collector.getKnives();
    }

    private static final class Handler extends DefaultHandler {
        private final KnifeEventCollector collector = new KnifeEventCollector();

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            collector.start(localName, attributes.getValue(KnifeXml.ID_ATTRIBUTE));
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            collector.characters(new String(ch, start, length));
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            collector.end(localName);
        }
    }
}
