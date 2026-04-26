plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    maven("https://maven.neoforged.net/releases")
    maven("https://maven.fabricmc.net/")
    mavenCentral()
}

dependencies {
    implementation("net.neoforged:moddev-gradle:2.0.141")
}
