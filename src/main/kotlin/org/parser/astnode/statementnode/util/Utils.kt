package org.parser.astnode.statementnode.util

import org.lexer.Token
import org.parser.astnode.expressionnode.Identifier
import org.parser.astnode.expressionnode.LiteralValue

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