package org.example

import org.example.tokenTypes.TokenType

interface Token {
    val type: TokenType
    val content: String
    val location: Location
}