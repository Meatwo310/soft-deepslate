package net.meatwo310.softdeepslate;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/** Discovers the 1.7.10-specific deepslate implementations supplied by EFR and GregTech. */
final class DeepslateBlockRegistry {

    private static final String ET_FUTURUM_MOD_ID = "etfuturum";
    private static final String GREGTECH_MOD_ID = "gregtech";
    private static final String GREGTECH_ORE_BLOCK_PREFIX = "gt.blockores";
    private static final String DEEPSLATE_PATH_FRAGMENT = "deepslate";
    private static final String DEEPSLATE_ORE_DICTIONARY_PREFIX = "oreDeepslate";
    private static final int GREGTECH_SMALL_ORE_METADATA_OFFSET = 16000;
    private static final int GREGTECH_NATURAL_ORE_METADATA_OFFSET = 8000;

    private static final Set<Block> allMetadataBlocks = newIdentitySet();
    private static final Set<Block> gregTechEncodedMetadataBlocks = newIdentitySet();
    private static final Map<Block, Set<Integer>> metadataBlocks = new IdentityHashMap<Block, Set<Integer>>();

    private DeepslateBlockRegistry() {}

    static void discover() {
        allMetadataBlocks.clear();
        gregTechEncodedMetadataBlocks.clear();
        metadataBlocks.clear();

        discoverEtFuturumBlocks();
        discoverDeepslateOres();

        int metadataCount = 0;
        for (Set<Integer> metadata : metadataBlocks.values()) {
            metadataCount += metadata.size();
        }

        if (allMetadataBlocks.isEmpty() && metadataCount == 0) {
            ModMain.LOG.warn("No Et Futurum Requiem deepslate blocks or deepslate ores were discovered");
        } else {
            ModMain.LOG.info(
                "Discovered {} whole deepslate blocks and {} metadata-specific deepslate ore variants",
                allMetadataBlocks.size(),
                metadataCount);
        }
    }

    static boolean contains(Block block, int metadata) {
        if (allMetadataBlocks.contains(block)) {
            return true;
        }

        Set<Integer> knownMetadata = metadataBlocks.get(block);
        if (knownMetadata == null) {
            return false;
        }

        int comparableMetadata = gregTechEncodedMetadataBlocks.contains(block)
            ? normalizeGregTechOreMetadata(metadata)
            : metadata;
        return knownMetadata.contains(comparableMetadata);
    }

    private static void discoverEtFuturumBlocks() {
        for (Object registeredBlock : Block.blockRegistry) {
            if (!(registeredBlock instanceof Block)) {
                continue;
            }

            Block block = (Block) registeredBlock;
            GameRegistry.UniqueIdentifier identifier = GameRegistry.findUniqueIdentifierFor(block);
            if (identifier != null && ET_FUTURUM_MOD_ID.equals(identifier.modId)
                && identifier.name.toLowerCase(Locale.ROOT).contains(DEEPSLATE_PATH_FRAGMENT)) {
                allMetadataBlocks.add(block);
            }
        }
    }

    private static void discoverDeepslateOres() {
        for (String oreName : OreDictionary.getOreNames()) {
            if (!oreName.startsWith(DEEPSLATE_ORE_DICTIONARY_PREFIX)) {
                continue;
            }

            for (ItemStack stack : OreDictionary.getOres(oreName)) {
                addOreStack(stack);
            }
        }
    }

    private static void addOreStack(ItemStack stack) {
        if (stack == null) {
            return;
        }

        Block block = Block.getBlockFromItem(stack.getItem());
        if (block == null || block == Blocks.air) {
            return;
        }

        int metadata = stack.getItemDamage();
        if (metadata == OreDictionary.WILDCARD_VALUE) {
            allMetadataBlocks.add(block);
            metadataBlocks.remove(block);
            return;
        }
        if (allMetadataBlocks.contains(block)) {
            return;
        }

        if (isGregTechOreBlock(block)) {
            gregTechEncodedMetadataBlocks.add(block);
            metadata = normalizeGregTechOreMetadata(metadata);
        }

        Set<Integer> knownMetadata = metadataBlocks.get(block);
        if (knownMetadata == null) {
            knownMetadata = new HashSet<Integer>();
            metadataBlocks.put(block, knownMetadata);
        }
        knownMetadata.add(metadata);
    }

    /** Removes the small-ore and natural-generation flags used by GT5-Unofficial's ore block metadata. */
    static int normalizeGregTechOreMetadata(int metadata) {
        int normalized = metadata % GREGTECH_SMALL_ORE_METADATA_OFFSET;
        if (normalized >= GREGTECH_NATURAL_ORE_METADATA_OFFSET) {
            normalized -= GREGTECH_NATURAL_ORE_METADATA_OFFSET;
        }
        return normalized;
    }

    private static boolean isGregTechOreBlock(Block block) {
        GameRegistry.UniqueIdentifier identifier = GameRegistry.findUniqueIdentifierFor(block);
        return identifier != null && GREGTECH_MOD_ID.equals(identifier.modId)
            && identifier.name.startsWith(GREGTECH_ORE_BLOCK_PREFIX);
    }

    private static Set<Block> newIdentitySet() {
        return java.util.Collections.newSetFromMap(new IdentityHashMap<Block, Boolean>());
    }
}
