package net.meatwo310.mdk.config;

import java.util.function.Predicate;

public record ConfigValidatedEntry<T>(
        String key,
        T defaultValue,
        Predicate<Object> validator,
        String comment
) implements ConfigEntry<T> {}
