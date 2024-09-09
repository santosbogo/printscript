package org.structures

import org.Location
import org.Token
import org.expressionfactory.PatternFactory

class IfElseStructure : Structure {
    override val type = "IfToken"

    override fun getTokens(tokenIterator: Iterator<Token>, buffer: ArrayList<Token>): ArrayList<Token> {
        val stack = ArrayDeque<Token>()

        while (tokenIterator.hasNext()) {
            val token = tokenIterator.next()
            buffer.add(token)

            when (token.type) {
                "OpenBraceToken" -> stack.add(token)
                "CloseBraceToken" -> {
                    stack.removeLast()
                    if (stack.isEmpty()) { // if 'if' block completed. Check if there is an else
                        if (checkIfElse(tokenIterator)) {
                            buffer.add(Token("Separator", "", Location(0, 0))) // Add a separator to split the if and else
                            getTokens(tokenIterator, buffer) // capture the 'else' block.
                        }
                        return buffer // return entire if/ifElse block of tokens
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

    private fun checkIfElse(tokenIterator: Iterator<Token>): Boolean {
        // im stood in the last brace of the if block. Check if there is an else
        if (!tokenIterator.hasNext()) {
            return false // To avoid out of bounds exception
        }
        val nextToken = tokenIterator.next()
        return nextToken.type == "ElseToken" // Check if the next token is an else token
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

        return Pair(ifTokens.subList(5, ifTokens.size - 1), elseTokens)
    }
}
