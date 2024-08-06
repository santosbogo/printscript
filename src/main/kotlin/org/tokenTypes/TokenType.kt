package org.tokenTypes

import org.Location
import org.token.Token

interface TokenType {
    val name: String
    fun generateToken(content: String, location: Location): Token
}