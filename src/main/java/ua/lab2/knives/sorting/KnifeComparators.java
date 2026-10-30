package ua.lab2.knives.sorting;

import java.util.Comparator;
import ua.lab2.knives.model.Knife;

/** Ready-made {@link Comparator}s for {@link Knife}. */
public final class KnifeComparators {

    public static final Comparator<Knife> BY_ID = Comparator.comparing(Knife::id);

    /** Order of the {@code KnifeType} enum constants. */
    public static final Comparator<Knife> BY_TYPE = Comparator.comparing(Knife::type);

    public static final Comparator<Knife> BY_ORIGIN = Comparator.comparing(Knife::origin);

    public static final Comparator<Knife> BY_BLADE_LENGTH =
            Comparator.comparingInt(k -> k.visual().blade().lengthCm());

    public static final Comparator<Knife> BY_BLADE_WIDTH =
            Comparator.comparingInt(k -> k.visual().blade().widthMm());

    /** Longest blade first, ties are resolved by id. */
    public static final Comparator<Knife> BY_LENGTH_DESC_THEN_ID =
            BY_BLADE_LENGTH.reversed().thenComparing(BY_ID);

    private KnifeComparators() {
    }
}
