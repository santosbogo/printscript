package org

class LexerResult {
    val errors = mutableListOf<String>()
    val tokens = mutableListOf<Token>()

    fun addError(message: String) {
        errors.add(message)
    }

    fun addToken(token: Token) {
        tokens.add(token)
    }

    fun hasErrors() = errors.isNotEmpty()
}
