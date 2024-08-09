package org.tokenTypes

import org.Location
import org.token.Number
import org.token.Token

class NumberType : TokenType {
    override val name = "number"
    override fun generateToken(content: String, location: Location): Token {
        return Number(this, content, location)
    }
}
