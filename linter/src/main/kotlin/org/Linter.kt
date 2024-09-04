package org

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.astnode.ProgramNode
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.VisitorResult
import org.checkvisitors.NamingFormatCheckVisitor
import org.checkvisitors.PrintUseCheckVisitor
import org.checkvisitors.ReadInputCheckVisitor
import org.checkvisitors.UnusedVariableCheckVisitor
import org.expressionfactory.PatternFactory
import java.io.File

class Linter(jsonFile: JsonObject) {
    private val warnings = mutableListOf<String>()
    private val checkVisitors: List<ASTNodeVisitor> = LinterFactory().createLinterVisitorsV10(jsonFile)

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

class LinterFactory {
    fun createLinterV10(jsonFilePath: String = "src/test/kotlin/org/jsons/jsonV10.json"): Linter {
        // use the default json file in test
        val jsonContent = File(jsonFilePath).readText()
        val jsonObject = Json.parseToJsonElement(jsonContent).jsonObject

        return Linter(jsonObject)
    }

    fun createLinterVisitorsV10(jsonFile: JsonObject): List<ASTNodeVisitor> {
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

    fun createLinterV11(jsonFilePath: String = "src/test/kotlin/org/jsons/jsonV11.json"): Linter {
        // use the default json file in test
        val jsonContent = File(jsonFilePath).readText()
        val jsonObject = Json.parseToJsonElement(jsonContent).jsonObject

        return Linter(jsonObject)
    }

    fun createLinterVisitorsV11(jsonFile: JsonObject): List<ASTNodeVisitor> {
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
                "ReadInputCheck" -> {
                    val readInputCheckEnabled = value.jsonObject["readInputCheckEnabled"]?.jsonPrimitive?.boolean ?: false
                    visitors.add(ReadInputCheckVisitor(readInputCheckEnabled))
                }
                else -> throw IllegalArgumentException("Unknown check: $key")
            }
        }

        return visitors
    }
}
