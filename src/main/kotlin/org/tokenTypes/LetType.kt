package org.tokenTypes

import org.token.Let
import org.token.Token
import org.utils.Location

class LetType : TokenType {
    override val name = "let"
    override fun generateToken(content: String, location: Location): Token {
        return Let(this, content, location)
    }
}