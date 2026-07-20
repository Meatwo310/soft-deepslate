package net.meatwo310.softdeepslate;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.meatwo310.softdeepslate.config.ModConfigs;
import net.meatwo310.softdeepslate.config.ServerConfig;
import net.meatwo310.softdeepslate.mdk.config.PlatformConfigRegistrar;
import net.meatwo310.softdeepslate.mdk.config.VersionedConfigSpec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.fml.event.config.ModConfigEvent;

public class ModMain implements ModInitializer {
    private static final SoftDeepslateLogic LOGIC =
            new SoftDeepslateLogic(ServerConfig.MINING_SPEED_MULTIPLIER, ServerConfig.BLOCKS);

    @Override
    public void onInitialize() {
        Constants.LOGGER.debug(Constants.INITIALIZING, ModUtils.loc("1.18.2-fabric"));
        ModConfigEvent.LOADING.register(config -> {
            if (Constants.MODID.equals(config.getModId())) {
                LOGIC.invalidateBlockCache();
            }
        });
        ModConfigEvent.RELOADING.register(config -> {
            if (Constants.MODID.equals(config.getModId())) {
                LOGIC.invalidateBlockCache();
            }
        });
        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> LOGIC.invalidateBlockCache());
        PlatformConfigRegistrar.registerAll(Constants.MODID, VersionedConfigSpec.bindAll(ModConfigs.ALL));
    }

    public static float adjustDestroySpeed(BlockState state, float currentSpeed) {
        return LOGIC.adjustDestroySpeed(state, currentSpeed);
    }
}
