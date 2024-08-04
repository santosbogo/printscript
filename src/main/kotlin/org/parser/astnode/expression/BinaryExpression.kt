package org.parser.astnode.expression

import org.utils.Location

class BinaryExpression(
    val type: String,
    val location: Location,
    val left: Expression,
    val right: Expression,
    val operator: String
) {

}