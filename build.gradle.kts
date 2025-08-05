plugins {
    kotlin("jvm") version "2.1.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.dropwizard:dropwizard-core:4.0.0")
}

tasks.test {
    useJUnitPlatform()
}