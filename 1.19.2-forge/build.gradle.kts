plugins {
    `java-library`
    `maven-publish`
    idea
    id("net.neoforged.moddev.legacyforge")
}

/// Version-specific Settings ///
val minecraftVersion = "1.19.2"
val minecraftVersionRange = "[1.19.2, 1.20)"
val forgeVersion = "43.4.0"
val forgeVersionRange = "[43.4.0,)"
val loaderVersionRange = "[43,)"
val parchmentMinecraftVersion = "1.19.2"
val parchmentMappingsVersion = "2022.11.27"
val forgeFullVersion = "$minecraftVersion-$forgeVersion"

/// Shared Mod Settings (from root gradle.properties) ///
val modId: String by project
val modName: String by project
val modLicense: String by project
val modVersion: String by project
val modAuthors: String by project
val modDescription: String by project
val modDisplayUrl: String by project
val modIssueTrackerUrl: String by project
val modCredits: String by project

dependencies {
    // Default Dependencies

    // Mod Dependencies
}

sourceSets.main.get().resources {
    srcDir("src/generated/resources")

    exclude("**/*.bbmodel")
    exclude("src/generated/**/.cache")
}

base {
    archivesName = "$modId-$minecraftVersion-forge"
}

java.toolchain {
    languageVersion = JavaLanguageVersion.of(17)
    @Suppress("UnstableApiUsage")
    vendor = JvmVendorSpec.JETBRAINS
}

legacyForge {
    version = forgeFullVersion

    parchment {
        mappingsVersion = parchmentMappingsVersion
        minecraftVersion = parchmentMinecraftVersion
    }

    runs {
        create("client") {
            client()
            systemProperty("forge.enabledGameTestNamespaces", modId)
        }

        create("server") {
            server()
            programArgument("--nogui")
            systemProperty("forge.enabledGameTestNamespaces", modId)
        }

        create("gameTestServer") {
            type = "gameTestServer"
            systemProperty("forge.enabledGameTestNamespaces", modId)
        }

        create("data") {
            data()
            gameDirectory = project.file("run-data")
            programArguments.addAll(
                "--mod", modId,
                "--all",
                "--output", file("src/generated/resources/").absolutePath,
                "--existing", file("src/main/resources/").absolutePath
            )
        }

        configureEach {
            systemProperty("forge.logging.markers", "REGISTRIES")
            logLevel = org.slf4j.event.Level.DEBUG
        }
    }

    mods {
        create(modId) {
            sourceSet(sourceSets.main.get())
        }
    }
}

configurations {
    val localRuntime by configurations.creating
    runtimeClasspath.get().extendsFrom(localRuntime)
}

val generateModMetadata = tasks.register<ProcessResources>("generateModMetadata") {
    val replaceProperties = mapOf(
        "minecraft_version" to minecraftVersion,
        "minecraft_version_range" to minecraftVersionRange,
        "forge_version" to forgeVersion,
        "forge_version_range" to forgeVersionRange,
        "loader_version_range" to loaderVersionRange,
        "mod_id" to modId,
        "mod_name" to modName,
        "mod_license" to modLicense,
        "mod_version" to modVersion,
        "mod_authors" to modAuthors,
        "mod_description" to modDescription,
        "mod_credits" to modCredits,
        "mod_display_url" to modDisplayUrl,
        "mod_issue_tracker_url" to modIssueTrackerUrl,
    )
    inputs.properties(replaceProperties)
    expand(replaceProperties)
    from("src/main/templates")
    into("build/generated/sources/modMetadata")
}

sourceSets.main.get().resources.srcDir(generateModMetadata)
legacyForge.ideSyncTask(generateModMetadata)
