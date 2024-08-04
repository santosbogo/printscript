package org.parser.astnode.expression

import org.utils.Location

interface Expression {
    val type: String;
    val location: Location;
}