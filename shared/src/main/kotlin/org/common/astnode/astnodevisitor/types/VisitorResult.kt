package org.common.astnode.astnodevisitor.types

import org.common.astnode.expressionnode.LiteralValue

sealed class VisitorResult {
    data class LiteralValueResult(val value: LiteralValue) : VisitorResult()
    data class StringResult(val value: String) : VisitorResult() {
        override fun toString(): String {
            return value
        }
    }
    data class MapResult(val value: Map<String, Any>) : VisitorResult()
    data class ListResult(val value: List<String>) : VisitorResult()
    data object Empty : VisitorResult() // Vacío por si no quiero devolver nada.
}