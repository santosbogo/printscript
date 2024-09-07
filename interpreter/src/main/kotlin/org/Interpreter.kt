package org

import org.astnode.ASTNode
import org.interpretervisitors.InterpreterVisitor

class Interpreter(private val visitor: InterpreterVisitor) {
    fun interpret(nodeIterator: Iterator<ASTNode>): InterpreterResult {
        while (nodeIterator.hasNext()) {
            val node = nodeIterator.next()
            visitor.visit(node)
        }
        // el printer dentro del visitor tiene el output. cuando itero lo hago con el mismo visitor.
        return InterpreterResult(visitor.printer.getOutput())
    }
}
