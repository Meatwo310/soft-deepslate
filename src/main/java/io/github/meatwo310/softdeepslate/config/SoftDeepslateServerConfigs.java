package io.github.meatwo310.softdeepslate.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class SoftDeepslateServerConfigs {
    public static final ForgeConfigSpec SERVER_SPEC;
    public static ForgeConfigSpec.DoubleValue multiplierEntry;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> blockIDsEntry;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> blockTags;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        multiplierEntry = builder
                .comment("Determines how fast the mining speed will be.\n" +
                        "Note that Deepslate is mined at half the speed of Stone in Vanilla.")
                .defineInRange("multiplier", 2.0D, 0.0D, Float.MAX_VALUE);

        blockIDsEntry = builder
                .comment("List of block IDs where this mod adjusts the mining speed.")
                .defineList("block_ids", List.of(
                        "minecraft:deepslate",
                        "minecraft:cobbled_deepslate",
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
                ), entry -> true);
        blockTags = builder
                .comment("List of Block Tags (not Item Tags) where this mod adjusts the mining speed.")
                .defineList("block_tags", List.of(
                        "forge:ores_in_ground/deepslate"
                ), entry -> true);

        SERVER_SPEC = builder.build();
    }
}
