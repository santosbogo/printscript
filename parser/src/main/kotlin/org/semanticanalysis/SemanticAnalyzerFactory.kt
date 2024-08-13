package org.parser.semanticanalysis

import org.parser.semanticanalysis.semanticchecks.SemanticCheck
import org.semanticanalysis.SemanticAnalyzer
import org.semanticanalysis.semanticchecks.*

class SemanticAnalyzerFactory {
    fun createDefaultSemanticAnalyzer(): SemanticAnalyzer {
        val checks: List<SemanticCheck> = listOf(
            VariableDeclarationCheck(),
            AssignmentTypeCheck(),
            UndeclaredVariableCheck(),
            VariableDeclarationTypeCheck(),
            PrintDeclaredVariableCheck()
        )
        return SemanticAnalyzer(checks)
    }
}
