package net.meatwo310.softdeepslate.config;

import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class ServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static ModConfigSpec.DoubleValue MINING_SPEED = BUILDER
            .comment("Adjusts the mining speed for deepslate. Given that deepslate is twice as hard as stone, " +
                    "a default value of 2.0 allows it to be mined at the same rate as stone.")
            .defineInRange("miningSpeed", 2.0D, 0.0D, Float.MAX_VALUE);

    public static ModConfigSpec.ConfigValue<List<? extends String>> BLOCK_IDS = BUILDER
            .comment("List of block IDs where this mod adjusts the mining speed.")
            .defineList("blockIDs", List.of(
                    "minecraft:deepslate",
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
                    "minecraft:deepslate_tile_slab"
            ), () -> "", ServerConfig::isValidIdentifier);

    public static ModConfigSpec.ConfigValue<List<? extends String>> BLOCK_TAGS = BUILDER
            .comment("List of Block Tags (not Item Tags) where this mod adjusts the mining speed.")
            .defineList("blockTags", List.of(
                    "c:cobblestones/deepslate",
                    "c:ores_in_ground/deepslate"
            ), () -> "", ServerConfig::isValidIdentifier);

    private static boolean isValidIdentifier(Object entry) {
        return entry instanceof String s && isValidIdentifier(s);
    }

    private static boolean isValidIdentifier(String entry) {
        return Identifier.tryParse(entry) != null;
    }

    public static final ModConfigSpec SPEC = BUILDER.build();
}
