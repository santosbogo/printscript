package org.token

import org.Location
import org.tokenTypes.TokenType

class Semicolon(override var type: TokenType, override var content: kotlin.String, override val location: Location) : Token {
}