import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class CodeAnalysisPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.tasks.register("generateProjectStatistics", CodeStatisticsTask::class.java) {
        }
    }
}

