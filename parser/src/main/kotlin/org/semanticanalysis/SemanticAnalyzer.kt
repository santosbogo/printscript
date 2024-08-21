package org.semanticanalysis

import org.astnode.ASTNode
import org.astnode.astnodevisitor.InterpreterVisitor
import org.semanticanalysis.semanticchecks.SemanticCheck

class SemanticAnalyzer(private val checks: List<SemanticCheck>) {
    private val semanticVisitor: SemanticVisitor = SemanticVisitor()

    fun analyze(node: ASTNode) {
        for (check in checks) {
            check.check(node, semanticVisitor.symbolTable)
        }
        semanticVisitor.visit(node)
    }

    fun reset() {
        semanticVisitor.symbolTable.clear()
    }
}
