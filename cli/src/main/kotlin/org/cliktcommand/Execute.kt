package org.cliktcommand

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import org.Interpreter
import org.Lexer
import org.Parser
import java.io.File

class Execute : CliktCommand(help = "./execute <file path>") {
    val filePath by argument()
    override fun run() {
        val content = File(filePath).readText()
        val tokens = Lexer().tokenize(content)
        val ast = Parser().parse(tokens)
        Interpreter().interpret(ast)
    }
}
