plugins {
    `java-library`
    idea
    id("net.neoforged.moddev.legacyforge")
}

/// Version-specific Settings ///
val minecraftVersion = "1.19.2"
val mcpVer = minecraftVersion

/// Shared Mod Settings (from root gradle.properties) ///
val modId: String by project

dependencies {
    api(project(":common"))
}

base {
    archivesName = "$modId-$minecraftVersion-common"
}

java.toolchain {
    languageVersion = JavaLanguageVersion.of(17)
    @Suppress("UnstableApiUsage")
    vendor = JvmVendorSpec.JETBRAINS
}

legacyForge {
    mcpVersion = mcpVer
}
