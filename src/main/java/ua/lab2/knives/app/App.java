package ua.lab2.knives.app;

import java.nio.file.Path;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import ua.lab2.knives.KnifeException;
import ua.lab2.knives.model.Knife;
import ua.lab2.knives.parser.ParserType;
import ua.lab2.knives.sorting.KnifeComparators;
import ua.lab2.knives.transform.XslTransformer;
import ua.lab2.knives.validation.XmlValidator;

/**
 * Usage: {@code App [knives.xml] [knives.xsd] [knives.xsl] [output.xml]}.
 * Validates the XML, parses it with SAX, DOM and StAX, sorts the objects and
 * transforms the document with XSL.
 */
public final class App {

    private static final Logger LOG = Logger.getLogger(App.class.getName());

    private App() {
    }

    public static void main(String[] args) {
        int code = run(args);
        if (code != 0) {
            System.exit(code);
        }
    }

    /** @return process exit code: 0 on success, 1 on failure */
    static int run(String[] args) {
        LoggingConfig.init();
        Path xml = argument(args, 0, "data/knives.xml");
        Path xsd = argument(args, 1, "data/knives.xsd");
        Path xsl = argument(args, 2, "data/knives.xsl");
        Path output = argument(args, 3, "output/knives-by-type.xml");
        try {
            XmlValidator.validate(xml, xsd);

            Map<ParserType, List<Knife>> results = new EnumMap<>(ParserType.class);
            for (ParserType type : ParserType.values()) {
                results.put(type, type.create().parse(xml));
            }
            boolean same = results.values().stream().distinct().count() == 1;
            LOG.info(() -> "SAX, DOM and StAX produced identical collections: " + same);

            List<Knife> knives = results.get(ParserType.DOM);
            logSorted("by blade length (longest first)", knives, KnifeComparators.BY_LENGTH_DESC_THEN_ID);
            logSorted("by type", knives, KnifeComparators.BY_TYPE.thenComparing(KnifeComparators.BY_ID));
            logSorted("by origin", knives, KnifeComparators.BY_ORIGIN.thenComparing(KnifeComparators.BY_ID));

            XslTransformer.transform(xml, xsl, output);
            return 0;
        } catch (KnifeException e) {
            LOG.log(Level.SEVERE, "Processing failed: " + e.getMessage(), e);
            return 1;
        }
    }

    private static void logSorted(String title, List<Knife> knives, java.util.Comparator<Knife> comparator) {
        LOG.info(() -> "Knives sorted " + title + ":");
        knives.stream().sorted(comparator).forEach(k -> LOG.info(() -> "  " + k));
    }

    private static Path argument(String[] args, int index, String defaultValue) {
        return Path.of(args.length > index ? args[index] : defaultValue);
    }
}
