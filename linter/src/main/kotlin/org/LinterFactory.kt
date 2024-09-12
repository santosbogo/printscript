package org

import org.astnode.ASTNode
import org.iterator.PrintScriptIterator

class LinterFactory {

    fun createLinterV10(nodeIterator: PrintScriptIterator<ASTNode>): Linter {
        return Linter("1.0", nodeIterator)
    }

    fun createLinterV11(nodeIterator: PrintScriptIterator<ASTNode>): Linter {
        return Linter("1.1", nodeIterator)
    }
}
