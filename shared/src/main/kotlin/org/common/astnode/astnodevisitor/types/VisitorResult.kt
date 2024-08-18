package org.common.astnode.astnodevisitor.types

import org.common.astnode.expressionnode.LiteralValue

data class VisitorResult(
    val literalValue: LiteralValue?,
    val map: Map<String, Any>,
    val errors: List<String> = emptyList()
)