package net.meatwo310.softdeepslate;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Constants.MODID)
public class ModMain {
    public static final Logger LOGGER = LogUtils.getLogger();

    public ModMain(FMLJavaModLoadingContext ctx) {
        LOGGER.debug(Constants.INITIALIZING, ModUtils.loc("1.20.1-forge"));
//        ctx.registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
    }
}
