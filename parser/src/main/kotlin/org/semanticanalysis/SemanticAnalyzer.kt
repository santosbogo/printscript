package org.semanticanalysis

import org.astnode.ASTNode
import org.astnode.astnodevisitor.InterpreterVisitor
import org.semanticanalysis.semanticchecks.SemanticCheck

class SemanticAnalyzer(private val checks: List<SemanticCheck>) {
    // lista donde voy guardando variables y funciones creadas.
    private val interpreterVisitor: InterpreterVisitor = InterpreterVisitor()

    fun analyze(node: ASTNode) {
        // corro los chequeos semánticos sobre cada statement.
        for (check in checks) {
            check.check(node, interpreterVisitor.symbolTable)
        }
    }
}
