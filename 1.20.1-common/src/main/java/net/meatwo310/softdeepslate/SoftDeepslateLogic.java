package net.meatwo310.softdeepslate;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public final class SoftDeepslateLogic {
    private final DoubleSupplier miningSpeed;
    private final Supplier<? extends Iterable<String>> blocks;
    private final BlockResolver blockResolver;
    private volatile Set<Block> blocksCache;

    public SoftDeepslateLogic(
            DoubleSupplier miningSpeed,
            Supplier<? extends Iterable<String>> blocks,
            BlockResolver blockResolver
    ) {
        this.miningSpeed = Objects.requireNonNull(miningSpeed);
        this.blocks = Objects.requireNonNull(blocks);
        this.blockResolver = Objects.requireNonNull(blockResolver);
    }

    public boolean shouldMineFaster(BlockState state) {
        return getBlocks().contains(state.getBlock());
    }

    public double miningSpeed() {
        return miningSpeed.getAsDouble();
    }

    public void invalidateBlockCache() {
        blocksCache = null;
    }

    private Set<Block> getBlocks() {
        Set<Block> cached = blocksCache;
        if (cached == null) {
            synchronized (this) {
                cached = blocksCache;
                if (cached == null) {
                    cached = buildCache();
                    blocksCache = cached;
                }
            }
        }
        return cached;
    }

    private Set<Block> buildCache() {
        HashSet<Block> resolvedBlocks = new HashSet<>();

        for (String name : blocks.get()) {
            if (name.startsWith("#")) {
                cacheBlockTag(resolvedBlocks, name.substring(1));
            } else {
                cacheBlock(resolvedBlocks, name);
            }
        }

        if (resolvedBlocks.isEmpty()) {
            Constants.LOGGER.warn("No valid blocks found");
        } else {
            Constants.LOGGER.info("Cached {} blocks", resolvedBlocks.size());
        }

        return Set.copyOf(resolvedBlocks);
    }

    private void cacheBlock(Set<Block> blocks, String blockName) {
        ResourceLocation id = ResourceLocation.tryParse(blockName);
        if (id == null) {
            return;
        }

        blockResolver.resolveBlock(id).ifPresentOrElse(
                block -> {
                    Constants.LOGGER.debug("Caching block: {}{}", blockName, blocks.contains(block) ? " (DUPLICATED)" : "");
                    blocks.add(block);
                },
                () -> Constants.LOGGER.warn("Unknown block in config: {}", blockName)
        );
    }

    private void cacheBlockTag(Set<Block> blocks, String blockTagName) {
        ResourceLocation id = ResourceLocation.tryParse(blockTagName);
        if (id == null) {
            return;
        }

        blockResolver.resolveTag(id).ifPresentOrElse(
                taggedBlocks -> {
                    for (Block block : taggedBlocks) {
                        Constants.LOGGER.debug(
                                "Caching block from tag #{}: {}{}",
                                blockTagName,
                                blockResolver.blockName(block),
                                blocks.contains(block) ? " (DUPLICATED)" : ""
                        );
                        blocks.add(block);
                    }
                },
                () -> Constants.LOGGER.warn("Unknown block tag in config: #{}", blockTagName)
        );
    }

    public interface BlockResolver {
        Optional<Block> resolveBlock(ResourceLocation id);
        Optional<? extends Iterable<Block>> resolveTag(ResourceLocation id);
        String blockName(Block block);
    }
}
