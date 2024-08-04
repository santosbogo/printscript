package org.parser.astnode.expression

import org.utils.Location

class Identifier(
    override val type: String,
    override val location: Location,
    val name: String,
    val dataType: String
) : Expression