package net.meatwo310.examplemod;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(Constants.MODID)
public class ExampleMod {
    public static final Logger LOGGER = LogUtils.getLogger();

    public ExampleMod() {
        LOGGER.debug(Constants.INITIALIZING, ModUtils.loc("1.19.2-forge"));
//        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
    }
}
