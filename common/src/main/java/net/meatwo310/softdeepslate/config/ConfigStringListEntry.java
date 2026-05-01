package net.meatwo310.softdeepslate.config;

import java.util.List;

public record ConfigStringListEntry(
        String key,
        List<String> defaultValue,
        String comment
) {
    public ConfigStringListEntry withDefaultValue(List<String> defaultValue) {
        return new ConfigStringListEntry(key, defaultValue, comment);
    }
}
