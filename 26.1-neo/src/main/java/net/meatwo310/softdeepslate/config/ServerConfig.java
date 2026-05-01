package net.meatwo310.softdeepslate.config;

import net.meatwo310.softdeepslate.SoftDeepslateConfig;
import net.meatwo310.softdeepslate.SoftDeepslateSettings;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class ServerConfig implements SoftDeepslateSettings {
    public static final ServerConfig INSTANCE = new ServerConfig();

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static ModConfigSpec.DoubleValue MINING_SPEED = BUILDER
            .comment(SoftDeepslateConfig.MINING_SPEED_COMMENT)
            .defineInRange("miningSpeed", SoftDeepslateConfig.DEFAULT_MINING_SPEED, 0.0D, Float.MAX_VALUE);

    public static ModConfigSpec.ConfigValue<List<? extends String>> BLOCKS = BUILDER
            .comment(SoftDeepslateConfig.BLOCKS_COMMENT)
            .defineList("blocks", SoftDeepslateConfig.DEFAULT_BLOCKS, () -> "", SoftDeepslateConfig::isValidIdOrTag);

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
}
