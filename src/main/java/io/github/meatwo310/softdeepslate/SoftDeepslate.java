package io.github.meatwo310.softdeepslate;

import com.mojang.logging.LogUtils;
import io.github.meatwo310.softdeepslate.config.SoftDeepslateCommonConfigs;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

import java.util.Objects;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("softdeepslate")
public class SoftDeepslate {
    private static final Logger LOGGER = LogUtils.getLogger();

    public SoftDeepslate() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SoftDeepslateCommonConfigs.COMMON_SPEC);
    }

    @Mod.EventBusSubscriber(modid = "softdeepslate")
    public static class ModEventSubscriber {
        @SubscribeEvent
        public static void onBlockBreakSpeed(PlayerEvent.BreakSpeed event) {
            final var blockIDs = SoftDeepslateCommonConfigs.blockIDsEntry.get();
            final var blockTags = SoftDeepslateCommonConfigs.blockTags.get();
            final var blockID = Objects.requireNonNullElse(event.getState().getBlock().getRegistryName(), "").toString();

            if (blockIDs.stream().anyMatch(block -> block.equals(blockID)) ||
                    blockTags.stream().anyMatch(tag -> event.getState().getTags().anyMatch(t -> t.location().toString().equals(tag)))) {
                event.setNewSpeed(event.getOriginalSpeed() * SoftDeepslateCommonConfigs.multiplierEntry.get());
            }
        }
    }
}
