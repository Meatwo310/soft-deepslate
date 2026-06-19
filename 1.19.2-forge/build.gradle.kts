import net.meatwo310.mdk.build.req

plugins {
    id("legacyforge-mod-conventions")
    id("legacyforge-config-conventions")
    id("softdeepslate-forge-common-resources-conventions")
}

val configuredVersion: String by project

// Mod Dependencies
dependencies {
    modRuntimeOnly(libs.configured, req(configuredVersion))
}
