plugins {
    `java-library`
    `maven-publish`
    idea
    id("net.neoforged.moddev.legacyforge") version "2.0.91"
}

/// Version-specific Settings ///
val minecraftVersion = "1.20.1"
val minecraftVersionRange = "[1.20.1, 1.21)"
val forgeVersion = "47.1.3"
val forgeVersionRange = "[47.1.3,)"
val loaderVersionRange = "[47,)"
val parchmentMinecraftVersion = "1.20.1"
val parchmentMappingsVersion = "2023.09.03"
val forgeFullVersion = "$minecraftVersion-$forgeVersion"

/// Shared Mod Settings (from root gradle.properties) ///
val modId: String by project
val modName: String by project
val modLicense: String by project
val modVersion: String by project
val modGroupId: String by project
val modAuthors: String by project
val modDescription: String by project
val modDisplayUrl: String by project
val modIssueTrackerUrl: String by project
val modCredits: String by project

repositories {
//    flatDir {
//        dir("libs")
//    }

//    maven {
//        name = "ModMaven"
//        url = uri("https://modmaven.dev/")
//    }

//    exclusiveContent {
//        forRepository {
//            maven {
//                name = "Modrinth"
//                url = uri("https://api.modrinth.com/maven")
//            }
//        }
//        filter {
//            includeGroup("maven.modrinth")
//        }
//    }

    exclusiveContent {
        forRepository {
            maven {
                url = uri("https://cursemaven.com")
            }
        }
        filter {
            includeGroup("curse.maven")
        }
    }
}

dependencies {
    // Default Dependencies

    // Mod Dependencies
}

/// Project Settings ///

version = "v$modVersion"
group = modGroupId

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

with(System.getProperties()) {
    val version = get("java.version")
    val vmVersion = get("java.vm.version")
    val vendor = get("java.vendor")
    val arch = get("os.arch")
    println("Configuring with Java: $version, JVM: $vmVersion ($vendor), Arch: $arch")
}

tasks.withType<JavaCompile>().configureEach {
    doFirst {
        with(javaCompiler.get().metadata) {
            println("Compiling with Java: $javaRuntimeVersion, JVM: $jvmVersion ($vendor)")
        }
    }
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

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}

tasks.withType<JavaExec> {
    standardInput = System.`in`
}
