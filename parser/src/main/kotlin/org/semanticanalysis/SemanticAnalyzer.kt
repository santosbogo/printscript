package org.semanticanalysis

import org.common.astnode.astnodevisitor.InterpreterVisitor
import org.shared.astnode.ASTNode
import org.parser.semanticanalysis.semanticchecks.SemanticCheck

class SemanticAnalyzer(private val checks: List<SemanticCheck>) {
    // lista donde voy guardando variables y funciones creadas.
    private val interpreterVisitor: InterpreterVisitor = InterpreterVisitor()

    fun analyze(node: ASTNode) {
        // corro los chequeos semanticos sobre cada statement.
        for (check in checks) {
            check.check(node, interpreterVisitor.symbolTable)
        }

        // agrego las variables declaradas a la symbolTable, si no tienen errores.
        interpreterVisitor.visit(node)
    }
}
