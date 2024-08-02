package org.parser.astnode

import org.Location
import org.parser.astnode.expression.Expression
import org.parser.astnode.expression.Identifier

class VariableDeclarator(
    private val type: String,
    private val location: Location,
    private val id: Identifier,
    private val init: Expression
) {
}