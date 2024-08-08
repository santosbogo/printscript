package org.astnode.statementnode.util

import org.Token
import org.astnode.expressionnode.Identifier
import org.astnode.expressionnode.LiteralValue

class Utils {

    // Generates an Identifier from a Token
    fun generateIdentifier(identifier: Token, dataType: String): Identifier {
        return Identifier(identifier.type, identifier.location, identifier.value, dataType)
    }

    // Gets the literal Value of a given token
    fun getLiteralValue(literal: Token): LiteralValue {
        return if (literal.type == "NumberToken") {
            LiteralValue.NumberValue(literal.value.toInt())
        } else {
            LiteralValue.StringValue(literal.value)
        }
    }
}
