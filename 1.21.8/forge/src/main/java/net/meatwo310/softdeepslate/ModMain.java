package net.meatwo310.softdeepslate;

import net.meatwo310.softdeepslate.config.ModConfigs;
import net.meatwo310.softdeepslate.config.ServerConfig;
import net.meatwo310.softdeepslate.mdk.config.PlatformConfigRegistrar;
import net.meatwo310.softdeepslate.mdk.config.VersionedConfigSpec;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MODID)
public class ModMain {
    private static final SoftDeepslateLogic LOGIC =
            new SoftDeepslateLogic(ServerConfig.MINING_SPEED_MULTIPLIER, ServerConfig.BLOCKS);

    public ModMain(FMLJavaModLoadingContext ctx) {
        Constants.LOGGER.debug(Constants.INITIALIZING, ModUtils.loc("1.21.8-forge"));
        ModConfigEvent.Loading.getBus(ctx.getModBusGroup())
                .addListener((ModConfigEvent.Loading event) -> LOGIC.invalidateBlockCache());
        ModConfigEvent.Reloading.getBus(ctx.getModBusGroup())
                .addListener((ModConfigEvent.Reloading event) -> LOGIC.invalidateBlockCache());
        PlayerEvent.BreakSpeed.BUS.addListener((PlayerEvent.BreakSpeed event) ->
                event.setNewSpeed(LOGIC.adjustDestroySpeed(event.getState(), event.getNewSpeed())));
        TagsUpdatedEvent.BUS.addListener((TagsUpdatedEvent event) -> LOGIC.invalidateBlockCache());
        PlatformConfigRegistrar.registerAll(ctx.getContainer(), VersionedConfigSpec.bindAll(ModConfigs.ALL));
    }
}
