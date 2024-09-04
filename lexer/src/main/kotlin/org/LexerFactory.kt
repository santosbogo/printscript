package org

object LexerFactory {
    fun createLexerV10(): Lexer {
        return Lexer(LexiconFactory().createLexiconV10())
    }

    fun createLexerV11(): Lexer {
        return Lexer(LexiconFactory().createLexiconV11())
    }
}
