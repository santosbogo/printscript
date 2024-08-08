package org.interpreter

import org.parser.astnode.ASTNode

class Interpreter {

    private val symbolTable: MutableMap<String, Any> = mutableMapOf()
    private val visitor = InterpreterVisitor(symbolTable)

    fun interpret(node: ASTNode) {
        visitor.visit(node)
    }
}