package org.astnodebuilder

import org.Parser
import org.iterator.PrintScriptIterator
import org.Token
import org.astnode.ASTNode
import org.astnode.expressionnode.BooleanExpressionNode
import org.astnode.expressionnode.ExpressionNode
import org.astnode.statementnode.CompleteIfNode
import org.astnode.statementnode.ElseNode
import org.astnode.statementnode.IfNode
import org.iterator.QueueIterator
import org.structures.IfElseStructure

class IfNodeBuilder : ASTNodeBuilder {
    override val formula: String = "IfToken OpenParenthesisToken BooleanToken " +
        "CloseParenthesisToken OpenBraceToken ExpressionNode CloseBraceToken " +
        "ElseToken OpenBraceToken ExpressionNode CloseBraceToken"
    private val ifElseStructure = IfElseStructure()

    override fun generate(tokens: List<Token>, parser: Parser): ASTNode {
        val ifElseTokens = ifElseStructure.separateIfElse(tokens)
        val first = getFirstToken(ifElseTokens.first) // To save the location
        val bool = getBoolean(ifElseTokens.first)

        val ifTokens = getIfTokens(ifElseTokens.first) // Trim the if (bool) { }
        val elseTokens = getElseTokens(ifElseTokens.second) // Trim the else { }

        val ifStatements = getSubStatements(QueueIterator(ifTokens), parser)

        val booleanExpression = ExpressionNodeBuilder().generate(bool, parser) as ExpressionNode

        val ifNode = IfNode(
            type = "IfNode",
            location = first.location,
            boolean = BooleanExpressionNode(
                "BooleanExpressionNode",
                bool[0].location,
                booleanExpression
            ),
            ifStatements = ifStatements,
        )

        if (elseTokens.isNotEmpty()) { // If there is an else statement
            val elseStatements = getSubStatements(QueueIterator(elseTokens), parser)
            val first = getFirstToken(elseTokens)
            val elseNode = ElseNode(
                type = "ElseNode",
                location = first.location,
                elseStatements = elseStatements,
            )
            return CompleteIfNode(
                type = "CompleteIfNode",
                location = first.location,
                ifNode = ifNode,
                elseNode = elseNode,
            )
        }
        return ifNode
    }

    private fun getFirstToken(tokens: List<Token>): Token {
        if (tokens.isNotEmpty()) {
            return tokens[0]
        }
        throw Exception("No statements inside if")
    }

    private fun getIfTokens(tokens: List<Token>): List<Token> {
        if (tokens.isNotEmpty()) {
            return tokens.subList(5, tokens.size - 1)
        }
        return emptyList()
    }

    private fun getElseTokens(tokens: List<Token>): List<Token> {
        if (tokens.isNotEmpty()) {
            return tokens.subList(2, (tokens.size - 1))
        }
        return emptyList()
    }

    private fun getBoolean(tokens: List<Token>): List<Token> {
        return tokens.asSequence().toList().subList(2, 3)
    }

    private fun getSubStatements(
        iterator: PrintScriptIterator<Token>,
        parser: Parser
    ): List<ASTNode> {
        val list = mutableListOf<ASTNode>()
        while (iterator.hasNext()) {
            list.add(parser.parse(iterator))
        }
        return list
    }

    override fun checkFormula(tokensString: String): Boolean {
        val pattern = "^IfToken\\s+OpenParenthesisToken\\s+(BooleanToken|IdentifierToken)" +
            "\\s+CloseParenthesisToken\\s+OpenBraceToken\\s+.*\\s+CloseBraceToken$"
        return Regex(pattern).matches(tokensString)
    }
}
