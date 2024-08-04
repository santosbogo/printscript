package org.tokenTypes

import org.token.Identifier
import org.token.Token
import org.utils.Location

class IdentifierType : TokenType {
    override val name = "Identifier"
    override fun generateToken(content: String, location: Location): Token {
        return Identifier(this, content, location)
    }
}
