package org.astnode.statementnode

import org.Location
import org.Token
import org.astnode.ASTNode
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.expressionnode.ExpressionNode
import org.astnode.expressionnode.Literal
import org.astnode.expressionnode.LiteralValue

class VariableDeclarationNode(
    override val type: String,
    override val location: Location,
    override val expression: ExpressionNode,
    val identifier: String,
    val init: ExpressionNode,
    private val kind: String
) : StatementNode {
    override fun accept(visitor: ASTNodeVisitor) {
        visitor.visitVariableDeclarationNode(this)
    }

    override fun generate(tokens: List<Token>): ASTNode {
        return VariableDeclarationNode(
            "VariableDeclarationNode",
            tokens[0].location,
            Literal(
                type = "String",
                location = tokens[5].location,
                value = LiteralValue.StringValue(tokens[5].value)
            ),
            tokens[1].value,
            Literal(
                type = "String",
                location = tokens[5].location,
                value = LiteralValue.StringValue(tokens[5].value)
            ),
            tokens[3].value
        )
    }

    override val formula: List<String> = listOf(
        "DeclarationToken",
        "IdentifierToken",
        "ColonToken",
        "TypeToken",
        "AssignationToken",
        "ExpressionToken",
        "SemicolonToken"
    )

    constructor() : this(
        type = "VariableDeclarationNode",
        location = Location(0, 0),
        expression = Literal("String", Location(0, 0), LiteralValue.StringValue("")),
        identifier = "",
        init = Literal("String", Location(0, 0), LiteralValue.StringValue("")),
        kind = ""
    )
}
