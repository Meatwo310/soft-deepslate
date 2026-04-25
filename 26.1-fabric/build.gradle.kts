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
val modAuthors: String by project
val modDescription: String by project
val modDisplayUrl: String by project
val modIssueTrackerUrl: String by project
val modCredits: String by project
val modFabricEntrypoint: String by project
val modFabricClientEntrypoint: String by project

val generatedModMetadataDir = layout.buildDirectory.dir("generated/sources/modMetadata")

base {
    archivesName = "$modId-$minecraftVersion-fabric"
}

sourceSets.main.get().resources.srcDir(generatedModMetadataDir)

loom {
    splitEnvironmentSourceSets()

    mods {
        create(modId) {
            sourceSet(sourceSets.main.get())
            sourceSet(sourceSets.named("client").get())
            sourceSet(project(":26.1-common").sourceSets.main.get())
        }
    }
}

tasks.jar {
    from(project(":26.1-common").sourceSets.main.get().output)
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    implementation("net.fabricmc:fabric-loader:$loaderVersion")
    implementation("net.fabricmc.fabric-api:fabric-api:$fabricApiVersion")
    implementation(project(":26.1-common"))
}

java.toolchain {
    languageVersion = JavaLanguageVersion.of(25)
    @Suppress("UnstableApiUsage")
    vendor = JvmVendorSpec.JETBRAINS
}

tasks.withType<JavaCompile>().configureEach {
    options.release = 25
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
        "mod_version" to JsonOutput.toJson(version.toString()),
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
//     inputs.property("projectName", project.name)
//
//     from(rootProject.file("LICENSE")) {
//         rename { "${it}_${project.name}" }
//     }
// }

loom {
    runs.configureEach {
        ideConfigGenerated(true)
        if (name == "gameTest") {
            vmArg("-Dfabric.log.level=debug")
        }
    }
}

fabricApi {
    val testModId = "$modId-test"

    @Suppress("UnstableApiUsage")
    configureTests {
        createSourceSet = true
        modId = testModId
        enableGameTests = true
        enableClientGameTests = true
        eula = true
    }
}
