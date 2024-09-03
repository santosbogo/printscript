package org.structures

import org.Location
import org.Token
import org.expressionfactory.PatternFactory

class IfElseStructure : Structure {
    override val type = "IfToken"

    override fun getTokens(tokens: List<Token>, index: Int, buffer: ArrayList<Token>): ArrayList<Token> {
        val stack = ArrayDeque<Token>()

        for (i in index until tokens.size + 1) {
            val token = tokens[i + 1]
            buffer.add(token)

            when (token.type) {
                "OpenBraceToken" -> stack.add(token)
                "CloseBraceToken" -> {
                    stack.removeLast()
                    if (stack.isEmpty()) {
                        if (checkIfElse(tokens, i)) {
                            buffer.add(Token("Separator", "", Location(0, 0))) // Add a separator to split the if and else
                            getTokens(tokens, i + 1, buffer) // + 1 to skip the brace
                        }
                        return buffer
                    }
                }
            }
        }

        return buffer
    }

    // Returns true if the structure is if without else
    override fun checkStructure(str: String): Boolean {
        val ifPattern = PatternFactory.getIfWithElsePattern()
        return Regex(ifPattern).matches(str)
    }

    private fun checkIfElse(tokens: List<Token>, i: Int): Boolean {
        if (tokens.size <= i + 2) {
            return false
        } // To avoid out of bounds exception
        return tokens[i + 2].type == "ElseToken" // Check if the next token is an else token
    }

    fun separateIfElse(tokens: List<Token>): Pair<List<Token>, List<Token>> {
        val ifTokens = ArrayList<Token>()
        val elseTokens = ArrayList<Token>()
        var isIf = true

        for (token in tokens) {
            if (token.type == "Separator") {
                isIf = false
                continue
            }

            if (isIf) {
                ifTokens.add(token)
            } else {
                elseTokens.add(token)
            }
        }

        return Pair(ifTokens, elseTokens)
    }
}
