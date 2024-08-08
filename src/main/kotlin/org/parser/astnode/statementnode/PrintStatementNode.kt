package org.parser.astnode.statementnode

import org.Location
import org.lexer.Token
import org.parser.astnode.ASTNode
import org.parser.astnode.astnodevisitor.ASTNodeVisitor
import org.parser.astnode.expressionnode.ExpressionNode
import org.parser.astnode.expressionnode.Literal
import org.parser.astnode.expressionnode.LiteralValue

class PrintStatementNode(
    override val type: String,
    override val location: Location,
    override val expression: ExpressionNode
) : StatementNode {
    override fun accept(visitor: ASTNodeVisitor) {
        visitor.visit(this)
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
