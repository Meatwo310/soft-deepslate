package net.meatwo310.softdeepslate.config;

import net.meatwo310.softdeepslate.SoftDeepslateConfig;
import net.meatwo310.softdeepslate.SoftDeepslateSettings;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class ServerConfig implements SoftDeepslateSettings {
    public static final ServerConfig INSTANCE = new ServerConfig();

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec.DoubleValue MINING_SPEED = BUILDER
            .comment(SoftDeepslateConfig.MINING_SPEED_COMMENT)
            .defineInRange("miningSpeed", SoftDeepslateConfig.DEFAULT_MINING_SPEED, 0.0D, Float.MAX_VALUE);

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> BLOCKS = BUILDER
            .comment(SoftDeepslateConfig.BLOCKS_COMMENT)
            .defineList("blocks", SoftDeepslateConfig.DEFAULT_BLOCKS, SoftDeepslateConfig::isValidIdOrTag);

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
}
