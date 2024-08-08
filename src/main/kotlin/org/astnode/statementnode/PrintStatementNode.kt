package org.astnode.statementnode

import org.Location
import org.Token
import org.astnode.ASTNode
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.expressionnode.ExpressionNode
import org.astnode.expressionnode.Literal
import org.astnode.expressionnode.LiteralValue

class PrintStatementNode(
    override val type: String,
    override val location: Location,
    override val expression: ExpressionNode
) : StatementNode {
    override fun accept(visitor: ASTNodeVisitor) {
        visitor.visitPrintStatementNode(this)
    }

    override fun generate(tokens: List<Token>): ASTNode {
        return PrintStatementNode(
            "PrintStatementNode",
            tokens[0].location,
            Literal(
                type = "String",
                location = tokens[2].location,
                value = LiteralValue.StringValue(tokens[2].value)
            )
        )
    }

    override val formula: List<String> = listOf(
        "PrintToken",
        "OpenParenthesisToken",
        "ExpressionToken", // Aca podría ir cualquier cosa, pero no se me ocurre que poner
        "CloseParenthesisToken",
        "SemicolonToken"
    )

    constructor() : this(
        type = "PrintStatementNode",
        location = Location(0, 0),
        expression = Literal("String", Location(0, 0), LiteralValue.StringValue(""))
    )
}
