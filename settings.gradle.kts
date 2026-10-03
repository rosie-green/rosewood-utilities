pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()

        exclusiveContent {
            forRepository {
                maven {
                    name = "Fabric"
                    url = uri("https://maven.fabricmc.net/")
                }
            }
            filter { includeGroupAndSubgroups("net.fabricmc") }
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version providers.gradleProperty("plugins.foojay.version")
    id("net.fabricmc.fabric-loom") version providers.gradleProperty("plugins.loom.version") apply false
}

rootProject.name = "rosewood-utilities"

includeBuild("build-logic")
include("fabric")

// Gradle 10 preview features
enableFeaturePreview("NO_IMPLICIT_LOOKUP_IN_PARENT_PROJECTS")
enableFeaturePreview("ENHANCED_GRAPH_ORDERING")
