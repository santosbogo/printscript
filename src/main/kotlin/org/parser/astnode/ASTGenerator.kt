package org.parser.astnode

import org.lexer.Token

class ASTGenerator(private val formulaMap: List<Pair<String, List<String>>>) {

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
                "AssignmentNode" to listOf(
                    "IdentifierNode",
                    "AssignationToken",
                    "ExpressionNode",
                ),
                "DeclarationNode" to listOf(
                    "DeclarationToken",
                    "IdentifierNode",
                    "ColonToken",
                    "TypeToken",
                ),
                "ExpressionNode" to listOf(
                    "ExpressionNode",
                    "OperationToken",
                    "ExpressionNode",
                )
            )
        )
    }
}