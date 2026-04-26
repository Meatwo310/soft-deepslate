plugins {
    `java-library`
    idea
    id("net.neoforged.moddev")
}

/// Version-specific Settings ///
val minecraftVersion = "1.21.1"
val neoVersion = "21.1.227"

/// Shared Mod Settings (from root gradle.properties) ///
val modId: String by project

dependencies {
    api(project(":common"))
}

base {
    archivesName = "$modId-$minecraftVersion-common"
}

java.toolchain {
    languageVersion = JavaLanguageVersion.of(21)
    @Suppress("UnstableApiUsage")
    vendor = JvmVendorSpec.JETBRAINS
}

neoForge {
    version = neoVersion
}
