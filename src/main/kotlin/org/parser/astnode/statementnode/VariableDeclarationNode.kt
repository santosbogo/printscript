package org.parser.astnode.statementnode

import org.Location
import org.lexer.Token
import org.parser.astnode.ASTNode
import org.parser.astnode.astnodevisitor.ASTNodeVisitor
import org.parser.astnode.expressionnode.ExpressionNode
import org.parser.astnode.expressionnode.Literal
import org.parser.astnode.expressionnode.LiteralValue

class VariableDeclarationNode(
    override val type: String,
    override val location: Location,
    override val expression: ExpressionNode,
    val identifier: String,
    val init: ExpressionNode,
    private val kind: String
) : StatementNode {
    override fun accept(visitor: ASTNodeVisitor) {
        TODO("Not yet implemented")
    }

    override fun generate(tokens: List<Token>): ASTNode {
        //TODO("Aca habría que pensar la estructura del buffer y asignar cada cosa a su lugar")
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
        "NumberTypeToken",
        "AssignationToken",
        "ExpressionToken",
        "SemicolonToken",
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