package org.parser.semanticanalysis

import org.parser.semanticanalysis.semanticchecks.SemanticCheck
import org.parser.semanticanalysis.semanticchecks.TypeCheck
import org.parser.semanticanalysis.semanticchecks.UndeclaredVariableCheck
import org.parser.semanticanalysis.semanticchecks.VariableDeclarationCheck

class SemanticAnalyzerFactory {
    fun createDefaultSemanticAnalyzer(): SemanticAnalyzer {
        val checks: List<SemanticCheck> = listOf(
            VariableDeclarationCheck(),
            TypeCheck(),
            UndeclaredVariableCheck()
        )
        return SemanticAnalyzer(checks)
    }

}