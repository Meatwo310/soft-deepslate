package net.meatwo310.softdeepslate;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.meatwo310.softdeepslate.config.ModConfigs;
import net.meatwo310.softdeepslate.config.ServerConfig;
import net.meatwo310.softdeepslate.mdk.config.PlatformConfigRegistrar;
import net.meatwo310.softdeepslate.mdk.config.VersionedConfigSpec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class ModMain implements ModInitializer {
    private static SoftDeepslateLogic logic;

    @Override
    public void onInitialize() {
        Constants.LOGGER.debug(Constants.INITIALIZING, ModUtils.id("26.2-fabric"));
        logic = new SoftDeepslateLogic(ServerConfig.MINING_SPEED, ServerConfig.BLOCKS, new FabricBlockResolver());
        PlatformConfigRegistrar.registerAll(Constants.MODID, VersionedConfigSpec.bindAll(ModConfigs.ALL));
        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> logic().invalidateBlockCache());
    }

    public static float adjustDestroySpeed(BlockState state, float speed) {
        SoftDeepslateLogic logic = logic();
        if (!logic.shouldMineFaster(state)) {
            return speed;
        }
        return (float) (speed * logic.miningSpeed());
    }

    private static SoftDeepslateLogic logic() {
        if (logic == null) {
            throw new IllegalStateException("SoftDeepslateLogic has not been initialized");
        }
        return logic;
    }

    private static class FabricBlockResolver implements SoftDeepslateLogic.BlockResolver {
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
