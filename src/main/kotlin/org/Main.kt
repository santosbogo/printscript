package org

import org.lexer.Lexer

fun main() {
    val lexer = Lexer()
    val tokens = lexer.tokenize("let a: number = 1;")
    tokens.forEach { println(it) }
}