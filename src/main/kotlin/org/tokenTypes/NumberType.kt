package org.tokenTypes

import org.Location
import org.token.Token
import org.token.Number

class NumberType : TokenType {
    override val name = "number"
    override fun generateToken(content: String, location: Location): Token {
        return Number(this, content, location)
    }
}