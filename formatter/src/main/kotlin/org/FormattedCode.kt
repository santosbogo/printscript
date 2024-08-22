package org

class FormattedCode(private val code: String) {

    fun getCode(): String {
        return code
    }

    override fun toString(): String {
        return code
    }
}
