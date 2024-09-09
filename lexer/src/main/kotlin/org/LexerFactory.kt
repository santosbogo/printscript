package org

import java.io.InputStream

object LexerFactory {
    fun createLexerV10(input: InputStream): Lexer {
        return Lexer(LexiconFactory().createLexiconV10(), input)
    }

    fun createLexerV11(input: InputStream): Lexer {
        return Lexer(LexiconFactory().createLexiconV11(), input)
    }
}
