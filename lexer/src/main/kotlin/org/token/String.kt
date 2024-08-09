package org.token

import org.Location
import org.tokenTypes.TokenType
import kotlin.String

class String(override var type: TokenType, override var content: String, override val location: Location) : Token
