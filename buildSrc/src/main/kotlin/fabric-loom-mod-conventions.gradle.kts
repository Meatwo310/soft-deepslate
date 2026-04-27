plugins {
    id("fabric-mod-conventions")
    id("net.fabricmc.fabric-loom")
}

val modId: String by project
val minecraftVersion: String by project
val loaderVersion: String by project
val fabricApiVersion: String by project

val commonProject = ":$minecraftVersion-common"

loom {
    splitEnvironmentSourceSets()

    mods {
        create(modId) {
            sourceSet(sourceSets.main.get())
            sourceSet(sourceSets.named("client").get())
            sourceSet(project(commonProject).sourceSets.main.get())
        }
    }

    runs.configureEach {
        ideConfigGenerated(true)
        if (name == "gameTest") {
            vmArg("-Dfabric.log.level=debug")
        }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    implementation("net.fabricmc:fabric-loader:$loaderVersion")
    implementation("net.fabricmc.fabric-api:fabric-api:$fabricApiVersion")
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
