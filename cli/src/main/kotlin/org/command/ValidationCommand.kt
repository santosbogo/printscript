package org.command

import org.Lexer
import org.Parser

class ValidationCommand(
    private val input: String,
    private val lexer: Lexer,
    private val parser: Parser
) : Command {
    override fun execute() {
        // validate file.txt
        val file = input.split(" ")[1]
        val tokens = lexer.tokenize(file)
        val ast = parser.parse(tokens)
        // TODO parser must return list of error messages
    }
}
