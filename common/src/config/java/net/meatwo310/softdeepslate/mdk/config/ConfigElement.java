package net.meatwo310.softdeepslate.mdk.config;

interface ConfigElement {
    void bindTo(ConfigVisitor visitor);
}
