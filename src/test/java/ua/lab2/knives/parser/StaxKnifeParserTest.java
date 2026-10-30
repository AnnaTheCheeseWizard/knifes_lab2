package ua.lab2.knives.parser;

class StaxKnifeParserTest extends AbstractKnifeParserTest {

    @Override
    protected KnifeParser createParser() {
        return new StaxKnifeParser();
    }
}
