package org.token

import org.tokenTypes.TokenType
import org.utils.Location

class Semicolon(override var type: TokenType, override var content: kotlin.String, override val location: Location) : Token {
}