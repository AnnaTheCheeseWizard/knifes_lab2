package ua.lab2.knives.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ua.lab2.knives.model.Knife;

/** DOM implementation. */
public class DomKnifeParser extends AbstractKnifeParser {

    @Override
    protected List<Knife> doParse(InputStream in)
            throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        Document document = factory.newDocumentBuilder().parse(in);

        NodeList knifeNodes = document.getElementsByTagNameNS(KnifeXml.NAMESPACE, KnifeXml.KNIFE);
        List<Knife> knives = new ArrayList<>(knifeNodes.getLength());
        for (int i = 0; i < knifeNodes.getLength(); i++) {
            knives.add(readKnife((Element) knifeNodes.item(i)));
        }
        return knives;
    }

    private static Knife readKnife(Element knifeElement) {
        String id = knifeElement.hasAttribute(KnifeXml.ID_ATTRIBUTE)
                ? knifeElement.getAttribute(KnifeXml.ID_ATTRIBUTE) : null;
        KnifeBuilder builder = new KnifeBuilder(id);
        NodeList descendants = knifeElement.getElementsByTagNameNS("*", "*");
        for (int i = 0; i < descendants.getLength(); i++) {
            Element element = (Element) descendants.item(i);
            boolean leaf = element.getElementsByTagNameNS("*", "*").getLength() == 0;
            if (leaf) {
                builder.accept(element.getLocalName(), element.getTextContent().trim());
            }
        }
        return builder.build();
    }
}
