package org.parser.semanticanalysis

import org.parser.semanticanalysis.semanticchecks.*
import org.parser.semanticanalysis.semanticmodifier.SemanticMapModifier
import org.parser.semanticanalysis.semanticmodifier.VariableDeclarationModifier

class SemanticAnalyzerFactory {
    fun createDefaultSemanticAnalyzer(): SemanticAnalyzer {
        val modifiers: List<SemanticMapModifier> = listOf(
            VariableDeclarationModifier()
        )

        val checks: List<SemanticCheck> = listOf(
            VariableDeclarationCheck(),
            AssignmentTypeCheck(),
            UndeclaredVariableCheck(),
            VariableDeclarationTypeCheck()
        )
        return SemanticAnalyzer(modifiers, checks)
    }

}