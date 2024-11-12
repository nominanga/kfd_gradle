plugins {
    kotlin ("jvm") version "2.1.0-RC"
    `kotlin-dsl`
    id("java-gradle-plugin")
    id("antlr")
}

gradlePlugin {
    plugins {
        create("CodeAnalysisPlugin") {
            id = "code-analysis-plugin"
            description = "Java and Kotlin code analysis plugin with results in JSON format"
            implementationClass = "CodeAnalysisPlugin"
        }
    }
}


repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleKotlinDsl())
    implementation(kotlin("stdlib"))
    antlr("org.antlr:antlr4:4.13.1")
}

