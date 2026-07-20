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
                                    "minecraft:deepslate",
                                    "minecraft:cobbled_deepslate",
                                    "minecraft:deepslate_coal_ore",
                                    "minecraft:deepslate_copper_ore",
                                    "minecraft:deepslate_diamond_ore",
                                    "minecraft:deepslate_emerald_ore",
                                    "minecraft:deepslate_gold_ore",
                                    "minecraft:deepslate_iron_ore",
                                    "minecraft:deepslate_lapis_ore",
                                    "minecraft:deepslate_redstone_ore",
                                    "minecraft:polished_deepslate",
                                    "minecraft:infested_deepslate",
                                    "minecraft:deepslate_bricks",
                                    "minecraft:cracked_deepslate_bricks",
                                    "minecraft:deepslate_tiles",
                                    "minecraft:cracked_deepslate_tiles",
                                    "minecraft:chiseled_deepslate",
                                    "minecraft:cobbled_deepslate_wall",
                                    "minecraft:polished_deepslate_wall",
                                    "minecraft:deepslate_brick_wall",
                                    "minecraft:deepslate_tile_wall",
                                    "minecraft:cobbled_deepslate_stairs",
                                    "minecraft:polished_deepslate_stairs",
                                    "minecraft:deepslate_brick_stairs",
                                    "minecraft:deepslate_tile_stairs",
                                    "minecraft:cobbled_deepslate_slab",
                                    "minecraft:polished_deepslate_slab",
                                    "minecraft:deepslate_brick_slab",
                                    "minecraft:deepslate_tile_slab"),
                            () -> "",
                            value -> value instanceof String);

    public static final ConfigEntries ENTRIES = BUILDER.build();

    private ServerConfig() {}
}
