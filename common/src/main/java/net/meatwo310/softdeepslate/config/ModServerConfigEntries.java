package net.meatwo310.softdeepslate.config;

import net.meatwo310.mdk.config.ConfigListEntry;
import net.meatwo310.mdk.config.ConfigRangeEntry;

import java.util.List;

public final class ModServerConfigEntries {
    public static final ConfigRangeEntry<Double> MINING_SPEED = new ConfigRangeEntry<>(
            "miningSpeed",
            2.0D,
            0.0D,
            (double) Float.MAX_VALUE,
            """
                    Adjusts the mining speed for deepslate.
                    Given that deepslate is twice as hard as stone, a default value of 2.0 allows it to be mined at the same rate as stone."""
    );

    public static final ConfigListEntry<String> BLOCKS = new ConfigListEntry<>(
            "blocks",
            List.of(
                    "minecraft:deepslate",
                    "#c:cobblestones/deepslate",
                    "#c:ores_in_ground/deepslate",
                    "#softdeepslate:building_blocks"
            ),
            "",
            """
                    List of block IDs or block tags (prefix with #) where this mod adjusts the mining speed.
                    `/reload` to apply."""
    );

    private ModServerConfigEntries() {}
}
