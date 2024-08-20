package org.command

import org.Lexer
import org.Parser
import org.common.astnode.astnodevisitor.InterpreterVisitor

class ExecuteCommand(private val input: String) : Command {
    override fun execute() {
        // ./file.txt execute
        val file = input.split(" ")[0].removePrefix("./")
        val tokens = Lexer().tokenize(file)
        val ast = Parser().parse(tokens)
        ast.accept(InterpreterVisitor())
    }
}
