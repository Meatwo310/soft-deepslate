import org.gradle.api.tasks.SourceSetContainer

val forgeCommonProject = rootProject.project(":forge-common")
val forgeCommonResources = forgeCommonProject.layout.projectDirectory.dir("src/main/resources")

extensions.getByType<SourceSetContainer>().named("main") {
    resources.srcDir(forgeCommonResources)
}
