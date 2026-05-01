package net.meatwo310.softdeepslate;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class SoftDeepslateLogic {
    private final IModServerConfig config;
    private final BlockResolver blockResolver;
    private volatile Set<Block> blocksCache;

    public SoftDeepslateLogic(IModServerConfig config, BlockResolver blockResolver) {
        this.config = Objects.requireNonNull(config);
        this.blockResolver = Objects.requireNonNull(blockResolver);
    }

    public boolean shouldMineFaster(BlockState state) {
        return getBlocks().contains(state.getBlock());
    }

    public double miningSpeed() {
        return config.miningSpeed();
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
        HashSet<Block> blocks = new HashSet<>();

        for (String name : config.blocks()) {
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

    private void cacheBlock(Set<Block> blocks, String blockName) {
        Identifier id = Identifier.tryParse(blockName);
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
        Identifier id = Identifier.tryParse(blockTagName);
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
        Optional<Block> resolveBlock(Identifier id);

        Optional<? extends Iterable<Block>> resolveTag(Identifier id);

        String blockName(Block block);
    }
}
