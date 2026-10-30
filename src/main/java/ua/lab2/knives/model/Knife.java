package ua.lab2.knives.model;

/**
 * Cold weapon (Java counterpart of the {@code Knife} element in the XSD).
 *
 * @param id          unique identifier (XML attribute {@code id})
 * @param type        kind of weapon
 * @param handy       one- or two-handed
 * @param origin      country of origin
 * @param visual      visual characteristics
 * @param collectible whether the weapon is collectible
 */
public record Knife(String id, KnifeType type, HandType handy, String origin, Visual visual, boolean collectible) {
}
