package org

import org.lexer.Lexer
import org.lexer.TokenMatcher
import org.tokenTypes.*

fun main() {
    val matcher = TokenMatcher()
    addTokens(matcher)
    val example1 = "let x: number;"
    val example2 = "let x: string = 'hello';"
    val example3 = "let x: number = 5;"
    val example4 = "let x: number; \n x = 5"
    val list = Lexer().lex(example1, matcher)
    list.forEach { println(it) }
}

fun addTokens(matcher: TokenMatcher) {
    matcher.addNewToken(AssignationType())
    matcher.addNewToken(IdentifierType())
    matcher.addNewToken(LetType())
    matcher.addNewToken(SemicolonType())
    matcher.addNewToken(StringType())
    matcher.addNewToken(NumberType())
}