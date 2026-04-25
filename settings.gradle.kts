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


/// Common ///
include("common")

/// 1.18.2 ///
include("1.18.2-common")
include("1.18.2-forge")

/// 1.19.2 ///
include("1.19.2-common")
include("1.19.2-forge")

/// 1.20.1 ///
include("1.20.1-common")
include("1.20.1-forge")
include("1.20.1-fabric")

/// 1.21.1 ///
include("1.21.1-common")
include("1.21.1-neo")
include("1.21.1-fabric")

/// 26.1 ///
include("26.1-common")
include("26.1-fabric")
include("26.1-neo")
