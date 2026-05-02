package net.meatwo310.softdeepslate.config;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class ServerConfig implements ModServerConfig, ModServerConfigValidator {
    public static final ServerConfig INSTANCE = new ServerConfig();

    private static final List<String> DEFAULT_BLOCKS = List.of(
            "minecraft:deepslate",
            "#forge:cobblestone/deepslate",
            "#forge:ores_in_ground/deepslate",
            "#softdeepslate:building_blocks"
    );

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec.DoubleValue MINING_SPEED = BUILDER
            .comment(ModServerConfigEntries.MINING_SPEED.comment())
            .defineInRange(
                    ModServerConfigEntries.MINING_SPEED.key(),
                    ModServerConfigEntries.MINING_SPEED.defaultValue(),
                    ModServerConfigEntries.MINING_SPEED.min(),
                    ModServerConfigEntries.MINING_SPEED.max()
            );

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> BLOCKS = BUILDER
            .comment(ModServerConfigEntries.BLOCKS.comment())
            .defineList(ModServerConfigEntries.BLOCKS.key(), DEFAULT_BLOCKS, INSTANCE::isValidIdOrTag);

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
