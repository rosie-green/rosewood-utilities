plugins {
    id("generic-java")
    id("net.fabricmc.fabric-loom")
}

val projectVersion = providers.gradleProperty("project_version").get()
val projectId = providers.gradleProperty("project_id").get()

group = providers.gradleProperty("project_group").get()
version = "${projectVersion}+${project.name}"

base.archivesName = providers.gradleProperty("project_name")

repositories {
    exclusiveContent {
        forRepository {
            maven {
                name = "Modrinth"
                url = uri("https://api.modrinth.com/maven")
            }
        }

        filter {
            includeGroup("maven.modrinth")
        }
    }
}

val minecraftVersion = providers.gradleProperty("minecraft_version").get()
val fabricLoaderVersion = providers.gradleProperty("fabric_loader_version").get()
val fabricApiVersion = providers.gradleProperty("fabric_api_version").get()


dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    implementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")
    implementation("net.fabricmc.fabric-api:fabric-api:$fabricApiVersion")

    runtimeOnly(providers.gradleProperty("runtime.modmenu").map { "maven.modrinth:modmenu:$it" })
}

tasks.processResources {
    val projectVersion = projectVersion

    inputs.properties("project_version" to projectVersion)

    filesMatching("fabric.mod.json") {
        expand(
            mapOf(
                "project" to mapOf(
                    "version" to projectVersion
                )
            )
        )
    }
}

loom.splitEnvironmentSourceSets()

fabricApi.configureDataGeneration {
    modId = "${projectId}_data"
    client = true
    outputDirectory = projectDir.resolve("src/main/generated")
    createSourceSet = true
}

loom {
    mods {
        create(projectId) {
            sourceSet(sourceSets.main.get())
            sourceSet(sourceSets.named("client").get())
        }
    }

    runs {
        configureEach {
            generateRunConfig = true
            runDirectory = layout.projectDirectory.dir("runs/$name")

            displayName = "Fabric ${name.replaceFirstChar(Char::uppercase)}"
        }

        named("client") { client() }
        named("server") { server() }
    }
}


