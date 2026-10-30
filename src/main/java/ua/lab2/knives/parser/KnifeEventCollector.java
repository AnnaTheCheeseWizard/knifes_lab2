package ua.lab2.knives.parser;

import java.util.ArrayList;
import java.util.List;
import ua.lab2.knives.model.Knife;

/**
 * Event sink for event-based parsers (SAX and StAX): both translate their events
 * into {@code start / characters / end} calls, so the collecting logic is shared.
 */
public final class KnifeEventCollector {

    private final List<Knife> knives = new ArrayList<>();
    private final StringBuilder text = new StringBuilder();
    private KnifeBuilder current;

    public void start(String localName, String idAttribute) {
        text.setLength(0);
        if (KnifeXml.KNIFE.equals(localName)) {
            current = new KnifeBuilder(idAttribute);
        }
    }

    public void characters(String chars) {
        text.append(chars);
    }

    public void end(String localName) {
        if (current == null) {
            return;
        }
        if (KnifeXml.KNIFE.equals(localName)) {
            knives.add(current.build());
            current = null;
        } else {
            current.accept(localName, text.toString().trim());
        }
    }

    public List<Knife> getKnives() {
        return knives;
    }
}
