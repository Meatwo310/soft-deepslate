package net.meatwo310.softdeepslate.config;

public record ConfigDoubleEntry(
        String key,
        double defaultValue,
        double min,
        double max,
        String comment
) {}
