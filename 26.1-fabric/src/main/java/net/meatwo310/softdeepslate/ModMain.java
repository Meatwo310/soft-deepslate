package net.meatwo310.softdeepslate;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModMain implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(Constants.MODID);

    @Override
    public void onInitialize() {
        LOGGER.debug(Constants.INITIALIZING, ModUtils.id("26.1-fabric"));
    }
}
