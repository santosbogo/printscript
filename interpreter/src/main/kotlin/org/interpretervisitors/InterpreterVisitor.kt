package org.interpretervisitors

import org.astnode.astnodevisitor.ASTNodeVisitor

interface InterpreterVisitor : ASTNodeVisitor {
    val printsList: MutableList<String>
}
