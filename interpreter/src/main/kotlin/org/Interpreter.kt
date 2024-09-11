package org

import org.astnode.ASTNode
import org.interpretervisitors.InterpreterVisitor
import org.iterator.PrintScriptIterator

class Interpreter(
    private val visitor: InterpreterVisitor,
    private val parser: PrintScriptIterator<ASTNode>
) {
    fun interpret() {
        while (parser.hasNext()) {
            val node = parser.next()!!
            visitor.visit(node)
        }
    }
}
