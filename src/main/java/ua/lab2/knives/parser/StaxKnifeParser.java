package ua.lab2.knives.parser;

import java.io.InputStream;
import java.util.List;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import ua.lab2.knives.model.Knife;

/** StAX (cursor API) implementation. */
public class StaxKnifeParser extends AbstractKnifeParser {

    @Override
    protected List<Knife> doParse(InputStream in) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newFactory();
        factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        XMLStreamReader reader = factory.createXMLStreamReader(in);
        KnifeEventCollector collector = new KnifeEventCollector();
        try {
            while (reader.hasNext()) {
                switch (reader.next()) {
                    case XMLStreamConstants.START_ELEMENT ->
                            collector.start(reader.getLocalName(),
                                    reader.getAttributeValue(null, KnifeXml.ID_ATTRIBUTE));
                    case XMLStreamConstants.CHARACTERS -> collector.characters(reader.getText());
                    case XMLStreamConstants.END_ELEMENT -> collector.end(reader.getLocalName());
                    default -> {
                        // comments, processing instructions, ... are not interesting
                    }
                }
            }
        } finally {
            reader.close();
        }
        return collector.getKnives();
    }
}
