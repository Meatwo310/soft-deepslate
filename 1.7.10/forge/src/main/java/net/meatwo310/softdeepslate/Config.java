package net.meatwo310.softdeepslate;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {

    private static final float DEFAULT_MINING_SPEED_MULTIPLIER = 2.0F;

    public static float miningSpeedMultiplier = DEFAULT_MINING_SPEED_MULTIPLIER;

    private Config() {}

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);
        configuration.load();

        miningSpeedMultiplier = configuration.getFloat(
            "miningSpeedMultiplier",
            Configuration.CATEGORY_GENERAL,
            DEFAULT_MINING_SPEED_MULTIPLIER,
            0.0F,
            Float.MAX_VALUE,
            "Multiplies the mining speed of Et Futurum Requiem deepslate blocks and deepslate ores. "
                + "A value of 2.0 doubles their mining speed.");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
