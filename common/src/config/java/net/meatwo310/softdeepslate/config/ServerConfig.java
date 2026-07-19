package net.meatwo310.softdeepslate.config;

import net.meatwo310.softdeepslate.mdk.config.ConfigEntries;
import net.meatwo310.softdeepslate.mdk.config.ConfigEntryBuilder;

public class ServerConfig {
    private static final ConfigEntryBuilder BUILDER = new ConfigEntryBuilder();

    public static final ConfigEntries ENTRIES = BUILDER.build();
}
