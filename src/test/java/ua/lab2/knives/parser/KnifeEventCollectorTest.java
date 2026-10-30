package ua.lab2.knives.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class KnifeEventCollectorTest {

    @Test
    void eventsOutsideKnifeAreIgnored() {
        KnifeEventCollector collector = new KnifeEventCollector();
        collector.start("Knives", null);
        collector.characters("\n   ");
        collector.end("Knives");
        assertTrue(collector.getKnives().isEmpty());
    }

    @Test
    void collectsKnifeFromEvents() {
        KnifeEventCollector c = new KnifeEventCollector();
        c.start("Knives", null);
        c.start("Knife", "k007");
        String[][] fields = {{"Type", "knife"}, {"Handy", "two-handed"}, {"Origin", "Peru"},
                {"Length", "12"}, {"Width", "11"}, {"BladeMaterial", "bronze"}, {"Metal", ""},
                {"Fuller", "false"}, {"Value", "true"}};
        for (String[] f : fields) {
            c.start(f[0], null);
            c.characters(" " + f[1] + " ");
            c.end(f[0]);
        }
        c.end("Knife");
        c.end("Knives");
        assertEquals(1, c.getKnives().size());
        assertEquals("k007", c.getKnives().get(0).id());
        assertEquals("Peru", c.getKnives().get(0).origin());
    }
}
