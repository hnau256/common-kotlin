plugins {
    val kotlinVersion = "2.1.20"
    kotlin("jvm") version kotlinVersion
    id("maven-publish")
    kotlin("plugin.serialization") version kotlinVersion
}

repositories {
    mavenCentral()
    google()
    mavenLocal()
    maven("https://jitpack.io")
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

group = "com.github.hnau256"
version = "1.0.3"

dependencies {
    val arrow = "1.2.4"
    implementation("io.arrow-kt:arrow-core:$arrow")
    implementation("io.arrow-kt:arrow-core-serialization:$arrow")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
}

tasks {
    create<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        from(sourceSets.main.get().allSource)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group as String
            artifactId = project.name
            version = project.version as String
            from(components["java"])
            artifact(tasks["sourcesJar"])
        }
    }
}