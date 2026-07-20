package net.meatwo310.softdeepslate;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

final class MinecraftBlockResolver implements ConfiguredBlockSet.Resolver<Block> {
    @Override
    public Optional<Block> resolveBlock(String name) {
        ResourceLocation id = ResourceLocation.tryParse(name);
        return id == null ? Optional.empty() : BuiltInRegistries.BLOCK.getOptional(id);
    }

    @Override
    public Optional<? extends Iterable<Block>> resolveTag(String name) {
        ResourceLocation id = ResourceLocation.tryParse(name);
        if (id == null) {
            return Optional.empty();
        }
        TagKey<Block> tagKey = TagKey.create(Registries.BLOCK, id);
        return BuiltInRegistries.BLOCK.get(tagKey).map(holders -> holders.stream()
                .map(holder -> holder.value())
                .toList());
    }
}
