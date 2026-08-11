import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml
import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml

plugins {
    kotlin("jvm") version libs.versions.kotlin.get()
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
    alias(libs.plugins.shadow)
    alias(libs.plugins.run.paper)
    alias(libs.plugins.resource.factory.paper)
    alias(libs.plugins.minotaur)
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
    implementation(libs.kotlinx.serialization)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.compactMono)
    implementation(libs.configurate.hocon)
    implementation(libs.configurate.extra.kotlin)

    testImplementation(kotlin("test"))
    testImplementation(libs.junit)
}

paperPluginYaml {
    name.set(rootProject.name)

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

    if (System.getenv("RELEASE")?.toBoolean() ?: false) modrinth {
        token.set(System.getenv("MODRINTH_TOKEN") ?: error("Missing 'MODRINTH_TOKEN' variable!"))

        projectId.set("xbr87sYf")
        versionName.set(System.getenv("VERSION_NAME") ?: error("Missing 'VERSION_NAME' variable!"))
        versionNumber.set(System.getenv("VERSION")?.removePrefix("v") ?: error("Missing 'VERSION' variable!"))
        versionType.set("release")
        uploadFile.set(shadowJar)
        gameVersions.addAll("26.1", "26.1.1", "26.1.2", "26.2")
        loaders.addAll("paper", "purpur", "folia")
        changelog.set(System.getenv("CHANGELOG") ?: error("Missing 'CHANGELOG' variable!"))
        dependencies {
            required.project("packetevents")
        }

        syncBodyFrom.set(rootProject.file("README.md").readText())
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