pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "custom-mdk"

include("26.1-fabric")
include("26.1-neo")
include("1.18.2-forge")
include("1.19.2-forge")
include("1.20.1-forge")
include("1.21.1-neo")
