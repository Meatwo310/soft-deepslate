package net.meatwo310.examplemod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(Constants.MODID);

    @Override
    public void onInitialize() {
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Constants.MODID, path);
    }
}
