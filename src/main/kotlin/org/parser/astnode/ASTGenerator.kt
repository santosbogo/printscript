package org.parser.astnode

import org.lexer.Token
import org.parser.astnode.statementnode.AssignmentNode
import org.parser.astnode.statementnode.PrintStatementNode
import org.parser.astnode.statementnode.StatementNode
import org.parser.astnode.statementnode.VariableDeclarationNode

// ASTGenerator is in charge of generating the AST from a list of tokens and checking its syntax.
class ASTGenerator {
    private val nodeMap: List<Pair<String, StatementNode>> = loadNodes()
    private var node : StatementNode = AssignmentNode() // por ahora inicializo asi

    fun generate(buffer : ArrayList<Token>): ASTNode {
        // Generate the AST from a list of ASTNodes
        // It should check for syntax errors and semantic errors.
        if (appliesToAnyFormula(buffer)) {
            return node.generate(buffer)
        } else {
            throw Exception("Invalid syntax, the Tokens didn't match any formula. Tokens: $buffer")
        }
    }

    // Checks if any formula applies to the buffer of tokens
    private fun appliesToAnyFormula(buffer: ArrayList<Token>): Boolean {
        for ((_, node) in nodeMap) {
            if (checkIfEqual(buffer, node.formula)) {
                this.node = node
                return true
            }
        }
        return false
    }

    // Checks if the buffer is equal to a specific fórmula
    // Esto es probable que cambie, pero por ahora es lo que se me ocurrió
    private fun checkIfEqual(buffer: ArrayList<Token>, list: List<String>): Boolean {
        if (buffer.size != list.size) {
            return false
        }
        for (i in buffer.indices) {
            if (buffer[i].type != list[i]) {
                return false
            }
        }
        return true
    }
}

fun loadNodes(): List<Pair<String, StatementNode>> {
    return listOf(
        "AssignmentNode" to AssignmentNode(),
        "PrintStatementNode" to PrintStatementNode(),
        "VariableDeclarationNode" to VariableDeclarationNode()
    )
}