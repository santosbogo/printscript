package org.tokenTypes

import org.Location
import org.token.Token
import org.token.String

class StringType : TokenType {
    override val name = "string"
    override fun generateToken(content: kotlin.String, location: Location): Token {
        return String(this, content, location)
    }
}