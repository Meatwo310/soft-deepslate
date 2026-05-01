package net.meatwo310.softdeepslate.config;

public interface IModServerConfigValidator {
    boolean isValidId(String entry);

    default boolean isValidIdOrTag(Object entry) {
        return entry instanceof String s && isValidIdOrTag(s);
    }

    default boolean isValidIdOrTag(String entry) {
        String normalized = entry.startsWith("#") ? entry.substring(1) : entry;
        return isValidId(normalized);
    }
}
