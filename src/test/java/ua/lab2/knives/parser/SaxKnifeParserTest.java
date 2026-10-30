package ua.lab2.knives.parser;

class SaxKnifeParserTest extends AbstractKnifeParserTest {

    @Override
    protected KnifeParser createParser() {
        return new SaxKnifeParser();
    }
}
