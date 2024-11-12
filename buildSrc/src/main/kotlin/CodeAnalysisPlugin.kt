import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import java.io.File


class CodeAnalysisPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        target.tasks.register("generateProjectStatistic", CodeStatisticsTask::class.java) {
            group = "Code Analysis"
            description = "Generates a JSON report of classes, methods, and lines of code in Kotlin source files."
        }
    }
}

abstract class CodeStatisticsTask : DefaultTask() {

    @TaskAction
    fun generateReport() {
        println("hello world")
    }
}