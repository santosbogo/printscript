package org.astnodebuilder

import org.Parser
import org.ParserResult
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
    val ifElseStructure = IfElseStructure()

    override fun generate(tokens: List<Token>): ASTNode {
        val ifElseTokens = ifElseStructure.separateIfElse(tokens)
        val ifTokens = ifElseTokens.first
        val elseTokens = ifElseTokens.second

        val ifStatements = checkIfError(Parser().parse(ifTokens.subList(5, ifTokens.size - 1)))
        val booleanExpression = ExpressionNodeBuilder().generate(listOf(ifTokens[2])) as ExpressionNode

        val ifNode = IfNode(
            type = "IfNode",
            location = ifTokens[0].location,
            boolean = BooleanExpressionNode("BooleanExpressionNode", ifTokens[2].location, booleanExpression),
            ifStatements = ifStatements,
        )

        if (hasElse(elseTokens)) { // If there is an else statement
            val elseStatements = checkIfError(Parser().parse(elseTokens.subList(2, elseTokens.size - 1)))

            val elseNode = ElseNode(
                type = "ElseNode",
                location = elseTokens[0].location,
                elseStatements = elseStatements,
            )
            return CompleteIfNode(
                type = "CompleteIfNode",
                location = ifTokens[0].location,
                ifNode = ifNode,
                elseNode = elseNode,
            )
        }
        return ifNode
    }

    private fun hasElse(tokens: List<Token>): Boolean {
        return !tokens.isEmpty()
    }

    override fun checkFormula(tokensString: String): Boolean {
        val pattern = "^IfToken\\s+OpenParenthesisToken\\s+BooleanToken\\s+CloseParenthesisToken\\s+OpenBraceToken\\s+.*?\\s+CloseBraceToken$"
        return Regex(pattern).matches(tokensString)
    }

    private fun checkIfError(result: ParserResult): List<ASTNode> {
        if (result.errors.isNotEmpty()) {
            throw Exception(result.errors.joinToString("\n"))
        }
        return result.programNode!!.statements
    }
}
