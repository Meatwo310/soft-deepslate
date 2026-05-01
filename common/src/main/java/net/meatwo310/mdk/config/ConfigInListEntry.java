package net.meatwo310.mdk.config;

import java.util.List;

public record ConfigInListEntry<T>(
        String key,
        T defaultValue,
        List<? extends T> allowedValues,
        String comment
) implements ConfigEntry<T> {}
