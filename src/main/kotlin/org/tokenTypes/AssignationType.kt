package org.tokenTypes

import org.token.Assignation
import org.token.Token
import org.utils.Location

class AssignationType : TokenType {
    override val name = "="
    override fun generateToken(content: String, location: Location): Token {
        return Assignation(this, content, location)
    }
}
