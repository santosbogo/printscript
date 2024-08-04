package org.tokenTypes

import org.token.Token
import org.utils.Location

interface TokenType {
    val name: String
    fun generateToken(content: String, location: Location): Token
}