import groovy.json.JsonOutput

plugins {
    `java-library`
    `maven-publish`
    idea
    id("net.fabricmc.fabric-loom")
}

/// Version-specific Settings ///
val minecraftVersion = "26.1"
val loaderVersion = "0.19.2"
val fabricApiVersion = "0.145.1+26.1"

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
val modFabricEntrypoint: String by project
val modFabricClientEntrypoint: String by project

version = "v$modVersion"
group = modGroupId
val resolvedVersion = version.toString()
val resolvedProjectName = project.name
val generatedModMetadataDir = layout.buildDirectory.dir("generated/sources/modMetadata")

base {
    archivesName = "$modId-$minecraftVersion-fabric"
}

sourceSets.main.get().resources.srcDir(generatedModMetadataDir)

repositories {
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

loom {
    splitEnvironmentSourceSets()

    mods {
        create(modId) {
            sourceSet(sourceSets.main.get())
            sourceSet(sourceSets.named("client").get())
        }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    implementation("net.fabricmc:fabric-loader:$loaderVersion")
    implementation("net.fabricmc.fabric-api:fabric-api:$fabricApiVersion")
}

java.toolchain {
    languageVersion = JavaLanguageVersion.of(25)
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
    options.release = 25
    options.encoding = "UTF-8"
}

java {
    withSourcesJar()
}

val generateModMetadata = tasks.register<ProcessResources>("generateModMetadata") {
    val replaceProperties = mapOf(
        "minecraft_version" to JsonOutput.toJson("~$minecraftVersion"),
        "loader_version" to JsonOutput.toJson(">=$loaderVersion"),
        "mod_id" to JsonOutput.toJson(modId),
        "mod_name" to JsonOutput.toJson(modName),
        "mod_license" to JsonOutput.toJson(modLicense),
        "mod_version" to JsonOutput.toJson(resolvedVersion),
        "mod_authors" to JsonOutput.toJson(
            modAuthors.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        ),
        "mod_description" to JsonOutput.toJson(modDescription),
        "mod_display_url" to JsonOutput.toJson(modDisplayUrl),
        "mod_issue_tracker_url" to JsonOutput.toJson(modIssueTrackerUrl),
        "mod_credits" to JsonOutput.toJson(modCredits),
        "mod_entrypoint" to JsonOutput.toJson(modFabricEntrypoint),
        "mod_client_entrypoint" to JsonOutput.toJson(modFabricClientEntrypoint),
    )
    inputs.properties(replaceProperties)
    expand(replaceProperties)
    from("src/main/templates")
    into(generatedModMetadataDir)
}

tasks.processResources {
    dependsOn(generateModMetadata)
}

tasks.named("sourcesJar") {
    dependsOn(generateModMetadata)
}

// tasks.jar {
//     notCompatibleWithConfigurationCache("Renames the bundled LICENSE file per subproject.")
//     inputs.property("projectName", resolvedProjectName)
//
//     from(rootProject.file("LICENSE")) {
//         rename { "${it}_$resolvedProjectName" }
//     }
// }

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}

loom {
    runs.configureEach {
        ideConfigGenerated(true)
    }
}

tasks.withType<JavaExec> {
    standardInput = System.`in`
}
