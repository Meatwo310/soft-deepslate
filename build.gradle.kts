plugins {
    id("net.neoforged.moddev") version "2.0.141" apply false
    id("net.neoforged.moddev.legacyforge") version "2.0.141" apply false
}

tasks.named<Wrapper>("wrapper").configure {
    distributionType = Wrapper.DistributionType.BIN
}
