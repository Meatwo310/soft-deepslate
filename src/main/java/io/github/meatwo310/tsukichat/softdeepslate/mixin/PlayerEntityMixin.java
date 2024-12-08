package io.github.meatwo310.tsukichat.softdeepslate.mixin;

import io.github.meatwo310.tsukichat.softdeepslate.SoftDeepslate;
import io.github.meatwo310.tsukichat.softdeepslate.config.ServerConfigs;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "getBlockBreakingSpeed", at = @At("RETURN"), cancellable = true)
    private void changeBlockBreakingSpeed(BlockState state, CallbackInfoReturnable<Float> cir) {
        String blockId = Registries.BLOCK.getId(state.getBlock()).toString();
        if (!SoftDeepslate.blocksCache.contains(blockId)) return;
        cir.setReturnValue(cir.getReturnValue() * ServerConfigs.multiplierEntry.get().floatValue());
    }
}
