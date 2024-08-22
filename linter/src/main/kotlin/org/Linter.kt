package org

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.serialization.json.JsonObject
import org.astnode.ProgramNode
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.types.VisitorResult
import org.checkvisitors.NamingFormatCheckVisitor
import org.checkvisitors.PrintUseCheckVisitor
import org.checkvisitors.UnusedVariableCheckVisitor
import org.expressionfactory.PatternFactory

class Linter(private val jsonFile: JsonObject){
    private val warnings = mutableListOf<String>()
    private val checkVisitors: List<ASTNodeVisitor> = LinterFactory().createLinterVisitors(jsonFile)

    fun lint(node: ProgramNode): Report {
        checkVisitors.forEach { visitor ->
            val result: VisitorResult.ListResult = visitor.visit(node) as VisitorResult.ListResult // estoy seguro q voy a recibir un listResult.
            warnings.addAll(result.value) // voy agregando los warnings q cada visitor da.
        }
        return Report(warnings) // devuelvo el reporte con todos los warnings.
    }
}

class LinterFactory() {
    fun createLinterVisitors(jsonFile: JsonObject): List<ASTNodeVisitor> {
        //pass jsonObject to string
        val jsonString = JsonObject.toString()

        val mapper = jacksonObjectMapper()
        val config: LinterConfig = mapper.readValue(jsonString)

        val checkVisitors = config.enabledChecks.mapNotNull { checkName ->
            when (checkName) {
                "UnusedVariableCheck" -> UnusedVariableCheckVisitor()
                "NamingFormatCheck" -> NamingFormatCheckVisitor(config.namingPatternName, PatternFactory.getNamingFormatPattern(config.namingPatternName))
                "PrintUseCheck" -> PrintUseCheckVisitor(config.printlnCheckEnabled)
                else -> null
            }
        }

        return checkVisitors
    }
}

data class LinterConfig(
    val enabledChecks: List<String>,
    val namingPatternName: String = "",
    val printlnCheckEnabled: Boolean = false
)
