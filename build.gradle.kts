plugins {
    idea
}

tasks.wrapper {
    gradleVersion = "9.8.0"
    distributionSha256Sum = "bafd5ce9cfaea0fbccfdc8439a1ac42fbd4cd9c89dc9a988228d8a2639a58e6c"
    distributionType = Wrapper.DistributionType.BIN
}

val projectFolders = arrayOf(
    rootDir,
    rootDir.resolve("build-logic"),
    rootDir.resolve("fabric")
)

val excludedFolders = arrayOf("build", "runs", ".kotlin")

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true

        excludeDirs.addAll(
            projectFolders.flatMap { folder ->
                excludedFolders.map { folder.resolve(it) }
            }
        )
    }
}
