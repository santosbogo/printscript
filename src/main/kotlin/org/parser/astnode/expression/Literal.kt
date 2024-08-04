package org.parser.astnode.expression

import org.utils.Location

class Literal(
    override val type: String,
    override val location: Location,
    val value: String
) : Expression
