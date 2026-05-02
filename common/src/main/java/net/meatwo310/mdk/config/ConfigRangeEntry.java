package net.meatwo310.mdk.config;

public record ConfigRangeEntry<T extends Comparable<? super T>>(
        String key,
        T defaultValue,
        T min,
        T max,
        String comment
) implements ConfigEntry<T> {}
