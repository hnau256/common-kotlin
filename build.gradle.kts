import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    val kotlinVersion = "2.3.0"
    kotlin("multiplatform") version kotlinVersion
    id("com.android.kotlin.multiplatform.library") version "8.13.2"
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

group = "com.github.hnau256.common-kotlin"
version = "1.10.0"

kotlin {
    jvm()
    linuxX64()

    jvmToolchain(17)

    androidLibrary {
        namespace = "com.github.hnau256." + project.name.replace('-', '.')
        compileSdk = 36
        minSdk = 21
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                val arrow = "2.2.1.1"
                implementation("io.arrow-kt:arrow-core:$arrow")
                implementation("io.arrow-kt:arrow-core-serialization:$arrow")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")
                implementation("org.jetbrains.kotlinx:atomicfu:0.29.0")
            }
        }

        androidMain {
            dependencies {
                implementation("androidx.appcompat:appcompat:1.7.1")
            }
        }
    }
}

publishing {
    publications {
        configureEach {
            (this as MavenPublication).apply {
                groupId = project.group as String
                version = project.version as String
            }
        }
    }
}
