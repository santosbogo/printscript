package org.structures

import org.Token

// Meant to be implemented by structures like if, for, while
interface Structure {
    val type: String
    fun getTokens(tokens: List<Token>, index: Int, buffer: ArrayList<Token>): ArrayList<Token>
    fun checkStructure(str: String): Boolean
}
