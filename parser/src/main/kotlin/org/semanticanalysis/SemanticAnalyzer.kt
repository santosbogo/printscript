package org.semanticanalysis

import org.shared.astnode.ASTNode
import org.parser.semanticanalysis.semanticchecks.SemanticCheck
import org.semanticanalysis.semanticmodifier.SemanticMapModifier

class SemanticAnalyzer(private val modifiers: List<SemanticMapModifier>, private val checks: List<SemanticCheck>) {
    // lista donde voy guardando variables y funciones creadas.
    private var symbolTable = mutableMapOf<String, Any>()

    fun analyze(node: ASTNode) {
        // corro los chequeos semanticos sobre cada statement.
        for (check in checks) {
            check.check(node, symbolTable)
        }

        // agrego las variables declaradas a la symbolTable, si no tienen errores.
        for (modifier in modifiers) {
            symbolTable = modifier.modify(node, symbolTable)
        }
    }
    fun getSymbolTable(): Map<String, Any> {
        return symbolTable
    }
}
