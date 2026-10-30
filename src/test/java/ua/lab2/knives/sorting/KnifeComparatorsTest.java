package ua.lab2.knives.sorting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.lab2.knives.TestData;
import ua.lab2.knives.model.Knife;
import ua.lab2.knives.model.KnifeType;

class KnifeComparatorsTest {

    private List<Knife> knives;

    @BeforeEach
    void setUp() {
        knives = new ArrayList<>(List.of(
                TestData.knife("k002", KnifeType.SWORD, "Spain", 100, 30),
                TestData.knife("k003", KnifeType.DAGGER, "Italy", 30, 50),
                TestData.knife("k001", KnifeType.KNIFE, "Peru", 100, 10)));
    }

    private List<String> ids(Comparator<Knife> comparator) {
        return knives.stream().sorted(comparator).map(Knife::id).collect(Collectors.toList());
    }

    @Test
    void byId() {
        assertEquals(List.of("k001", "k002", "k003"), ids(KnifeComparators.BY_ID));
    }

    @Test
    void byTypeFollowsEnumOrder() {
        assertEquals(List.of("k001", "k003", "k002"), ids(KnifeComparators.BY_TYPE));
    }

    @Test
    void byOrigin() {
        assertEquals(List.of("k003", "k001", "k002"), ids(KnifeComparators.BY_ORIGIN));
    }

    @Test
    void byBladeLength() {
        assertEquals("k003", ids(KnifeComparators.BY_BLADE_LENGTH).get(0));
    }

    @Test
    void byBladeWidth() {
        assertEquals(List.of("k001", "k002", "k003"), ids(KnifeComparators.BY_BLADE_WIDTH));
    }

    @Test
    void longestFirstThenById() {
        assertEquals(List.of("k001", "k002", "k003"), ids(KnifeComparators.BY_LENGTH_DESC_THEN_ID));
    }
}
