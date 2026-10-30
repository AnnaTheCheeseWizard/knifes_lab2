package ua.lab2.knives.parser;

import java.util.function.Supplier;

/** Available parser implementations. */
public enum ParserType {
    SAX(SaxKnifeParser::new),
    DOM(DomKnifeParser::new),
    STAX(StaxKnifeParser::new);

    private final Supplier<KnifeParser> factory;

    ParserType(Supplier<KnifeParser> factory) {
        this.factory = factory;
    }

    public KnifeParser create() {
        return factory.get();
    }
}
