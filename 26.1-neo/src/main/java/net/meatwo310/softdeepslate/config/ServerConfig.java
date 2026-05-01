package net.meatwo310.softdeepslate.config;

import net.meatwo310.mdk.config.ConfigListEntry;
import net.meatwo310.mdk.config.ConfigRangeEntry;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class ServerConfig implements ModServerConfig, ModServerConfigValidator {
    public static final ServerConfig INSTANCE = new ServerConfig();

    private static final ConfigRangeEntry<Double> MINING_SPEED_ENTRY = ModServerConfigEntries.MINING_SPEED;
    private static final ConfigListEntry<String> BLOCKS_ENTRY = ModServerConfigEntries.BLOCKS;

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static ModConfigSpec.DoubleValue MINING_SPEED = BUILDER
            .comment(MINING_SPEED_ENTRY.comment())
            .defineInRange(
                    MINING_SPEED_ENTRY.key(),
                    MINING_SPEED_ENTRY.defaultValue(),
                    MINING_SPEED_ENTRY.min(),
                    MINING_SPEED_ENTRY.max()
            );

    public static ModConfigSpec.ConfigValue<List<? extends String>> BLOCKS = BUILDER
            .comment(BLOCKS_ENTRY.comment())
            .defineList(
                    BLOCKS_ENTRY.key(),
                    BLOCKS_ENTRY.defaultValue(),
                    BLOCKS_ENTRY::newElementValue,
                    INSTANCE::isValidIdOrTag
            );

    public static final ModConfigSpec SPEC = BUILDER.build();

    private ServerConfig() {}

    @Override
    public double miningSpeed() {
        return MINING_SPEED.get();
    }

    @Override
    public List<? extends String> blocks() {
        return BLOCKS.get();
    }

    @Override
    public boolean isValidId(String entry) {
        return Identifier.tryParse(entry) != null;
    }
}
