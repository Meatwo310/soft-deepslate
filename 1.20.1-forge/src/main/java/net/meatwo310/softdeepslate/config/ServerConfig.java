package net.meatwo310.softdeepslate.config;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class ServerConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec.DoubleValue MINING_SPEED = BUILDER
            .comment("""
                    Adjusts the mining speed for deepslate.
                    Given that deepslate is twice as hard as stone, a default value of 2.0 allows it to be mined at the same rate as stone.""")
            .defineInRange("miningSpeed", 2.0D, 0.0D, Float.MAX_VALUE);

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> BLOCKS = BUILDER
            .comment("""
                    List of block IDs or block tags (prefix with #) where this mod adjusts the mining speed.
                    `/reload` to apply.""")
            .defineList("blocks", List.of(
                    "minecraft:deepslate",
                    "#forge:cobblestone/deepslate",
                    "#forge:ores_in_ground/deepslate",
                    "#softdeepslate:building_blocks"
            ), ServerConfig::isValidIdOrTag);

    private static boolean isValidIdOrTag(Object entry) {
        return entry instanceof String s && isValidIdOrTag(s);
    }

    private static boolean isValidIdOrTag(String entry) {
        String normalized = entry.startsWith("#") ? entry.substring(1) : entry;
        return ResourceLocation.tryParse(normalized) != null;
    }

    public static final ForgeConfigSpec SPEC = BUILDER.build();
}
