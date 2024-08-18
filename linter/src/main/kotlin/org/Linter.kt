package org

import org.common.astnode.ProgramNode
import org.common.astnode.astnodevisitor.ASTNodeVisitor
import org.common.astnode.astnodevisitor.types.VisitorResult

class Linter(private val checkVisitors: List<ASTNodeVisitor>) {
    private val warnings = mutableListOf<String>()
    fun lint(node: ProgramNode): MutableList<String> {
        checkVisitors.forEach { visitor ->
            val result: VisitorResult = visitor.visit(node)
            warnings.addAll(result.errors) // voy agregando los warnings q cada visitor da.
        }
        return warnings
    }

}

