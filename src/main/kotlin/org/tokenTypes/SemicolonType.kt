package org.tokenTypes

import org.token.Semicolon
import org.token.Token
import org.utils.Location

class SemicolonType : TokenType {
    override val name = ";"
    override fun generateToken(content: String, location: Location): Token {
        return Semicolon(this, content, location)
    }
}