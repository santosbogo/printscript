package org.common.astnode.astnodevisitor.types

import org.shared.astnode.expressionnode.LiteralValue

data class VisitorResult(
    val literalValue: LiteralValue?,
    val map: Map<String, Any>
)