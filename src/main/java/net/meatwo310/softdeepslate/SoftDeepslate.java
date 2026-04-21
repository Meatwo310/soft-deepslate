package net.meatwo310.softdeepslate;

import net.meatwo310.softdeepslate.config.ServerConfig;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(SoftDeepslate.MODID)
public class SoftDeepslate {
    public static final String MODID = "softdeepslate";

    public SoftDeepslate(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
    }

    /// Utility method to create a [Identifier] with the namespace of this mod.
    /// @param path Path of the resource. Example: `"example_item"`
    /// @return [Identifier] with the namespace of this mod and the given path. Example: `examplemod:example_item`
    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }
}
