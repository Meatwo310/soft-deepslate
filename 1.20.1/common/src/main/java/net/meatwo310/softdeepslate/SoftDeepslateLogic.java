package net.meatwo310.softdeepslate;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public final class SoftDeepslateLogic {
    private final DoubleSupplier miningSpeedMultiplier;
    private final ConfiguredBlockSet<Block> blocks;

    public SoftDeepslateLogic(
            DoubleSupplier miningSpeedMultiplier,
            Supplier<? extends Iterable<String>> blockSelectors
    ) {
        this.miningSpeedMultiplier = Objects.requireNonNull(miningSpeedMultiplier);
        this.blocks = new ConfiguredBlockSet<>(blockSelectors, new MinecraftBlockResolver());
    }

    public float adjustDestroySpeed(BlockState state, float currentSpeed) {
        if (!blocks.contains(state.getBlock())) {
            return currentSpeed;
        }
        return (float) (currentSpeed * miningSpeedMultiplier.getAsDouble());
    }

    public void invalidateBlockCache() {
        blocks.invalidate();
    }
}
