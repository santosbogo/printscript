package org

import org.astnode.ASTNode
import org.interpretervisitors.InterpreterVisitor
import org.iterator.PrintScriptIterator

class Interpreter(
    private val visitor: InterpreterVisitor,
    private val nodeIterator: PrintScriptIterator<ASTNode>
) {
    fun interpret() {
        while (nodeIterator.hasNext()) {
            val node = nodeIterator.next()!!
            visitor.visit(node)
        }
    }
}
