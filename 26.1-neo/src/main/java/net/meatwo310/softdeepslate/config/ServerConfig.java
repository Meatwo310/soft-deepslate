package net.meatwo310.softdeepslate.config;

import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class ServerConfig implements ModServerConfig, ModServerConfigValidator {
    public static final ServerConfig INSTANCE = new ServerConfig();

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static ModConfigSpec.DoubleValue MINING_SPEED = BUILDER
            .comment(ModServerConfigEntries.MINING_SPEED.comment())
            .defineInRange(
                    ModServerConfigEntries.MINING_SPEED.key(),
                    ModServerConfigEntries.MINING_SPEED.defaultValue(),
                    ModServerConfigEntries.MINING_SPEED.min(),
                    ModServerConfigEntries.MINING_SPEED.max()
            );

    public static ModConfigSpec.ConfigValue<List<? extends String>> BLOCKS = BUILDER
            .comment(ModServerConfigEntries.BLOCKS.comment())
            .defineList(
                    ModServerConfigEntries.BLOCKS.key(),
                    ModServerConfigEntries.BLOCKS.defaultValue(),
                    ModServerConfigEntries.BLOCKS::newElementValue,
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
