import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.antlr.v4.runtime.*
import org.antlr.v4.runtime.tree.ParseTreeWalker
import com.google.gson.Gson
import java.io.File



abstract class CodeStatisticsTask : DefaultTask() {

    data class Statistics(val lineNumber: Int, val classNumber: Int, val methodNumber: Int)
    private var lineNumber = 0
    private var classNumber = 0
    private var methodNumber = 0

    @TaskAction
    fun generateReport() {
        val srcDir = project.fileTree("src").matching {
            include("**/*.java", "**/*.kt")
        }

        srcDir.forEach {
            file -> getFileStatistics(file)
        }

        saveStatistics()
    }

    private fun getFileStatistics(file: File) {
        lineNumber += file.readLines().size

        val inputStream = CharStreams.fromFileName(file.absolutePath)


        val tree = when (file.extension) {
            "kt" -> KotlinParser(CommonTokenStream(KotlinLexer(inputStream))).kotlinFile()
            "java" -> JavaParser(CommonTokenStream(JavaLexer(inputStream))).compilationUnit()
            else -> throw IllegalStateException("Unsupported file extension ${file.extension}")
        }

        val listener = when (file.extension) {
            "kt" -> KotlinCodeListener()
            "java" -> JavaCodeListener()
            else -> throw IllegalStateException("Unsupported file extension ${file.extension}")
        }

        val walker = ParseTreeWalker()
        walker.walk(listener, tree)
    }

    private inner class JavaCodeListener : JavaParserBaseListener() {

        override fun enterClassDeclaration(ctx: JavaParser.ClassDeclarationContext?) {
            classNumber++
        }

        override fun enterMethodDeclaration(ctx: JavaParser.MethodDeclarationContext?) {
            methodNumber++
        }
    }

    private inner class KotlinCodeListener : KotlinParserBaseListener() {

        override fun enterClassDeclaration(ctx: KotlinParser.ClassDeclarationContext?) {
            classNumber++
        }

        override fun enterFunctionDeclaration(ctx: KotlinParser.FunctionDeclarationContext?) {
            methodNumber++
        }
    }

    private fun saveStatistics() {
        val gson = Gson()
        val jsonReport = gson.toJson(Statistics(lineNumber, classNumber, methodNumber))

        val file = project.file("report.json")
        file.writeText(jsonReport)
    }
}