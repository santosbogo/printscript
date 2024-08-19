package org.common.astnode.astnodevisitor.types

import org.common.astnode.expressionnode.LiteralValue

sealed class VisitorResult {
    data class LiteralValueResult(val value: LiteralValue) : VisitorResult()
    data class StringResult(val value: String) : VisitorResult()
    data class MapResult(val value: Map<String, Any>) : VisitorResult()
    data class ListResult(val value: List<String>) : VisitorResult()
    data object Empty : VisitorResult() // Vacio por si no quiero devolver nada.
}