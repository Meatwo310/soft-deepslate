package net.meatwo310.softdeepslate;

import net.meatwo310.softdeepslate.config.ServerConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.Optional;

@Mod(Constants.MODID)
public class ModMain {
    private static SoftDeepslateLogic logic;

    public ModMain(IEventBus modEventBus, ModContainer modContainer) {
        Constants.LOGGER.debug(Constants.INITIALIZING, ModUtils.id("26.1-neo"));
        logic = new SoftDeepslateLogic(ServerConfig.INSTANCE, new NeoBlockResolver());
        modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
    }

    @EventBusSubscriber(modid = Constants.MODID)
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

    private class NeoBlockResolver implements SoftDeepslateLogic.BlockResolver {
        @Override
        public Optional<Block> resolveBlock(Identifier id) {
            return BuiltInRegistries.BLOCK.getOptional(id);
        }

        @Override
        public Optional<? extends Iterable<Block>> resolveTag(Identifier id) {
            TagKey<Block> tagKey = TagKey.create(Registries.BLOCK, id);
            return BuiltInRegistries.BLOCK.get(tagKey).map(holders -> holders.stream()
                    .map(holder -> holder.value())
                    .toList());
        }

        @Override
        public String blockName(Block block) {
            return BuiltInRegistries.BLOCK.getResourceKey(block)
                    .map(ResourceKey::identifier)
                    .map(Identifier::toString)
                    .orElse("?");
        }
    }
}
