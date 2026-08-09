package net.meatwo310.softdeepslate;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public final class MiningSpeedHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (DeepslateBlockRegistry.contains(event.block, event.metadata)) {
            event.newSpeed *= Config.miningSpeedMultiplier;
        }
    }
}
