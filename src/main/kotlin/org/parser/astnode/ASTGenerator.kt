package org.parser.astnode

import org.lexer.Token
import org.parser.astnode.expressionnode.Literal
import org.parser.astnode.statementnode.AssignmentNode
import org.parser.astnode.statementnode.PrintStatementNode

// ASTGenerator is in charge of generating the AST from a list of tokens and checking its syntax.
class ASTGenerator {
    private val formulaMap: List<Pair<String, List<String>>> = loadFormulas()
    private var formula : String = "";

    fun generate(buffer : ArrayList<Token>): ASTNode {
        // Generate the AST from a list of ASTNodes
        // It should check for syntax errors and semantic errors.
        if (appliesToAnyFormula(buffer)) {
            return when (formula) {
                "AssignmentNode" -> generateAssignment(buffer)
                // "DeclarationNode" -> null
                else -> throw Exception("Invalid formula")
            }
        } else {
            throw Exception("Invalid syntax, the Tokens didn't match any formula. Tokens: $buffer")
        }
    }

    private fun generateAssignment(buffer: java.util.ArrayList<Token>): ASTNode {
        TODO()
        val startLocation = buffer[0].location
        for (element in buffer) {
            if (element.type == "IdentifierToken") {
                val identifier = element
            }
            if (element.type == "AssignationToken") {
                val assignation = element
            }
        }
        // return AssignmentNode("AssignmentNode", startLocation, Literal(buffer[1], buffer[2], ), buffer[3])
    }

    private fun appliesToAnyFormula(buffer: ArrayList<Token>): Boolean {
        for ((key, list) in formulaMap) {
            if (checkIfEqual(buffer, list)) {
                formula = key
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
    // ExpressionToken sería String o Number
    return listOf(
        "AssignmentNode" to listOf(
            "IdentifierToken",
            "AssignationToken",
            "ExpressionToken",
            "SemicolonToken"
        ),
        "PrintStatementNode" to listOf(
            "PrintToken",
            "OpenParenthesisToken",
            // Aca podría ir cualquier cosa, pero no se me ocurre que poner
            "CloseParenthesisToken",
            "SemicolonToken",
        ),
        "VariableDeclarationNode" to listOf(
            "DeclarationToken",
            "IdentifierToken",
            "ColonToken",
            "NumberTypeToken",
            "AssignationToken",
            "ExpressionToken",
            "SemicolonToken",
        )
    )
}