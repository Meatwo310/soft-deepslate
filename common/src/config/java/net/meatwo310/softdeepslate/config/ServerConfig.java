package net.meatwo310.softdeepslate.config;

import net.meatwo310.softdeepslate.mdk.config.ConfigEntries;
import net.meatwo310.softdeepslate.mdk.config.ConfigEntry;
import net.meatwo310.softdeepslate.mdk.config.ConfigEntryBuilder;

import java.util.List;

public final class ServerConfig {
    private static final ConfigEntryBuilder BUILDER = new ConfigEntryBuilder();

    public static final ConfigEntry.DoubleEntry MINING_SPEED_MULTIPLIER =
            BUILDER.comment("""
                            Multiplies the mining speed of configured blocks.
                            A value of 2.0 makes deepslate, which is twice as hard as stone, mine at roughly the same speed as stone.""")
                    .defineInRange("miningSpeedMultiplier", 2.0D, 0.0D, (double) Float.MAX_VALUE);

    public static final ConfigEntry.ListEntry<String> BLOCKS =
            BUILDER.comment("""
                            Blocks affected by the mining speed multiplier.
                            Use a block ID or prefix a tag with # to add blocks.
                            Prefix either form with ! to exclude it or !! to force-add it.
                            Priority is !! (force-add), then ! (exclude), then an unprefixed addition.
                            Changes are applied when the config or block tags reload.""")
                    .defineList(
                            "blocks",
                            List.of(
                                    "#softdeepslate:ore_bearing_ground/deepslate",
                                    "#softdeepslate:cobblestones/deepslate",
                                    "#softdeepslate:ores_in_ground/deepslate",
                                    "#softdeepslate:building_blocks"),
                            () -> "",
                            value -> value instanceof String);

    public static final ConfigEntries ENTRIES = BUILDER.build();

    private ServerConfig() {}
}
