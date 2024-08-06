package org.parser.astnode.statementnode

import org.Location
import org.lexer.Token
import org.parser.astnode.ASTNode
import org.parser.astnode.astnodevisitor.ASTNodeVisitor
import org.parser.astnode.expressionnode.ExpressionNode
import org.parser.astnode.expressionnode.Identifier
import org.parser.astnode.expressionnode.Literal
import org.parser.astnode.expressionnode.LiteralValue
import org.parser.astnode.statementnode.util.Utils

class AssignmentNode(
    override val type: String,
    override val location: Location,
    override val expression: ExpressionNode,
    val identifier: Identifier
) : StatementNode {
    override val formula: List<String> = listOf("IdentifierToken, AssignmentToken, ExpressionToken, SemicolonToken")

    constructor() : this (
        type = "AssignmentNode",
        location = Location(0, 0),
        expression = Literal("String", Location(0, 0), LiteralValue.StringValue("")),
        identifier = Identifier("Identifier", Location(0, 0), "", "String")
    )

    override fun generate(tokens : List<Token>): ASTNode {
        val dataType : LiteralValue = Utils().getLiteralValue(tokens[2])
        return AssignmentNode(
            "AssignmentNode",
            tokens[0].location,
            Literal( // Por ahora tiene un literal, pero podría ser un identifier o binaryExpression
                type = tokens[2].type,
                location = tokens[2].location,
                value = dataType),
            Utils().generateIdentifier(tokens[0], dataType.getType()))
    }


    override fun accept(visitor: ASTNodeVisitor) {
        TODO("Not yet implemented")
    }
}