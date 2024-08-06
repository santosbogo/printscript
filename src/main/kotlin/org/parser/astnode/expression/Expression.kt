package org.parser.astnode.expression

import org.Location

interface Expression {
    val type: String;
    val location: Location;
}