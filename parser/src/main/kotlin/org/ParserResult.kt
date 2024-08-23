package org

import org.astnode.ProgramNode

class ParserResult {
    val errors = mutableListOf<String>()
    var programNode: ProgramNode? = null

    fun addError(message: String) {
        errors.add(message)
    }

    fun hasErrors() = errors.isNotEmpty()
}
