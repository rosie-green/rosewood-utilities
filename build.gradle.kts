plugins {
    idea
}

tasks.wrapper {
    gradleVersion = "9.8.0"
    distributionSha256Sum = "bafd5ce9cfaea0fbccfdc8439a1ac42fbd4cd9c89dc9a988228d8a2639a58e6c"
    distributionType = Wrapper.DistributionType.BIN
}

fun shouldBeExcluded(file: File): Boolean {
    if (file.isDirectory) {
        val excludedFolderNames = setOf("runs", "build")

        return file.name in excludedFolderNames
    }

    return false
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true

        excludeDirs.addAll(
            rootDir.walkTopDown().filter(::shouldBeExcluded)
        )
    }
}
