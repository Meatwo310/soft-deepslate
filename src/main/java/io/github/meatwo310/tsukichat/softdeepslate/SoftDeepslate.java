package io.github.meatwo310.tsukichat.softdeepslate;

import com.mojang.logging.LogUtils;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import io.github.meatwo310.tsukichat.softdeepslate.config.ServerConfigs;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraftforge.fml.config.ModConfig;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.List;

public class SoftDeepslate implements ModInitializer {
    public static final String MODID = "softdeepslate";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final HashSet<String> blocksCache = new HashSet<>();

    @Override
    public void onInitialize() {
        ForgeConfigRegistry.INSTANCE.register(MODID, ModConfig.Type.SERVER, ServerConfigs.SERVER_SPEC);
        CommonLifecycleEvents.TAGS_LOADED.register((dynamicRegistryManager, b) -> reload());
    }

    private static void reload() {
        if (!ServerConfigs.SERVER_SPEC.isLoaded()) {
            LOGGER.warn("Config not loaded, skipping reload!");
            return;
        }

        blocksCache.clear();
        blocksCache.addAll(ServerConfigs.blockIDsEntry.get());
        LOGGER.info("Added {} blocks from config", blocksCache.size());

        addBlocksFromTags(ServerConfigs.blockTags.get());
        addBlocksFromTags(ServerConfigs.blockTagsDeepslate.get(), "deepslate");
        LOGGER.info("Done reloading! Total: {} blocks", blocksCache.size());
    }

    private static void addBlocksFromTags(List<? extends String> tags) {
        addBlocksFromTags(tags, null);
    }

    private static void addBlocksFromTags(List<? extends String> tags, @Nullable String substringToCheck) {
        for (String tag : tags) {
            LOGGER.info("Processing tag #{}{}", tag, (substringToCheck != null) ? " with id containing '" + substringToCheck + "'" : "");
            TagKey<Block> tagKey = TagKey.of(RegistryKeys.BLOCK, new Identifier(tag));
            for (RegistryEntry<Block> registryBlock : Registries.BLOCK.iterateEntries(tagKey)) {
                var key = registryBlock.getKey();
                if (key.isEmpty()) continue;
                String blockId = key.get().getValue().toString();
                if (substringToCheck != null && !blockId.contains(substringToCheck)) continue;
                blocksCache.add(blockId);
                LOGGER.info("#{} → {}", tag, blockId);
            }
        }
    }
}
