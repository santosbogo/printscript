package org.tokenTypes

import org.Location
import org.token.Semicolon
import org.token.Token

class SemicolonType : TokenType {
    override val name = ";"
    override fun generateToken(content: String, location: Location): Token {
        return Semicolon(this, content, location)
    }
}