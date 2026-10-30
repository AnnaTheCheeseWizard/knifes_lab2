package ua.lab2.knives.transform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import ua.lab2.knives.KnifeException;
import ua.lab2.knives.TestData;
import ua.lab2.knives.model.Knife;
import ua.lab2.knives.parser.DomKnifeParser;

class XslTransformerTest {

    private static final String NS = "http://lab2.example.com/knives";
    private Path output;

    @BeforeEach
    void setUp() throws IOException {
        output = Files.createTempDirectory("knives-xsl").resolve("nested").resolve("result.xml");
    }

    private Document transformAndRead() throws Exception {
        XslTransformer.transform(TestData.XML, TestData.XSL, output);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        return factory.newDocumentBuilder().parse(output.toFile());
    }

    @Test
    void groupsKnivesByType() throws Exception {
        Set<String> expectedTypes = new HashSet<>();
        for (Knife knife : new DomKnifeParser().parse(TestData.XML)) {
            expectedTypes.add(knife.type().name().toLowerCase());
        }

        Document result = transformAndRead();
        assertEquals("KnivesByType", result.getDocumentElement().getLocalName());

        NodeList groups = result.getElementsByTagNameNS(NS, "TypeGroup");
        Set<String> actualTypes = new HashSet<>();
        int total = 0;
        for (int i = 0; i < groups.getLength(); i++) {
            Element group = (Element) groups.item(i);
            actualTypes.add(group.getAttribute("type"));
            int inGroup = group.getElementsByTagNameNS(NS, "Knife").getLength();
            assertEquals(Integer.parseInt(group.getAttribute("count")), inGroup);
            total += inGroup;
        }
        assertEquals(expectedTypes, actualTypes);
        assertEquals(TestData.KNIFE_COUNT, total);
    }

    @Test
    void typeElementIsMovedToGroupAttribute() throws Exception {
        Document result = transformAndRead();
        assertEquals(0, result.getElementsByTagNameNS(NS, "Type").getLength());
    }

    @Test
    void knivesInsideGroupAreSortedByLength() throws Exception {
        Document result = transformAndRead();
        NodeList groups = result.getElementsByTagNameNS(NS, "TypeGroup");
        for (int i = 0; i < groups.getLength(); i++) {
            NodeList lengths = ((Element) groups.item(i)).getElementsByTagNameNS(NS, "Length");
            int previous = 0;
            for (int j = 0; j < lengths.getLength(); j++) {
                int current = Integer.parseInt(lengths.item(j).getTextContent());
                assertTrue(current >= previous);
                previous = current;
            }
        }
    }

    @Test
    void missingStylesheetIsReported() {
        assertThrows(KnifeException.class,
                () -> XslTransformer.transform(TestData.XML, Path.of("nope.xsl"), output));
    }

    @Test
    void malformedXmlIsReported() {
        assertThrows(KnifeException.class,
                () -> XslTransformer.transform(TestData.resource("malformed.xml"), TestData.XSL, output));
    }
}
