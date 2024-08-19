package org.command

import org.Lexer
import org.Parser

class ValidationCommand(private val input: String) : Command {
    override fun execute() {
        // ./file.txt validate
        val file = input.split(" ")[0].removePrefix("./")
        val tokens = Lexer().tokenize(file)
        val ast = Parser().parse(tokens)
        // TODO parser must return list of error messages
    }
}