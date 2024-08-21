package org.semanticanalysis

import org.semanticanalysis.semanticchecks.AssignmentTypeCheck
import org.semanticanalysis.semanticchecks.SemanticCheck
import org.semanticanalysis.semanticchecks.VariableDeclarationCheck
import org.semanticanalysis.semanticchecks.VariableDeclarationTypeCheck

class SemanticAnalyzerFactory {
    fun createDefaultSemanticAnalyzer(): SemanticAnalyzer {
        val checks: List<SemanticCheck> = listOf(
            VariableDeclarationCheck(),
            AssignmentTypeCheck(),
            VariableDeclarationTypeCheck(),
        )
        return SemanticAnalyzer(checks)
    }

    fun createDefaultSemanticChecks(): List<SemanticCheck> {
        return listOf(
            VariableDeclarationCheck(),
            AssignmentTypeCheck(),
            VariableDeclarationTypeCheck(),
        )
    }
}
