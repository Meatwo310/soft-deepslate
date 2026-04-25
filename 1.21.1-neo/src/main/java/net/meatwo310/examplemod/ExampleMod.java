package net.meatwo310.examplemod;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(Constants.MODID)
public class ExampleMod {
    public static final Logger LOGGER = LogUtils.getLogger();

    public ExampleMod(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.debug(Constants.INITIALIZING, ModUtils.loc("1.21.1-neo"));
//        modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
    }
}
