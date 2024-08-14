package org.semanticanalysis

import org.semanticanalysis.semanticchecks.SemanticCheck
import org.semanticanalysis.semanticchecks.*

class SemanticAnalyzerFactory {
    fun createDefaultSemanticAnalyzer(): SemanticAnalyzer {
        val checks: List<SemanticCheck> = listOf(
            VariableDeclarationCheck(),
            AssignmentTypeCheck(),
            VariableDeclarationTypeCheck(),
        )
        return SemanticAnalyzer(checks)
    }
}
