package org.astnodebuilder

import org.Parser
import org.Token
import org.astnode.ASTNode
import org.astnode.expressionnode.BooleanExpressionNode
import org.astnode.expressionnode.ExpressionNode
import org.astnode.statementnode.CompleteIfNode
import org.astnode.statementnode.ElseNode
import org.astnode.statementnode.IfNode
import org.structures.IfElseStructure

class IfNodeBuilder : ASTNodeBuilder {
    override val formula: String = "IfToken OpenParenthesisToken BooleanToken CloseParenthesisToken OpenBraceToken ExpressionNode CloseBraceToken " +
        "ElseToken OpenBraceToken ExpressionNode CloseBraceToken"
    private val ifElseStructure = IfElseStructure()

    override fun generate(tokens: List<Token>, parser: Parser): ASTNode {
        val ifElseTokens = ifElseStructure.separateIfElse(tokens)
        val ifTokens = ifElseTokens.first.iterator()
        val elseTokens = getElseTokens(ifElseTokens.second).iterator()

        val ifStatements = iteratorToList(ifTokens, parser)
        val booleanExpression = ExpressionNodeBuilder().generate(ifTokens.asSequence().toList().subList(2, 3), parser) as ExpressionNode

        val ifNode = IfNode(
            type = "IfNode",
            location = ifTokens.asSequence().toList()[0].location,
            boolean = BooleanExpressionNode("BooleanExpressionNode", ifTokens.asSequence().toList()[2].location, booleanExpression),
            ifStatements = ifStatements,
        )

        if (elseTokens.hasNext()) { // If there is an else statement
            val elseStatements = iteratorToList(elseTokens, parser)

            val elseNode = ElseNode(
                type = "ElseNode",
                location = elseTokens.asSequence().toList()[0].location,
                elseStatements = elseStatements,
            )
            return CompleteIfNode(
                type = "CompleteIfNode",
                location = ifTokens.asSequence().toList()[0].location,
                ifNode = ifNode,
                elseNode = elseNode,
            )
        }
        return ifNode
    }

    private fun getElseTokens(tokens: List<Token>): List<Token> {
        if (tokens.isNotEmpty()) {
            // take out else & {}
            return tokens.subList(2, tokens.size-1)
        }
        return emptyList()
    }

    private fun iteratorToList(iterator: Iterator<Token>, parser: Parser): List<ASTNode> {
        val list = mutableListOf<ASTNode>()
        while (iterator.hasNext()) {
            list.add(parser.parse(iterator))
        }
        return list

    }

    override fun checkFormula(tokensString: String): Boolean {
        val pattern = "^IfToken\\s+OpenParenthesisToken\\s+(BooleanToken|IdentifierToken)\\s+CloseParenthesisToken\\s+OpenBraceToken\\s+.*\\s+CloseBraceToken$"
        return Regex(pattern).matches(tokensString)
    }

}
