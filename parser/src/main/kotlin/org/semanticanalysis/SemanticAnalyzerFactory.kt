package org.parser.semanticanalysis

import org.parser.semanticanalysis.semanticchecks.SemanticCheck
import org.semanticanalysis.semanticmodifier.SemanticMapModifier
import org.semanticanalysis.semanticmodifier.VariableDeclarationModifier
import org.semanticanalysis.SemanticAnalyzer
import org.semanticanalysis.semanticchecks.*

class SemanticAnalyzerFactory {
    fun createDefaultSemanticAnalyzer(): SemanticAnalyzer {
        val modifiers: List<SemanticMapModifier> = listOf(
            VariableDeclarationModifier()
        )

        val checks: List<SemanticCheck> = listOf(
            VariableDeclarationCheck(),
            AssignmentTypeCheck(),
            UndeclaredVariableCheck(),
            VariableDeclarationTypeCheck(),
            PrintDeclaredVariableCheck()
        )
        return SemanticAnalyzer(modifiers, checks)
    }
}
