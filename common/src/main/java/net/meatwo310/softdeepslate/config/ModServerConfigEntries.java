package net.meatwo310.softdeepslate.config;

import java.util.List;

public final class ModServerConfigEntries {
    public static final ConfigDoubleEntry MINING_SPEED = new ConfigDoubleEntry(
            "miningSpeed",
            2.0D,
            0.0D,
            Float.MAX_VALUE,
            """
                    Adjusts the mining speed for deepslate.
                    Given that deepslate is twice as hard as stone, a default value of 2.0 allows it to be mined at the same rate as stone."""
    );

    public static final ConfigStringListEntry BLOCKS = new ConfigStringListEntry(
            "blocks",
            List.of(
                    "minecraft:deepslate",
                    "#c:cobblestones/deepslate",
                    "#c:ores_in_ground/deepslate",
                    "#softdeepslate:building_blocks"
            ),
            """
                    List of block IDs or block tags (prefix with #) where this mod adjusts the mining speed.
                    `/reload` to apply."""
    );

    private ModServerConfigEntries() {}
}
