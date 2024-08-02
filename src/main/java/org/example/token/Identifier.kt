package org.example.token
import org.example.utils.Location
import org.example.tokenTypes.TokenType

class Identifier(override val type: TokenType, override val content: String, override val location: Location) : Token {
}