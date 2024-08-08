package org.tokenTypes

import org.Location
import org.token.String
import org.token.Token

class StringType : TokenType {
    override val name = "string"
    override fun generateToken(content: kotlin.String, location: Location): Token {
        return String(this, content, location)
    }
}
