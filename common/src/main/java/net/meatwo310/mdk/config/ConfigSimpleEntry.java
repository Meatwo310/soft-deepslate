package net.meatwo310.mdk.config;

public record ConfigSimpleEntry<T>(
        String key,
        T defaultValue,
        String comment
) implements ConfigEntry<T> {}
