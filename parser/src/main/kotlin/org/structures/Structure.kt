package org.structures

import org.iterator.PrintScriptIterator
import org.Token

// Meant to be implemented by structures like if, for, while
interface Structure {
    val type: String
    fun getTokens(
        tokenIterator: PrintScriptIterator<Token>,
        buffer: ArrayList<Token>
    ): ArrayList<Token>
    fun checkStructure(str: String): Boolean
}
