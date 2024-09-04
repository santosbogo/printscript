package org

import org.astnode.ASTNode
import org.interpretervisitors.InterpreterVisitor

class Interpreter(private val visitor: InterpreterVisitor) {
    fun interpret(node: ASTNode): InterpreterResult {
        visitor.visit(node)
        return InterpreterResult(visitor.printsList)
    }
}
