plugins {
    `java-library`
}

java {
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release = 25
    }
}
