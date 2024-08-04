package org.parser.astnode

import org.parser.astnode.expression.Expression
import org.parser.astnode.expression.Identifier
import org.utils.Location

class VariableDeclarator(
    private val type: String,
    private val location: Location,
    private val id: Identifier,
    private val init: Expression
) {
}