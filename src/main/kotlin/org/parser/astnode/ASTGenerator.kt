package org.parser.astnode

import org.lexer.Token

// ASTGenerator is in charge of generating the AST from a list of tokens and checking its syntax.
class ASTGenerator {
    private val formulaMap: List<Pair<String, List<String>>> = loadFormulas()

    public fun generate(buffer : ArrayList<Token>): ASTNode {
        // Generate the AST from a list of ASTNodes
        // It should check for syntax errors and semantic errors.
        if (appliesToAnyFormula(buffer)) {
            TODO()
        } else {
            throw Exception("Invalid syntax")
        }
    }

    private fun appliesToAnyFormula(buffer: ArrayList<Token>): Boolean {
        for (list in formulaMap.map { it.second }) {
            if (checkIfEqual(buffer, list)) {
                return true
            }
        }
        return false
    }

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

fun loadFormulas(): List<Pair<String, List<String>>> {
    return listOf(
        "AssignmentNode" to listOf(
            "IdentifierNode",
            "AssignationToken",
            "ExpressionNode"
        ),
        "DeclarationNode" to listOf(
            "DeclarationToken",
            "IdentifierNode",
            "ColonToken",
            "TypeToken"
        ),
        "ExpressionNode" to listOf(
            "ExpressionNode",
            "OperationToken",
            "ExpressionNode"
        )
    )
}