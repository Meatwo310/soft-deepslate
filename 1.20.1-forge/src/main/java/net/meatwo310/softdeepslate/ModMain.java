package net.meatwo310.softdeepslate;

import net.meatwo310.softdeepslate.config.ServerConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import net.minecraftforge.registries.tags.ITagManager;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Mod(Constants.MODID)
public class ModMain {
    private static Lazy<Set<Block>> blocksCache = Lazy.of(ModMain::buildCache);

    public ModMain(FMLJavaModLoadingContext ctx) {
        Constants.LOGGER.debug(Constants.INITIALIZING, ModUtils.loc("1.20.1-forge"));
        ctx.registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
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
            Constants.LOGGER.warn("No valid blocks found");
        } else {
            Constants.LOGGER.info("Cached {} blocks", blocks.size());
        }

        return Set.copyOf(blocks);
    }

    private static void cacheBlock(Set<Block> blocks, String blockName) {
        ResourceLocation id = ResourceLocation.tryParse(blockName);
        if (id == null) {
            return;
        }

        Optional.ofNullable(ForgeRegistries.BLOCKS.getValue(id)).ifPresentOrElse(
                block -> {
                    Constants.LOGGER.debug("Caching block: {}{}", blockName, blocks.contains(block) ? " (DUPLICATED)" : "");
                    blocks.add(block);
                },
                () -> Constants.LOGGER.warn("Unknown block in config: {}", blockName)
        );
    }

    private static void cacheBlockTag(Set<Block> blocks, String blockTagName) {
        ResourceLocation id = ResourceLocation.tryParse(blockTagName);
        if (id == null) {
            return;
        }

        ITagManager<Block> tagManager = ForgeRegistries.BLOCKS.tags();
        if (tagManager == null) {
            Constants.LOGGER.warn("Block tag manager is not available: #{}", blockTagName);
            return;
        }

        TagKey<Block> tagKey = tagManager.createTagKey(id);
        if (!tagManager.isKnownTagName(tagKey)) {
            Constants.LOGGER.warn("Unknown block tag in config: #{}", blockTagName);
            return;
        }

        ITag<Block> tag = tagManager.getTag(tagKey);
        for (Block block : tag) {
            ResourceLocation blockId = ForgeRegistries.BLOCKS.getKey(block);
            String blockName = blockId != null ? blockId.toString() : "?";

            Constants.LOGGER.debug(
                    "Caching block from tag #{}: {}{}",
                    blockTagName,
                    blockName,
                    blocks.contains(block) ? " (DUPLICATED)" : ""
            );
            blocks.add(block);
        }
    }

    public static boolean shouldMineFaster(BlockState state) {
        return blocksCache.get().contains(state.getBlock());
    }

    @Mod.EventBusSubscriber(modid = Constants.MODID)
    public static class Subscriber {
        @SubscribeEvent
        public static void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event) {
            if (shouldMineFaster(event.getState())) {
                event.setNewSpeed((float) (event.getNewSpeed() * ServerConfig.MINING_SPEED.get()));
            }
        }

        @SubscribeEvent
        public static void onTagsUpdated(TagsUpdatedEvent event) {
            blocksCache = Lazy.of(ModMain::buildCache);
        }
    }
}
