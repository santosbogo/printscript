package org.tokenTypes

import org.Location
import org.token.Identifier
import org.token.Token

class IdentifierType : TokenType {
    override val name = "Identifier"
    override fun generateToken(content: String, location: Location): Token {
        return Identifier(this, content, location)
    }
}
