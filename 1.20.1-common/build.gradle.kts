plugins {
    `java-library`
    `maven-publish`
    idea
    id("net.neoforged.moddev.legacyforge")
}

/// Version-specific Settings ///
val minecraftVersion = "1.20.1"
val forgeVersion = "47.4.10"
val forgeFullVersion = "$minecraftVersion-$forgeVersion"

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
    version = forgeFullVersion
}
