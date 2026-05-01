package net.meatwo310.softdeepslate.config;

import net.meatwo310.softdeepslate.IModServerConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class ServerConfig implements IModServerConfig, IModServerConfigValidator {
    public static final ServerConfig INSTANCE = new ServerConfig();

    private static final ConfigDoubleEntry MINING_SPEED_ENTRY = ModServerConfigEntries.MINING_SPEED;
    private static final ConfigStringListEntry BLOCKS_ENTRY = ModServerConfigEntries.BLOCKS.withDefaultValue(List.of(
            "minecraft:deepslate",
            "#forge:cobblestone/deepslate",
            "#forge:ores_in_ground/deepslate",
            "#softdeepslate:building_blocks"
    ));

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec.DoubleValue MINING_SPEED = BUILDER
            .comment(MINING_SPEED_ENTRY.comment())
            .defineInRange(
                    MINING_SPEED_ENTRY.key(),
                    MINING_SPEED_ENTRY.defaultValue(),
                    MINING_SPEED_ENTRY.min(),
                    MINING_SPEED_ENTRY.max()
            );

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> BLOCKS = BUILDER
            .comment(BLOCKS_ENTRY.comment())
            .defineList(BLOCKS_ENTRY.key(), BLOCKS_ENTRY.defaultValue(), INSTANCE::isValidIdOrTag);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

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
        return ResourceLocation.tryParse(entry) != null;
    }
}
