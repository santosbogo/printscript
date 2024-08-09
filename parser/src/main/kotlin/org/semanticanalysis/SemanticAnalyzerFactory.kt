package org.parser.semanticanalysis

import org.semanticanalysis.semanticchecks.AssignmentTypeCheck
import org.parser.semanticanalysis.semanticchecks.SemanticCheck
import org.semanticanalysis.semanticchecks.UndeclaredVariableCheck
import org.semanticanalysis.semanticchecks.VariableDeclarationCheck
import org.semanticanalysis.semanticchecks.VariableDeclarationTypeCheck
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
