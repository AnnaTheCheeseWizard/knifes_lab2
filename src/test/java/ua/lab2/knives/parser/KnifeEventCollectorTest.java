package ua.lab2.knives.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.function.Function;
import org.junit.jupiter.api.Test;

class KnifeEventCollectorTest {

    private static final Function<String, String> NO_ATTRIBUTES = name -> null;

    private static Function<String, String> attributes(String name, String value) {
        return Map.of(name, value)::get;
    }

    @Test
    void eventsOutsideKnifeAreIgnored() {
        KnifeEventCollector collector = new KnifeEventCollector();
        collector.start("Knives", NO_ATTRIBUTES);
        collector.characters("\n   ");
        collector.end("Knives");
        assertTrue(collector.getKnives().isEmpty());
    }

    @Test
    void collectsKnifeFromEvents() {
        KnifeEventCollector c = new KnifeEventCollector();
        c.start("Knives", NO_ATTRIBUTES);
        c.start("Knife", attributes("id", "k007"));
        String[][] fields = {{"Type", "knife"}, {"Handy", "two-handed"}, {"Origin", "Peru"},
                {"Length", "12", "cm"}, {"Width", "11", "mm"}, {"BladeMaterial", "bronze"}, {"Metal", ""},
                {"Fuller", "false"}, {"Value", "true"}};
        for (String[] f : fields) {
            c.start(f[0], f.length > 2 ? attributes("unit", f[2]) : NO_ATTRIBUTES);
            c.characters(" " + f[1] + " ");
            c.end(f[0]);
        }
        c.end("Knife");
        c.end("Knives");
        assertEquals(1, c.getKnives().size());
        assertEquals("k007", c.getKnives().get(0).id());
        assertEquals("Peru", c.getKnives().get(0).origin());
    }

    @Test
    void wrongUnitIsRejectedAtElementStart() {
        KnifeEventCollector c = new KnifeEventCollector();
        c.start("Knife", attributes("id", "k001"));
        assertThrows(IllegalArgumentException.class, () -> c.start("Length", attributes("unit", "inch")));
    }
}
