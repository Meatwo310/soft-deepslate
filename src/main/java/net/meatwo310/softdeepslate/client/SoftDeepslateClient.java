package net.meatwo310.softdeepslate.client;

import net.meatwo310.softdeepslate.SoftDeepslate;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = SoftDeepslate.MODID, dist = Dist.CLIENT)
public class SoftDeepslateClient {
    public SoftDeepslateClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
