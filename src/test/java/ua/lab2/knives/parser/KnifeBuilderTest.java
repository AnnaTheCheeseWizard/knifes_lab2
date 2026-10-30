package ua.lab2.knives.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ua.lab2.knives.model.BladeMaterial;
import ua.lab2.knives.model.HandleMaterial;
import ua.lab2.knives.model.Knife;
import ua.lab2.knives.model.KnifeType;
import ua.lab2.knives.model.WoodType;

class KnifeBuilderTest {

    private static KnifeBuilder complete(String handleElement, String handleText) {
        KnifeBuilder b = new KnifeBuilder("k001");
        b.accept("Type", "dagger");
        b.accept("Handy", "one-handed");
        b.accept("Origin", "Italy");
        b.accept("Length", "30");
        b.accept("Width", "35");
        b.accept("BladeMaterial", "cast-iron");
        b.accept(handleElement, handleText);
        b.accept("Fuller", "1");
        b.accept("Value", "0");
        return b;
    }

    @Test
    void buildsKnifeWithWoodenHandle() {
        Knife knife = complete("WoodType", "oak").build();
        assertEquals("k001", knife.id());
        assertEquals(KnifeType.DAGGER, knife.type());
        assertEquals("Italy", knife.origin());
        assertEquals(30, knife.visual().blade().lengthCm());
        assertEquals(35, knife.visual().blade().widthMm());
        assertEquals(BladeMaterial.CAST_IRON, knife.visual().bladeMaterial());
        assertEquals(HandleMaterial.WOOD, knife.visual().handle().material());
        assertEquals(WoodType.OAK, knife.visual().handle().woodType());
        assertTrue(knife.visual().fuller());
        assertFalse(knife.collectible());
    }

    @Test
    void plasticAndMetalHandles() {
        assertEquals(HandleMaterial.PLASTIC, complete("Plastic", "").build().visual().handle().material());
        assertEquals(HandleMaterial.METAL, complete("Metal", "").build().visual().handle().material());
    }

    @Test
    void containerElementsAreIgnored() {
        KnifeBuilder b = complete("Metal", "");
        b.accept("Visual", "   ");
        b.accept("Wooden", "");
        assertEquals(HandleMaterial.METAL, b.build().visual().handle().material());
    }

    @Test
    void incompleteKnifeIsRejected() {
        KnifeBuilder b = new KnifeBuilder("k001");
        b.accept("Type", "knife");
        IllegalStateException e = assertThrows(IllegalStateException.class, b::build);
        assertTrue(e.getMessage().contains("Length"));
    }

    @Test
    void missingIdIsRejected() {
        KnifeBuilder b = new KnifeBuilder(null);
        b.accept("Type", "dagger");
        b.accept("Handy", "one-handed");
        b.accept("Origin", "Italy");
        b.accept("Length", "30");
        b.accept("Width", "35");
        b.accept("BladeMaterial", "steel");
        b.accept("Metal", "");
        b.accept("Fuller", "true");
        b.accept("Value", "true");
        IllegalStateException e = assertThrows(IllegalStateException.class, b::build);
        assertTrue(e.getMessage().contains("id"));
    }
}
