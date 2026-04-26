plugins {
    `java-library`
    idea
    id("net.neoforged.moddev")
}

/// Version-specific Settings ///
val minecraftVersion = "26.1"
val neoFormVer = "26.1-1"

/// Shared Mod Settings (from root gradle.properties) ///
val modId: String by project

dependencies {
    api(project(":common"))
}

base {
    archivesName = "$modId-$minecraftVersion-common"
}

java.toolchain {
    languageVersion = JavaLanguageVersion.of(25)
    @Suppress("UnstableApiUsage")
    vendor = JvmVendorSpec.JETBRAINS
}

neoForge {
    neoFormVersion = neoFormVer
}
