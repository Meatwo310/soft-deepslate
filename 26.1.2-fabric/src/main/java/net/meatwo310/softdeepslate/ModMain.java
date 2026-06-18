package net.meatwo310.softdeepslate;

import net.fabricmc.api.ModInitializer;
import net.meatwo310.softdeepslate.config.ModConfigs;
import net.meatwo310.softdeepslate.mdk.config.PlatformConfigRegistrar;
import net.meatwo310.softdeepslate.mdk.config.VersionedConfigSpec;

public class ModMain implements ModInitializer {
    @Override
    public void onInitialize() {
        Constants.LOGGER.debug(Constants.INITIALIZING, ModUtils.id("26.1.2-fabric"));
        PlatformConfigRegistrar.registerAll(Constants.MODID, VersionedConfigSpec.bindAll(ModConfigs.ALL));
    }
}
