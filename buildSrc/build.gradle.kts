plugins {
    `kotlin-dsl`
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
    antlr("org.antlr:antlr4:4.13.1")
    implementation("com.google.code.gson:gson:2.8.9")
}

tasks {
    generateGrammarSource {
        source = fileTree("src/main/antlr")
        outputDirectory = file("build/generated-src/antlr/main")
        arguments.plusAssign(listOf("-visitor"))
    }

    compileKotlin {
        dependsOn("generateGrammarSource")
    }
}

sourceSets {
    main {
        java {
            srcDir("build/generated-src/antlr/main")
        }
    }
}

