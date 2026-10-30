package ua.lab2.knives.util;

import java.util.Locale;

/** Conversion of XML literals (e.g. {@code cast-iron}, {@code true}) to Java values. */
public final class XmlEnums {

    private XmlEnums() {
    }

    /**
     * Converts an XML literal to an enum constant: {@code "cast-iron"} becomes {@code CAST_IRON}.
     *
     * @throws IllegalArgumentException if the literal does not match any constant
     */
    public static <E extends Enum<E>> E parse(Class<E> type, String xmlValue) {
        String normalized = xmlValue.trim().toUpperCase(Locale.ROOT).replace('-', '_');
        try {
            return Enum.valueOf(type, normalized);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Unknown " + type.getSimpleName() + " value: '" + xmlValue + "'", e);
        }
    }

    /** Parses an {@code xs:boolean} literal ({@code true}/{@code false}/{@code 1}/{@code 0}). */
    public static boolean parseBoolean(String xmlValue) {
        String v = xmlValue.trim();
        if ("true".equals(v) || "1".equals(v)) {
            return true;
        }
        if ("false".equals(v) || "0".equals(v)) {
            return false;
        }
        throw new IllegalArgumentException("Invalid boolean value: '" + xmlValue + "'");
    }
}
