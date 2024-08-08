package org.parser.semanticanalysis

import org.parser.astnode.ASTNode
import org.parser.astnode.ProgramNode
import org.parser.semanticanalysis.semanticchecks.SemanticCheck
import org.parser.semanticanalysis.semanticchecks.TypeCheck
import org.parser.semanticanalysis.semanticchecks.UndeclaredVariableCheck
import org.parser.semanticanalysis.semanticchecks.VariableDeclarationCheck

class SemanticAnalyzer {
    // lista donde voy guardando variables y funciones creadas.
    private val symbolTable = mutableMapOf<String, Any>()

    // lista de chequeos que voy a hacer.
    private val checks: List<SemanticCheck> = listOf(
        VariableDeclarationCheck(),
        TypeCheck(),
        UndeclaredVariableCheck()
    )

    fun analyze(node: ASTNode) {
        if (node is ProgramNode) {
            for (statement in node.statements) {
                analyze(statement)
            }
        }

        for (check in checks) {
            check.check(node, symbolTable)
        }
    }
}
