package net.meatwo310.mdk.config;

public interface ConfigEntry<T> {
    String key();
    T defaultValue();
    String comment();
}
