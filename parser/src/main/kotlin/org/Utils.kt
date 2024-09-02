package org

class Utils {
    fun getIfTokens(tokens: List<Token>, index: Int): ArrayList<Token> {
        var openBraces = 0
        val buffer = ArrayList<Token>()
        for (i in index until tokens.size + 1) {
            val token = tokens[i + 1]
            buffer.add(token)

            // To handle nested if statements
            if (token.type == "OpenBraceToken") {
                openBraces++
            } else if (token.type == "CloseBraceToken") {
                openBraces--
                if (openBraces == 0) { // Indicates it is the end of the if statement
                    if (checkIfElse(tokens, i)) {
                        getElseTokens(tokens, i, buffer)
                    }
                    return buffer
                }
            }
        }

        return buffer
    }

    fun getElseTokens(tokens: List<Token>, i: Int, buffer: ArrayList<Token>) {
        for (token in tokens.subList(i, tokens.size)) {
            buffer.add(token)
            if (token.type == "CloseBraceToken") {
                return
            }
        }
    }

    private fun checkIfElse(tokens: List<Token>, i: Int): Boolean {
        if (tokens.size <= i + 1) {
            return false
        } // To avoid out of bounds exception
        return tokens[i + 1].equals("ElseToken") // Check if the next token is an else token
    }

    fun getFormula(buffer: List<Token>): String {
        return buffer.joinToString(" ") { it.type }
    }
}
