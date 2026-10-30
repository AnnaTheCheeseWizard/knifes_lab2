package ua.lab2.knives.model;

/**
 * Visual characteristics of a knife.
 *
 * @param blade         blade dimensions
 * @param bladeMaterial blade material
 * @param handle        handle description
 * @param fuller        whether the blade has a fuller ("дол")
 */
public record Visual(Blade blade, BladeMaterial bladeMaterial, Handle handle, boolean fuller) {
}
