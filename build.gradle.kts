import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml
import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml

plugins {
    kotlin("jvm") version libs.versions.kotlin.get()
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
    alias(libs.plugins.shadow)
    alias(libs.plugins.run.paper)
    alias(libs.plugins.resource.factory.paper)
    alias(libs.plugins.minotaur)
    alias(libs.plugins.hangerPublish)
}

repositories {
    gradlePluginPortal()
    mavenCentral()

    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.codemc.io/repository/maven-releases/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
}

@Suppress("VulnerableLibrariesLocal") dependencies {
    compileOnly(libs.paperApi)
    compileOnly(libs.packetEvents)

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.compactMono)
    implementation(libs.configurate.hocon)
    implementation(libs.configurate.extra.kotlin)

    testImplementation(kotlin("test"))
    testImplementation(libs.junit)
}

val releaseVersion = System.getenv("VERSION")?.removePrefix("v") ?: "0.0.0-DEV"

paperPluginYaml {
    name.set(rootProject.name)
    description.set(property("description") as String)
    version.set(releaseVersion)

    main.set("io.github.runkang10.fixedGameMode.FixedGameMode")
    bootstrapper.set("io.github.runkang10.fixedGameMode.FixedGameModeBootstrap")
    apiVersion.set("26.2")
    foliaSupported.set(true)

    load.set(BukkitPluginYaml.PluginLoadOrder.STARTUP)
    authors.addAll("Runkang10")
    website.set("https://github.com/Runkang10/FixedGameMode")

    dependencies {
        server("packetevents", PaperPluginYaml.Load.BEFORE, joinClasspath = true, required = false)
    }
}

kotlin {
    jvmToolchain(25)
}

tasks {
    shadowJar {
        archiveBaseName.set(rootProject.name)
        archiveVersion.set("")
        archiveClassifier.set("")

        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }

    if (System.getenv("RELEASE")?.toBoolean() ?: false) {
        val releaseName = System.getenv("VERSION_NAME") ?: error("Missing 'VERSION_NAME' variable!")
        val supportedVersions = listOf("26.1", "26.1.1", "26.1.2", "26.2")
        val releaseChangelog = System.getenv("CHANGELOG") ?: error("Missing 'CHANGELOG' variable!")
        val readme = rootProject.file("README.md").readText()

        modrinth {
            token.set(System.getenv("MODRINTH_TOKEN") ?: error("Missing 'MODRINTH_TOKEN' variable!"))

            projectId.set("xbr87sYf")
            versionName.set(releaseName)
            versionNumber.set(releaseVersion)
            versionType.set("release")
            uploadFile.set(shadowJar)
            gameVersions.addAll(supportedVersions)
            loaders.addAll("paper", "purpur", "folia")
            changelog.set(releaseChangelog)
            dependencies {
                required.project("packetevents")
            }

            syncBodyFrom.set(readme)
        }

        hangarPublish {
            publications.register("plugin") {
                apiKey.set(System.getenv("HANGAR_TOKEN") ?: error("Missing 'HANGAR_TOKEN' variable!"))

                id.set("FixedGameMode")
                channel.set("Release")
                version.set(releaseVersion)
                changelog.set(releaseChangelog)

                platforms {
                    paper {
                        url.set("https://modrinth.com/plugin/fixedgamemode/version/$releaseVersion")
                        platformVersions.set(supportedVersions)
                        dependencies {
                            url("PacketEvents", "https://modrinth.com/plugin/packetevents") {
                                required.set(true)
                            }
                        }
                    }
                }

                pages.resourcePage(readme)
            }
        }
    }

    test {
        useJUnitPlatform()
        testLogging {
            showExceptions = true
            showCauses = true
            showStackTraces = true
            showStandardStreams = true
        }
    }

    runServer {
        minecraftVersion(libs.versions.minecraft.get())
        jvmArgs("-Xms2G", "-Xmx2G", "-Dcom.mojang.eula.agree=true")
    }
}