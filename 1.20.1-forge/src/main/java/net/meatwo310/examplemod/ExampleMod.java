package net.meatwo310.examplemod;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ExampleMod.MODID)
public class ExampleMod {
    public static final String MODID = "examplemod";

    public ExampleMod(FMLJavaModLoadingContext ctx) {
//        ctx.registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
    }

    /// Utility method to create a [ResourceLocation] with the namespace of this mod.
    /// @param path Path of the resource. Example: `"example_item"`
    /// @return [ResourceLocation] with the namespace of this mod and the given path. Example: `examplemod:example_item`
    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
