package org.token

import org.tokenTypes.TokenType
import org.utils.Location
import kotlin.String

interface Token {
    var type: TokenType
    var content: String
    val location: Location
}