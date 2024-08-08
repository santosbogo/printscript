package org.parser.astnode

import org.lexer.Token
import org.parser.astnode.statementnode.AssignmentNode
import org.parser.astnode.statementnode.PrintStatementNode
import org.parser.astnode.statementnode.StatementNode
import org.parser.astnode.statementnode.VariableDeclarationNode

// ASTGenerator is in charge of generating the AST from a list of tokens and checking its syntax.
class ASTGenerator {
    private val nodeMap: List<Pair<String, StatementNode>> = loadNodes()

    fun generate(buffer: ArrayList<Token>): ASTNode {
        // Check if the buffer of tokens matches any formula
        if (appliesToAnyFormula(buffer)) {
            return getNodeWhichApplies(buffer)
        } else {
            throw Exception("Invalid syntax, the Tokens didn't match any formula. Tokens: $buffer")
        }
    }

    // Once we know which formula applies to the buffer, we generate the ASTNode
    private fun getNodeWhichApplies(buffer: java.util.ArrayList<Token>): ASTNode {
        for ((_, node) in nodeMap) {
            if (checkIfEqual(buffer, node.formula)) {
                return node.generate(buffer)
            }
        }
        throw Exception("Node not found")
    }

    // Checks if any formula applies to the buffer of tokens
    private fun appliesToAnyFormula(buffer: ArrayList<Token>): Boolean {
        for ((_, node) in nodeMap) {
            if (checkIfEqual(buffer, node.formula)) {
                return true
            }
        }
        return false
    }

    // Checks if the buffer is equal to a specific fórmula
    // Esto es probable que cambie, pero por ahora es lo que se me ocurrió
    // Principalmente el problema es que por ejemplo en el caso de un print puedo recibir multiples args dentro,
    // por lo que no puedo comparar directamente con la fórmula.
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
