package ua.lab2.knives;

import java.nio.file.Path;
import ua.lab2.knives.model.Blade;
import ua.lab2.knives.model.BladeMaterial;
import ua.lab2.knives.model.HandType;
import ua.lab2.knives.model.Handle;
import ua.lab2.knives.model.HandleMaterial;
import ua.lab2.knives.model.Knife;
import ua.lab2.knives.model.KnifeType;
import ua.lab2.knives.model.Visual;
import ua.lab2.knives.model.WoodType;

/** Shared constants for tests. */
public final class TestData {

    public static final Path XML = Path.of("data/knives.xml");
    public static final Path XSD = Path.of("data/knives.xsd");
    public static final Path XSL = Path.of("data/knives.xsl");
    public static final int KNIFE_COUNT = 11;

    private TestData() {
    }

    public static Path resource(String name) {
        return Path.of("src/test/resources", name);
    }

    /** Knife k003 from data/knives.xml. */
    public static Knife saber() {
        return new Knife("k003", KnifeType.SABER, HandType.ONE_HANDED, "Poland",
                new Visual(new Blade(85, 40), BladeMaterial.DAMASCUS_STEEL,
                        new Handle(HandleMaterial.WOOD, WoodType.WALNUT), true), true);
    }

    public static Knife knife(String id, KnifeType type, String origin, int length, int width) {
        return new Knife(id, type, HandType.ONE_HANDED, origin,
                new Visual(new Blade(length, width), BladeMaterial.STEEL,
                        new Handle(HandleMaterial.METAL, null), false), false);
    }
}
