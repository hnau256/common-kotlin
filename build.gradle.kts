import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    val kotlinVersion = "2.1.20"
    kotlin("multiplatform") version kotlinVersion
    id("com.android.library") version "8.7.2"
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
version = "1.0.5"

android {
    namespace = "com.github.hnau256." + project.name.replace('-', '.')
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

kotlin {
    jvm()
    linuxX64()

    jvmToolchain(17)

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
        publishLibraryVariants("release")
    }

    sourceSets {
        commonMain {
            dependencies {
                val arrow = "1.2.4"
                implementation("io.arrow-kt:arrow-core:$arrow")
                implementation("io.arrow-kt:arrow-core-serialization:$arrow")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
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