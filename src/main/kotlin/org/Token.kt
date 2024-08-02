package org

import org.tokenTypes.TokenType

interface Token {
    val type: String
    val content: String
    val location: Location
}