package org.checkvisitors

import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.VisitorResult

interface CheckVisitors : ASTNodeVisitor {
    fun checkWarnings(): VisitorResult
    val warnings: MutableList<String>
}
