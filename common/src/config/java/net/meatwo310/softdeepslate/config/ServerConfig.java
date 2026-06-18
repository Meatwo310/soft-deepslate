package net.meatwo310.softdeepslate.config;

import net.meatwo310.softdeepslate.mdk.config.ConfigEntries;
import net.meatwo310.softdeepslate.mdk.config.ConfigEntry;
import net.meatwo310.softdeepslate.mdk.config.ConfigEntryBuilder;

import java.util.List;

public final class ServerConfig {
    private static final ConfigEntryBuilder BUILDER = new ConfigEntryBuilder();

    public static final ConfigEntry.DoubleEntry MINING_SPEED =
            BUILDER.comment("""
                            Adjusts the mining speed for deepslate.
                            Given that deepslate is twice as hard as stone, a default value of 2.0 allows it to be mined at the same rate as stone.""")
                    .defineInRange("miningSpeed", 2.0D, 0.0D, (double) Float.MAX_VALUE);

    public static final ConfigEntry.ListEntry<String> BLOCKS =
            BUILDER.comment("""
                            List of block IDs or block tags (prefix with #) where this mod adjusts the mining speed.
                            `/reload` to apply.""")
                    .defineList(
                            "blocks",
                            List.of(
                                    "minecraft:deepslate",
                                    "#c:cobblestones/deepslate",
                                    "#c:ores_in_ground/deepslate",
                                    "#softdeepslate:building_blocks"
                            ),
                            () -> "",
                            value -> value instanceof String);

    public static final ConfigEntries ENTRIES = BUILDER.build();

    private ServerConfig() {}
}
