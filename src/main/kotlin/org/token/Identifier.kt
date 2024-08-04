package org.token
import org.utils.Location
import org.tokenTypes.TokenType

class Identifier(override var type: TokenType, override var content: kotlin.String, override val location: Location) : Token {
}