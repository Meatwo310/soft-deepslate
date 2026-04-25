package net.meatwo310.examplemod;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

@Mod(Constants.MODID)
public class ExampleMod {
    public ExampleMod() {
//        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(Constants.MODID, path);
    }
}
