package org.token

import org.Location
import org.tokenTypes.TokenType
import kotlin.String

interface Token {
    var type: TokenType
    var content: String
    val location: Location
}