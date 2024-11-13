plugins {
    kotlin("jvm") version "2.1.0-RC"
    id("code-analysis-plugin")
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
}

tasks.named("compileKotlin") {
    dependsOn("generateProjectStatistics")
}

application {
    mainClass.set("MainKt")
}


kotlin {
    jvmToolchain(23)
}
