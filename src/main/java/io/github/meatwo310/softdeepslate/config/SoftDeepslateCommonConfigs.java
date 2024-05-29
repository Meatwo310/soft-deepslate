package io.github.meatwo310.softdeepslate.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class SoftDeepslateCommonConfigs {
    public static final ForgeConfigSpec COMMON_SPEC;
    public static ForgeConfigSpec.ConfigValue<Float> multiplierEntry;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> blockIDsEntry;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> blockTags;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("Common");
        multiplierEntry = builder
                .comment("Determines how fast the mining speed will be.\n" +
                        "Note that Deepslate is mined at half the speed of Stone in Vanilla.")
                .define("multiplier", 2.0F, o -> o instanceof Float);
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
                ), o -> o instanceof String);
        blockTags = builder
                .comment("List of Block Tags (not Item Tags) where this mod adjusts the mining speed.")
                .defineList("block_tags", List.of(
                        "forge:ores_in_ground/deepslate"
                ), o -> o instanceof String);
        builder.pop();

        COMMON_SPEC = builder.build();
    }
}
