package org

import java.io.Reader

object LexerFactory {
    fun createLexerV10(reader: Reader): Lexer {
        return Lexer(LexiconFactory().createLexiconV10(), reader)
    }

    fun createLexerV11(reader: Reader): Lexer {
        return Lexer(LexiconFactory().createLexiconV11(), reader)
    }
}
