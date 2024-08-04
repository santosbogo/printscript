package org.token

import org.tokenTypes.TokenType
import org.utils.Location
import kotlin.String

class String(override var type: TokenType, override var content: String, override val location: Location) : Token {
}