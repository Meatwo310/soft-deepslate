plugins {
    id("net.fabricmc.fabric-loom") version "1.16-SNAPSHOT" apply false
    id("net.fabricmc.fabric-loom-remap") version "1.16-SNAPSHOT" apply false
    id("net.neoforged.moddev") version "2.0.141" apply false
    id("net.neoforged.moddev.legacyforge") version "2.0.141" apply false
}

tasks.named<Wrapper>("wrapper").configure {
    distributionType = Wrapper.DistributionType.BIN
}

subprojects {
    val modVersion: String by project
    val modGroupId: String by project

    version = modVersion
    group = modGroupId

    tasks.withType<AbstractArchiveTask>().configureEach {
        archiveVersion.set("v$modVersion")
    }

    repositories {
//        flatDir { dir("libs") }
//        maven { name = "ModMaven"; url = uri("https://modmaven.dev/") }
//        exclusiveContent {
//            forRepository { maven { name = "Modrinth"; url = uri("https://api.modrinth.com/maven") } }
//            filter { includeGroup("maven.modrinth") }
//        }
        exclusiveContent {
            forRepository { maven { url = uri("https://cursemaven.com") } }
            filter { includeGroup("curse.maven") }
        }
        maven {
            name = "ParchmentMC"
            url = uri("https://maven.parchmentmc.org")
        }
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
        options.encoding = "UTF-8"
    }

    afterEvaluate {
        tasks.withType<JavaExec>().configureEach {
            standardInput = System.`in`
        }
    }

    plugins.withId("idea") {
        configure<org.gradle.plugins.ide.idea.model.IdeaModel> {
            module {
                isDownloadSources = true
                isDownloadJavadoc = true
            }
        }
    }
}
