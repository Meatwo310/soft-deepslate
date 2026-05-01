package net.meatwo310.mdk.config;

public record ConfigEnumEntry<E extends Enum<E>>(
        String key,
        E defaultValue,
        String comment
) implements ConfigEntry<E> {}
