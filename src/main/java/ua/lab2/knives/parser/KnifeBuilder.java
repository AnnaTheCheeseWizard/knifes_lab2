package ua.lab2.knives.parser;

import ua.lab2.knives.model.Blade;
import ua.lab2.knives.model.BladeMaterial;
import ua.lab2.knives.model.HandType;
import ua.lab2.knives.model.Handle;
import ua.lab2.knives.model.HandleMaterial;
import ua.lab2.knives.model.Knife;
import ua.lab2.knives.model.KnifeType;
import ua.lab2.knives.model.Visual;
import ua.lab2.knives.model.WoodType;
import ua.lab2.knives.util.XmlEnums;

/**
 * Assembles a single {@link Knife} from "element name / text" pairs.
 * It is shared by the SAX, DOM and StAX parsers so the mapping logic is written only once.
 */
public final class KnifeBuilder {

    private final String id;
    private KnifeType type;
    private HandType handy;
    private String origin;
    private Integer length;
    private Integer width;
    private BladeMaterial bladeMaterial;
    private HandleMaterial handleMaterial;
    private WoodType woodType;
    private Boolean fuller;
    private Boolean collectible;

    public KnifeBuilder(String id) {
        this.id = id;
    }

    /**
     * Accepts the text content of an XML element. Container elements are ignored.
     *
     * @param element local name of the element
     * @param text    trimmed text content
     */
    public void accept(String element, String text) {
        switch (element) {
            case "Type" -> type = XmlEnums.parse(KnifeType.class, text);
            case "Handy" -> handy = XmlEnums.parse(HandType.class, text);
            case "Origin" -> origin = text;
            case "Length" -> length = Integer.valueOf(text);
            case "Width" -> width = Integer.valueOf(text);
            case "BladeMaterial" -> bladeMaterial = XmlEnums.parse(BladeMaterial.class, text);
            case "WoodType" -> {
                handleMaterial = HandleMaterial.WOOD;
                woodType = XmlEnums.parse(WoodType.class, text);
            }
            case "Plastic" -> handleMaterial = HandleMaterial.PLASTIC;
            case "Metal" -> handleMaterial = HandleMaterial.METAL;
            case "Fuller" -> fuller = XmlEnums.parseBoolean(text);
            case "Value" -> collectible = XmlEnums.parseBoolean(text);
            default -> {
                // container element (Knife, Visual, Blade, Handle, Wooden, ...) - nothing to read
            }
        }
    }

    /**
     * Builds the knife.
     *
     * @throws IllegalStateException if some mandatory element has not been provided
     */
    public Knife build() {
        Blade blade = new Blade(require(length, "Length"), require(width, "Width"));
        Handle handle = new Handle(require(handleMaterial, "Handle"), woodType);
        Visual visual = new Visual(blade, require(bladeMaterial, "BladeMaterial"), handle,
                require(fuller, "Fuller"));
        return new Knife(require(id, "id"), require(type, "Type"), require(handy, "Handy"),
                require(origin, "Origin"), visual, require(collectible, "Value"));
    }

    private static <T> T require(T value, String name) {
        if (value == null) {
            throw new IllegalStateException("Missing mandatory element/attribute: " + name);
        }
        return value;
    }
}
