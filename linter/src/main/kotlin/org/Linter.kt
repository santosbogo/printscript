package org

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.checkvisitors.NamingFormatCheckVisitor
import org.checkvisitors.PrintUseCheckVisitor
import org.checkvisitors.UnusedVariableCheckVisitor
import org.common.astnode.ProgramNode
import org.common.astnode.astnodevisitor.ASTNodeVisitor
import org.common.astnode.astnodevisitor.types.VisitorResult
import org.common.expressionfactory.PatternFactory

class Linter(private val checkVisitors: List<ASTNodeVisitor>) {
    private val warnings = mutableListOf<String>()
    fun lint(node: ProgramNode): MutableList<String> {
        checkVisitors.forEach { visitor ->
            val result: VisitorResult = visitor.visit(node)
            warnings.addAll(result.errors) // voy agregando los warnings q cada visitor da.
        }
        return warnings
    }

}

class LinterFactory(){
    fun createLinter(jsonFile: String): Linter {
        val mapper = jacksonObjectMapper()
        val config: LinterConfig = mapper.readValue(jsonFile)

        val checkVisitors = config.enabledChecks.mapNotNull { checkName ->
            when (checkName) {
                "UnusedVariableCheck" -> UnusedVariableCheckVisitor()
                "NamingFormatCheck" -> NamingFormatCheckVisitor(config.namingPatternName, PatternFactory.getNamingFormatPattern(config.namingPatternName))
                "PrintUseCheck" -> PrintUseCheckVisitor(config.printlnCheckEnabled)
                else -> null
            }
        }

        return Linter(checkVisitors)
    }
}

data class LinterConfig(
    val enabledChecks: List<String>,
    val namingPatternName: String = "",
    val printlnCheckEnabled: Boolean = false
)
