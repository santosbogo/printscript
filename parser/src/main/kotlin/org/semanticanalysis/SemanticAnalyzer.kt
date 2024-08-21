package org.semanticanalysis

import org.astnode.ASTNode
import org.astnode.astnodevisitor.InterpreterVisitor
import org.semanticanalysis.semanticchecks.SemanticCheck

class SemanticAnalyzer(private val checks: List<SemanticCheck>) {
    private val interpreterVisitor: InterpreterVisitor = InterpreterVisitor()

    fun analyze(node: ASTNode) {
        for (check in checks) {
            check.check(node, interpreterVisitor.symbolTable)
        }
    }
}
