package org.parser.astnode

import org.Location
import org.lexer.Token

class ASTGenerator(private val formulaMap: List<Pair<String, ArrayList<Token>>>) {

    public fun generate(buffer : ArrayList<Token>): ASTNode {
        // Generate the AST from a list of ASTNodes
        // It should check for syntax errors and semantic errors.
        TODO("Not yet implemented")

    }
}

class FormulaFactory {
    fun loadFormulas(): ASTGenerator {
        // Create a default lexicon with some common tokens. It's the basic lexicon.
        return ASTGenerator(
            listOf(
                "AssignmentNode" to arrayListOf(
                    Token("IdentifierNode", "IdentifierNode", Location(0, 0)),
                    Token("AssignationToken", "AssignationToken", Location(0, 0)),
                    Token("ExpressionNode", "ExpressionNode", Location(0, 0))
                ),
                "DeclarationNode" to arrayListOf(
                    Token("DeclarationToken", "DeclarationToken", Location(0, 0)),
                    Token("IdentifierNode", "IdentifierNode", Location(0, 0)),
                    Token("ColonToken", "ColonToken", Location(0, 0)),
                    Token("TypeToken", "TypeToken", Location(0, 0)),
                ),
                "ExpressionNode" to arrayListOf(
                    Token("ExpressionNode", "ExpressionNode", Location(0, 0)),
                    Token("OperationToken", "OperationToken", Location(0, 0)),
                    Token("ExpressionNode", "ExpressionNode", Location(0, 0)),
                )
            )
        )
    }
}