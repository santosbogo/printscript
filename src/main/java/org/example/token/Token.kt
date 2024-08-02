package org.example.token

import org.example.tokenTypes.TokenType
import org.example.utils.Location

interface Token {
    val type: TokenType
    val content: String
    val location: Location
}