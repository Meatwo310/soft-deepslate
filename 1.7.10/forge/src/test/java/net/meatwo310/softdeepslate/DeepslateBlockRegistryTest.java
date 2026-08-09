package net.meatwo310.softdeepslate;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DeepslateBlockRegistryTest {

    private static final int DEEPSLATE_ORE_METADATA = 4001;

    @Test
    public void normalizesEveryGregTechWorldgenFlagCombination() {
        assertEquals(
            DEEPSLATE_ORE_METADATA,
            DeepslateBlockRegistry.normalizeGregTechOreMetadata(DEEPSLATE_ORE_METADATA));
        assertEquals(
            DEEPSLATE_ORE_METADATA,
            DeepslateBlockRegistry.normalizeGregTechOreMetadata(DEEPSLATE_ORE_METADATA + 8000));
        assertEquals(
            DEEPSLATE_ORE_METADATA,
            DeepslateBlockRegistry.normalizeGregTechOreMetadata(DEEPSLATE_ORE_METADATA + 16000));
        assertEquals(
            DEEPSLATE_ORE_METADATA,
            DeepslateBlockRegistry.normalizeGregTechOreMetadata(DEEPSLATE_ORE_METADATA + 24000));
    }
}
