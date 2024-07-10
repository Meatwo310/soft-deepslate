package io.github.meatwo310.softdeepslate.config;

import io.github.meatwo310.softdeepslate.SoftDeepslate;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = SoftDeepslate.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ServerConfigs {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec.DoubleValue multiplierEntry = BUILDER
            .comment("Determines how fast the mining speed will be.\n" +
                    "Note that Deepslate is mined at half the speed of Stone in Vanilla.")
            .defineInRange("multiplier", 2.0D, 0.0D, Float.MAX_VALUE);

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> blockIDsEntry = BUILDER
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

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> blockTags = BUILDER
            .comment("List of Block Tags (not Item Tags) where this mod adjusts the mining speed.")
            .defineList("block_tags", List.of(
                    "forge:ores_in_ground/deepslate"
            ), entry -> true);

    public static final ForgeConfigSpec SPEC = BUILDER.build();
}
