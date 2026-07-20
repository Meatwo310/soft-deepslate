package net.meatwo310.softdeepslate;

import net.meatwo310.softdeepslate.config.ModConfigs;
import net.meatwo310.softdeepslate.config.ServerConfig;
import net.meatwo310.softdeepslate.mdk.config.PlatformConfigRegistrar;
import net.meatwo310.softdeepslate.mdk.config.VersionedConfigSpec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@Mod(Constants.MODID)
public class ModMain {
    private static final SoftDeepslateLogic LOGIC =
            new SoftDeepslateLogic(ServerConfig.MINING_SPEED_MULTIPLIER, ServerConfig.BLOCKS);

    public ModMain(IEventBus modEventBus, ModContainer modContainer) {
        Constants.LOGGER.debug(Constants.INITIALIZING, ModUtils.id("26.2-neo"));
        modEventBus.addListener(ModMain::onConfigLoading);
        modEventBus.addListener(ModMain::onConfigReloading);
        NeoForge.EVENT_BUS.addListener(ModMain::onPlayerBreakSpeed);
        NeoForge.EVENT_BUS.addListener(ModMain::onTagsUpdated);
        PlatformConfigRegistrar.registerAll(modContainer, VersionedConfigSpec.bindAll(ModConfigs.ALL));
    }

    private static void onConfigLoading(ModConfigEvent.Loading event) {
        LOGIC.invalidateBlockCache();
    }

    private static void onConfigReloading(ModConfigEvent.Reloading event) {
        LOGIC.invalidateBlockCache();
    }

    private static void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event) {
        event.setNewSpeed(LOGIC.adjustDestroySpeed(event.getState(), event.getNewSpeed()));
    }

    private static void onTagsUpdated(TagsUpdatedEvent event) {
        LOGIC.invalidateBlockCache();
    }
}
