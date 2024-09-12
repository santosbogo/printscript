package org

import org.astnode.ASTNode
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.iterator.PrintScriptIterator

class Interpreter(
    private val visitor: ASTNodeVisitor,
    private val parser: PrintScriptIterator<ASTNode>
) {
    fun interpret() {
        while (parser.hasNext()) {
            val node = parser.next()!!
            visitor.visit(node)
        }
    }
}
