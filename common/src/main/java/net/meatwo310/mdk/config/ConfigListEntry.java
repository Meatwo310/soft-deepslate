package net.meatwo310.mdk.config;

import java.util.List;

public record ConfigListEntry<T>(
        String key,
        List<? extends T> defaultValue,
        T newElementValue,
        String comment
) implements ConfigEntry<List<? extends T>> {
    public ConfigListEntry<T> withDefaultValue(List<? extends T> defaultValue) {
        return new ConfigListEntry<>(key, defaultValue, newElementValue, comment);
    }
}
