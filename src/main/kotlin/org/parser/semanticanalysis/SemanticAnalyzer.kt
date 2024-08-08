package org.parser.semanticanalysis

import org.astnode.ASTNode
import org.parser.semanticanalysis.semanticchecks.SemanticCheck

class SemanticAnalyzer(private val checks: List<SemanticCheck>) {
    // lista donde voy guardando variables y funciones creadas.
    private val symbolTable = mutableMapOf<String, Any>()

    fun analyze(node: ASTNode) {
        // corro los chequeos semanticos sobre cada statement.
        for (check in checks) {
            check.check(node, symbolTable)
        }
    }
}
