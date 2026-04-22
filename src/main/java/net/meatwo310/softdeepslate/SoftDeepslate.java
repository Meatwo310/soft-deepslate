package net.meatwo310.softdeepslate;

import com.mojang.logging.LogUtils;
import net.meatwo310.softdeepslate.config.ServerConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.Set;

@Mod(SoftDeepslate.MODID)
public class SoftDeepslate {
    public static final String MODID = "softdeepslate";
    public static final Logger LOGGER = LogUtils.getLogger();

    private static final Lazy<Set<Block>> blocksCache = Lazy.of(SoftDeepslate::buildCache);

    public SoftDeepslate(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }

    private static Set<Block> buildCache() {
        HashSet<Block> blocks = new HashSet<>();

        for (String name : ServerConfig.BLOCKS.get()) {
            if (name.startsWith("#")) {
                cacheBlockTag(blocks, name.substring(1));
            } else {
                cacheBlock(blocks, name);
            }
        }

        if (blocks.isEmpty()) {
            LOGGER.warn("No valid blocks found");
        } else {
            LOGGER.info("Cached {} blocks", blocks.size());
        }

        return Set.copyOf(blocks);
    }

    private static void cacheBlock(Set<Block> blocks, String blockName) {
        Identifier id = Identifier.tryParse(blockName);
        if (id == null) {
            return;
        }

        BuiltInRegistries.BLOCK.getOptional(id).ifPresentOrElse(
                block -> {
                    LOGGER.debug("Caching block: {}{}", blockName, blocks.contains(block) ? " (DUPLICATED)" : "");
                    blocks.add(block);
                },
                () -> LOGGER.warn("Unknown block in config: {}", blockName)
        );
    }

    private static void cacheBlockTag(Set<Block> blocks, String blockTagName) {
        Identifier id = Identifier.tryParse(blockTagName);
        if (id == null) {
            return;
        }

        TagKey<Block> tagKey = TagKey.create(Registries.BLOCK, id);
        BuiltInRegistries.BLOCK.get(tagKey).ifPresentOrElse(
                holders -> holders.forEach(holder -> {
                    Block block = holder.value();
                    String blockName = holder.unwrapKey()
                            .map(ResourceKey::identifier)
                            .map(Identifier::toString)
                            .orElse("?");
                    LOGGER.debug("Caching block from tag #{}: {}{}", blockTagName, blockName, blocks.contains(block) ? " (DUPLICATED)" : "");
                    blocks.add(block);
                }),
                () -> LOGGER.warn("Unknown block tag in config: #{}", blockTagName)
        );
    }

    public static boolean shouldMineFaster(BlockState state) {
        return blocksCache.get().contains(state.getBlock());
    }

    @EventBusSubscriber(modid = MODID)
    public static class Subscriber {
        @SubscribeEvent
        public static void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event) {
            if (shouldMineFaster(event.getState())) {
                event.setNewSpeed((float) (event.getNewSpeed() * ServerConfig.MINING_SPEED.get()));
            }
        }

        @SubscribeEvent
        public static void onTagsUpdated(TagsUpdatedEvent event) {
            blocksCache.invalidate();
        }
    }
}
