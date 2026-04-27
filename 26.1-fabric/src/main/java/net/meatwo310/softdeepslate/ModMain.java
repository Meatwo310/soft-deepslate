package net.meatwo310.softdeepslate;

import net.fabricmc.api.ModInitializer;

public class ModMain implements ModInitializer {
    @Override
    public void onInitialize() {
        Constants.LOGGER.debug(Constants.INITIALIZING, ModUtils.id("26.1-fabric"));
    }
}
