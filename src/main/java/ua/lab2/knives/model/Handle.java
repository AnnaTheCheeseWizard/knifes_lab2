package ua.lab2.knives.model;

import java.util.Objects;

/**
 * Handle of a knife. The wood type is specified if and only if the handle is wooden.
 *
 * @param material handle material
 * @param woodType kind of wood (only for {@link HandleMaterial#WOOD}, otherwise {@code null})
 */
public record Handle(HandleMaterial material, WoodType woodType) {

    public Handle {
        Objects.requireNonNull(material, "material");
        if (material == HandleMaterial.WOOD && woodType == null) {
            throw new IllegalArgumentException("Wooden handle requires a wood type");
        }
        if (material != HandleMaterial.WOOD && woodType != null) {
            throw new IllegalArgumentException("Wood type is allowed only for a wooden handle");
        }
    }
}
