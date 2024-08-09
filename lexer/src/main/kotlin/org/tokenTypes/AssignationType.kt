package org.tokenTypes

import org.Location
import org.token.Assignation
import org.token.Token

class AssignationType : TokenType {
    override val name = "="
    override fun generateToken(content: String, location: Location): Token {
        return Assignation(this, content, location)
    }
}
