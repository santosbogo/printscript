package org

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.astnode.ProgramNode
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.types.VisitorResult
import org.checkvisitors.NamingFormatCheckVisitor
import org.checkvisitors.PrintUseCheckVisitor
import org.checkvisitors.UnusedVariableCheckVisitor
import org.expressionfactory.PatternFactory
import java.io.File

class Linter(private val jsonFile: JsonObject) {
    private val warnings = mutableListOf<String>()
    private val checkVisitors: List<ASTNodeVisitor> = LinterFactory().createLinterVisitors(jsonFile)

    fun lint(node: ProgramNode): LinterResult {
        checkVisitors.forEach { visitor ->
            val result: VisitorResult = visitor.visit(node) // estoy seguro q voy a recibir un listResult.

            if (result is VisitorResult.ListResult) {
                warnings.addAll(result.value) // voy agregando los warnings q cada visitor da.
            }
        }
        return LinterResult(warnings) // devuelvo el reporte con todos los warnings.
    }
}

class LinterFactory() {
    fun createDefaultLinter(jsonFilePath: String = "src/test/kotlin/org/jsons/defaultJson.json"): Linter {
        // use the default json file in test
        val jsonContent = File(jsonFilePath).readText()
        val jsonObject = Json.parseToJsonElement(jsonContent).jsonObject

        return Linter(jsonObject)
    }

    fun createLinterVisitors(jsonFile: JsonObject): List<ASTNodeVisitor> {
        val visitors = mutableListOf<ASTNodeVisitor>()

        // pass jsonObject to string
        for ((key, value) in jsonFile) {
            when (key) {
                "UnusedVariableCheck" -> {
                    visitors.add(UnusedVariableCheckVisitor())
                }
                "NamingFormatCheck" -> {
                    // agarro que naming pattern se quiere usar
                    val namingPatternName = value.jsonObject["namingPatternName"]?.jsonPrimitive?.content ?: ""
                    val pattern = PatternFactory.getNamingFormatPattern(namingPatternName)
                    visitors.add(NamingFormatCheckVisitor(namingPatternName, pattern))
                }
                "PrintUseCheck" -> {
                    // agarro si esta habilitado o no el check. seteo default a falso.
                    val printlnCheckEnabled = value.jsonObject["printlnCheckEnabled"]?.jsonPrimitive?.boolean ?: false
                    visitors.add(PrintUseCheckVisitor(printlnCheckEnabled))
                }
                else -> throw IllegalArgumentException("Unknown check: $key")
            }
        }

        return visitors
    }
}
