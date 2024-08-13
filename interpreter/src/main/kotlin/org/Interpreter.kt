package org

import org.common.astnode.astnodevisitor.InterpreterVisitor
import org.shared.astnode.ASTNode
import org.common.astnode.astnodevisitor.ASTNodeVisitor

class Interpreter(private val visitor: ASTNodeVisitor = InterpreterVisitor()) {
    fun interpret(node: ASTNode) {
        visitor.visit(node)
    }

    fun getSymbolTable(): Map<String, Any> {
        return visitor.symbolTable
    }
}
