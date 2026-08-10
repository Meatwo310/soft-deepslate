import me.modmuss50.mpp.PublishModTask
import me.modmuss50.mpp.ReleaseType
import net.meatwo310.mdk.build.DownloadGitHubRelease

plugins {
    id("me.modmuss50.mod-publish-plugin")
}

data class PublishTarget(
    val projectName: String,
    val minecraftVersion: String,
    val loader: String,
    val artifactLoader: String,
    val javaVersion: String,
)

fun String.toPublishTaskSuffix(): String =
    split(Regex("[^A-Za-z0-9]+"))
        .filter { it.isNotEmpty() }
        .joinToString("") { part -> part.replaceFirstChar { it.titlecase() } }

val modVersion = providers.fileContents(layout.projectDirectory.file("version.txt"))
    .asText
    .map { it.trim() }
    .get()
val modId = providers.gradleProperty("modId").get()
val modName = providers.gradleProperty("modName").get()
val publishTargets = (gradle.extensions.extraProperties["ciBuildProjectNames"] as List<*>)
    .map { it.toString() }
    .map { projectName ->
        val targetProject = project(":$projectName")
        val loader = projectName.substringAfterLast("-")
        PublishTarget(
            projectName = projectName,
            minecraftVersion = targetProject.property("minecraftVersion").toString(),
            loader = loader,
            artifactLoader = if (loader == "neo") "neoforge" else loader,
            javaVersion = targetProject.findProperty("javaVersion")?.toString() ?: "17",
        )
    }

val publishTag = providers.gradleProperty("publishTag").orElse("v$modVersion")
val publishDestination = providers.gradleProperty("publishDestination")
    .orElse("both")
    .map { it.lowercase() }
val publishDryRun = providers.gradleProperty("publishDryRun")
    .map { it.toBooleanStrict() }
    .orElse(true)
val publishReleaseType = providers.gradleProperty("publishReleaseType")
    .orElse("stable")
    .map { ReleaseType.valueOf(it.uppercase()) }
val publishRepository = providers.gradleProperty("publishGitHubRepository")
val publishCurseForgeProjectId = providers.gradleProperty("publishCurseForgeProjectId")
val publishModrinthProjectId = providers.gradleProperty("publishModrinthProjectId")
val publishInputDirectory = layout.buildDirectory.dir("publish/input")
val publishChangelogFile = layout.buildDirectory.file("publish/RELEASE_NOTES.md")
val requiredPublishAssets = publishTargets.flatMap { target ->
    val baseName = "$modId-${target.minecraftVersion}-${target.artifactLoader}-v$modVersion"
    listOf("$baseName.jar", "$baseName-sources.jar")
}

val downloadPublishRelease = tasks.register<DownloadGitHubRelease>("downloadPublishRelease") {
    group = "publishing"
    description = "Downloads jars and release notes from the selected GitHub Release."
    repository.set(publishRepository)
    tag.set(publishTag)
    requiredAssetNames.set(requiredPublishAssets)
    artifactsDirectory.set(publishInputDirectory)
    changelogFile.set(publishChangelogFile)
}

val selectedPublishDestination = publishDestination.get()
if (selectedPublishDestination !in setOf("both", "curseforge", "modrinth")) {
    throw GradleException(
        "Unsupported publishDestination '$selectedPublishDestination'; use both, curseforge, or modrinth.",
    )
}

publishMods {
    dryRun.set(publishDryRun)
    changelog.set(providers.fileContents(publishChangelogFile).asText)
    type.set(publishReleaseType)

    for (target in publishTargets) {
        val taskSuffix = target.projectName.toPublishTaskSuffix()
        val baseName = "$modId-${target.minecraftVersion}-${target.artifactLoader}-v$modVersion"
        val mainFile = publishInputDirectory.map { it.file("$baseName.jar") }
        val sourcesFile = publishInputDirectory.map { it.file("$baseName-sources.jar") }
        val releaseVersion = "${target.minecraftVersion}-${target.artifactLoader}-v$modVersion"
        val displayName = "$modName $releaseVersion"

        if (selectedPublishDestination in setOf("both", "curseforge")) {
            curseforge("curseforge$taskSuffix") {
                accessToken.set(providers.environmentVariable("CURSEFORGE_TOKEN"))
                projectId.set(publishCurseForgeProjectId)
                file.set(mainFile)
                version.set(releaseVersion)
                this.displayName.set(displayName)
                modLoaders.add(target.artifactLoader)
                minecraftVersions.add(target.minecraftVersion)
                javaVersions.add(JavaVersion.toVersion(target.javaVersion))
                client.set(true)
                server.set(true)
                additionalFile(sourcesFile) {
                    name.set("Sources")
                }
                if (target.loader == "fabric") {
                    requires("fabric-api", "forge-config-api-port-fabric")
                }
            }
        }

        if (selectedPublishDestination in setOf("both", "modrinth")) {
            modrinth("modrinth$taskSuffix") {
                accessToken.set(providers.environmentVariable("MODRINTH_TOKEN"))
                projectId.set(publishModrinthProjectId)
                file.set(mainFile)
                version.set(releaseVersion)
                this.displayName.set(displayName)
                modLoaders.add(target.artifactLoader)
                minecraftVersions.add(target.minecraftVersion)
                environment.set(CLIENT_AND_SERVER)
                additionalFile(sourcesFile) {
                    type.set(SOURCES_JAR)
                }
                if (target.loader == "fabric") {
                    requires("fabric-api", "forge-config-api-port")
                }
            }
        }
    }
}

tasks.withType<PublishModTask>().configureEach {
    dependsOn(downloadPublishRelease)
}
