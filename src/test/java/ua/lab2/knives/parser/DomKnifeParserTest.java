package ua.lab2.knives.parser;

class DomKnifeParserTest extends AbstractKnifeParserTest {

    @Override
    protected KnifeParser createParser() {
        return new DomKnifeParser();
    }
}
