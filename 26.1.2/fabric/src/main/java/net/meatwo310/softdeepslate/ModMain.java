package net.meatwo310.softdeepslate;

import fuzs.forgeconfigapiport.fabric.api.v5.ModConfigEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.meatwo310.softdeepslate.config.ModConfigs;
import net.meatwo310.softdeepslate.config.ServerConfig;
import net.meatwo310.softdeepslate.mdk.config.PlatformConfigRegistrar;
import net.meatwo310.softdeepslate.mdk.config.VersionedConfigSpec;
import net.minecraft.world.level.block.state.BlockState;

public class ModMain implements ModInitializer {
    private static final SoftDeepslateLogic LOGIC =
            new SoftDeepslateLogic(ServerConfig.MINING_SPEED_MULTIPLIER, ServerConfig.BLOCKS);

    @Override
    public void onInitialize() {
        Constants.LOGGER.debug(Constants.INITIALIZING, ModUtils.id("26.1.2-fabric"));
        ModConfigEvents.loading(Constants.MODID).register(config -> LOGIC.invalidateBlockCache());
        ModConfigEvents.reloading(Constants.MODID).register(config -> LOGIC.invalidateBlockCache());
        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> LOGIC.invalidateBlockCache());
        PlatformConfigRegistrar.registerAll(Constants.MODID, VersionedConfigSpec.bindAll(ModConfigs.ALL));
    }

    public static float adjustDestroySpeed(BlockState state, float currentSpeed) {
        return LOGIC.adjustDestroySpeed(state, currentSpeed);
    }
}
