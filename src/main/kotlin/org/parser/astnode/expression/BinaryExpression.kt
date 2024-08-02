package org.parser.astnode.expression

import org.Location

class BinaryExpression(
    override val type: String,
    override val location: Location,
    val left: Expression,
    val right: Expression,
    val operator: String
) : Expression