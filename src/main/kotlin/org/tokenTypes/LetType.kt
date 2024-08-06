package org.tokenTypes

import org.Location
import org.token.Let
import org.token.Token

class LetType : TokenType {
    override val name = "let"
    override fun generateToken(content: String, location: Location): Token {
        return Let(this, content, location)
    }
}