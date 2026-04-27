package net.meatwo310.softdeepslate;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(Constants.MODID)
public class ModMain {
    public static final Logger LOGGER = LogUtils.getLogger();

    public ModMain() {
        LOGGER.debug(Constants.INITIALIZING, ModUtils.loc("1.18.2-forge"));
//        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
    }
}
