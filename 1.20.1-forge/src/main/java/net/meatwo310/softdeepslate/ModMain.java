package net.meatwo310.softdeepslate;

import net.meatwo310.softdeepslate.config.ServerConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import net.minecraftforge.registries.tags.ITagManager;

import java.util.Optional;

@Mod(Constants.MODID)
public class ModMain {
    private static SoftDeepslateLogic logic;

    public ModMain(FMLJavaModLoadingContext ctx) {
        Constants.LOGGER.debug(Constants.INITIALIZING, ModUtils.loc("1.20.1-forge"));
        logic = new SoftDeepslateLogic(ServerConfig.INSTANCE, new ForgeBlockResolver());
        ctx.registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
    }

    @Mod.EventBusSubscriber(modid = Constants.MODID)
    public static class Subscriber {
        @SubscribeEvent
        public static void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event) {
            SoftDeepslateLogic logic = logic();
            if (logic.shouldMineFaster(event.getState())) {
                event.setNewSpeed((float) (event.getNewSpeed() * logic.miningSpeed()));
            }
        }

        @SubscribeEvent
        public static void onTagsUpdated(TagsUpdatedEvent event) {
            logic().invalidateBlockCache();
        }
    }

    private static SoftDeepslateLogic logic() {
        if (logic == null) {
            throw new IllegalStateException("SoftDeepslateLogic has not been initialized");
        }
        return logic;
    }

    private static class ForgeBlockResolver implements SoftDeepslateLogic.BlockResolver {
        @Override
        public Optional<Block> resolveBlock(ResourceLocation id) {
            return Optional.ofNullable(ForgeRegistries.BLOCKS.getValue(id));
        }

        @Override
        public Optional<ITag<Block>> resolveTag(ResourceLocation id) {
            ITagManager<Block> tagManager = ForgeRegistries.BLOCKS.tags();
            if (tagManager == null) {
                Constants.LOGGER.warn("Block tag manager is not available: #{}", id);
                return Optional.empty();
            }

            TagKey<Block> tagKey = tagManager.createTagKey(id);
            if (!tagManager.isKnownTagName(tagKey)) {
                return Optional.empty();
            }

            return Optional.of(tagManager.getTag(tagKey));
        }

        @Override
        public String blockName(Block block) {
            ResourceLocation id = ForgeRegistries.BLOCKS.getKey(block);
            return id != null ? id.toString() : "?";
        }
    }
}
