package org.interpretervisitors

import org.astnode.expressionnode.LiteralValue

class VariableTripleData(
    private val kind: String,
    val dataType: String,
    val literalValue: LiteralValue
) {
    fun changeValue(literalValue: LiteralValue): VariableTripleData {
        return VariableTripleData(kind, dataType, literalValue)
    }
}
