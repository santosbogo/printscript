package org.semanticanalysis

import org.semanticanalysis.semanticchecks.AssignmentKindCheck
import org.semanticanalysis.semanticchecks.AssignmentTypeCheck
import org.semanticanalysis.semanticchecks.SemanticCheck
import org.semanticanalysis.semanticchecks.VariableDeclarationCheck
import org.semanticanalysis.semanticchecks.VariableDeclarationTypeCheck

class SemanticAnalyzerFactory {
    fun createSemanticAnalyzerV10(): SemanticAnalyzer {
        val checks: List<SemanticCheck> = listOf(
            VariableDeclarationCheck(),
            AssignmentTypeCheck(),
            VariableDeclarationTypeCheck(),
        )
        return SemanticAnalyzer(checks)
    }

    fun createSemanticChecksV10(): List<SemanticCheck> {
        return listOf(
            VariableDeclarationCheck(),
            AssignmentTypeCheck(),
            VariableDeclarationTypeCheck(),
        )
    }

    fun createSemanticAnalyzerV11(): SemanticAnalyzer {
        val checks: List<SemanticCheck> = createSemanticChecksV11()
        return SemanticAnalyzer(checks)
    }

    private fun createSemanticChecksV11(): List<SemanticCheck> {
        return listOf(
            VariableDeclarationCheck(),
            AssignmentTypeCheck(),
            VariableDeclarationTypeCheck(),
            AssignmentKindCheck(),
        )
    }
}
