package ua.lab2.knives.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import ua.lab2.knives.KnifeException;
import ua.lab2.knives.TestData;
import ua.lab2.knives.model.Knife;

class ParserTypeTest {

    @Test
    void createsMatchingImplementations() {
        assertTrue(ParserType.SAX.create() instanceof SaxKnifeParser);
        assertTrue(ParserType.DOM.create() instanceof DomKnifeParser);
        assertTrue(ParserType.STAX.create() instanceof StaxKnifeParser);
    }

    @Test
    void allParsersProduceTheSameCollection() throws KnifeException {
        List<Knife> expected = ParserType.DOM.create().parse(TestData.XML);
        assertEquals(expected, ParserType.SAX.create().parse(TestData.XML));
        assertEquals(expected, ParserType.STAX.create().parse(TestData.XML));
    }
}
