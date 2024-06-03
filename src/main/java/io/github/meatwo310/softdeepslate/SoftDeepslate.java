package io.github.meatwo310.softdeepslate;

import com.mojang.logging.LogUtils;
import io.github.meatwo310.softdeepslate.config.SoftDeepslateServerConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("softdeepslate")
public class SoftDeepslate {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final HashSet<String> blocksCache = new HashSet<>();

    public SoftDeepslate() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SoftDeepslateServerConfigs.SERVER_SPEC);
    }

    @Mod.EventBusSubscriber(modid = "softdeepslate")
    public static class ModEventSubscriber {
        @SubscribeEvent
        public static void onBlockBreakSpeed(PlayerEvent.BreakSpeed event) {
            String blockID = Objects.requireNonNullElse(event.getState().getBlock().getRegistryName(), "").toString();

            if (blocksCache.contains(blockID)) {
                event.setNewSpeed((float) (event.getOriginalSpeed() * SoftDeepslateServerConfigs.multiplierEntry.get()));
//                event.getPlayer().playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
            }
        }

        @SubscribeEvent
        public static void onUpdateTags(TagsUpdatedEvent event) {
            try {
                reload();
                LOGGER.info("Tags reloaded: {} blocks cached", blocksCache.size());
            } catch (Exception e) {
                LOGGER.error("Failed to reload tags: ", e);
            }
        }
    }

    public static void reload() {
        List<? extends String> blockIDs = SoftDeepslateServerConfigs.blockIDsEntry.get();
        List<? extends String> blockTags = SoftDeepslateServerConfigs.blockTags.get();

        blocksCache.clear();
        blocksCache.addAll(blockIDs);

        // 設定されたタグからブロックを取得して、IDをキャッシュに保存
        blockTags.stream()
                .map(id -> TagKey.create(ForgeRegistries.Keys.BLOCKS, new ResourceLocation(id)))
                .map(tagKey -> Objects.requireNonNull(ForgeRegistries.BLOCKS.tags()).getTag(tagKey).stream()
                        .map(Block::getRegistryName)
                        .filter(Objects::nonNull)
                        .map(ResourceLocation::toString)
                        .toList()
                ).forEach(blocksCache::addAll);
    }
}
