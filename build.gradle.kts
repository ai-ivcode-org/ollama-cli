import org.ivcode.gradle.s3mvn.utils.isSnapshot
import java.net.URI

plugins {
    id("s3mvn")
    kotlin("jvm") version "2.1.10"
    kotlin("plugin.spring") version "2.1.10"
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "org.ivcode"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

s3mvn {
    val directory = if (isSnapshot(project.version.toString())) "snapshot" else "release"
    url = URI("s3://maven.ivcode.org/$directory/")
}

dependencies {
    // SLF4J Logging
    implementation("org.slf4j:slf4j-api:2.0.9")
    runtimeOnly("org.slf4j:slf4j-simple:2.0.9")

    // Kotlin reflection (needed when you want to read KClass annotations, properties, etc.)
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.1.10")

    implementation("io.github.ollama4j:ollama4j:1.1.4")

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter")

    // Kotlin CLI
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.6")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}