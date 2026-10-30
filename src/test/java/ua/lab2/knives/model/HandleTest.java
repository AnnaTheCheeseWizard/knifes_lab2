package ua.lab2.knives.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class HandleTest {

    @Test
    void woodenHandleKeepsWoodType() {
        Handle handle = new Handle(HandleMaterial.WOOD, WoodType.OAK);
        assertEquals(WoodType.OAK, handle.woodType());
    }

    @Test
    void woodenHandleWithoutWoodTypeIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Handle(HandleMaterial.WOOD, null));
    }

    @Test
    void nonWoodenHandleWithWoodTypeIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Handle(HandleMaterial.PLASTIC, WoodType.ASH));
    }

    @Test
    void materialIsMandatory() {
        assertThrows(NullPointerException.class, () -> new Handle(null, null));
    }

    @Test
    void metalHandleWithoutWoodTypeIsValid() {
        assertEquals(HandleMaterial.METAL, new Handle(HandleMaterial.METAL, null).material());
    }
}
