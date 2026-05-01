package net.meatwo310.softdeepslate;

import net.minecraft.resources.Identifier;

import java.util.List;

public final class SoftDeepslateConfig {
    private SoftDeepslateConfig() {}

    public static final double DEFAULT_MINING_SPEED = 2.0D;

    public static final String MINING_SPEED_COMMENT = """
            Adjusts the mining speed for deepslate.
            Given that deepslate is twice as hard as stone, a default value of 2.0 allows it to be mined at the same rate as stone.""";

    public static final String BLOCKS_COMMENT = """
            List of block IDs or block tags (prefix with #) where this mod adjusts the mining speed.
            `/reload` to apply.""";

    public static final List<String> DEFAULT_BLOCKS = List.of(
            "minecraft:deepslate",
            "#c:cobblestones/deepslate",
            "#c:ores_in_ground/deepslate",
            "#softdeepslate:building_blocks"
    );

    public static boolean isValidIdOrTag(Object entry) {
        return entry instanceof String s && isValidIdOrTag(s);
    }

    public static boolean isValidIdOrTag(String entry) {
        String normalized = entry.startsWith("#") ? entry.substring(1) : entry;
        return Identifier.tryParse(normalized) != null;
    }
}
