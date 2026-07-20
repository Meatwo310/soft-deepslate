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
        Constants.LOGGER.debug(Constants.INITIALIZING, ModUtils.id("26.1.2-neo"));
        modEventBus.addListener((ModConfigEvent.Loading event) -> LOGIC.invalidateBlockCache());
        modEventBus.addListener((ModConfigEvent.Reloading event) -> LOGIC.invalidateBlockCache());
        NeoForge.EVENT_BUS.addListener((PlayerEvent.BreakSpeed event) ->
                event.setNewSpeed(LOGIC.adjustDestroySpeed(event.getState(), event.getNewSpeed())));
        NeoForge.EVENT_BUS.addListener((TagsUpdatedEvent event) -> LOGIC.invalidateBlockCache());
        PlatformConfigRegistrar.registerAll(modContainer, VersionedConfigSpec.bindAll(ModConfigs.ALL));
    }
}
