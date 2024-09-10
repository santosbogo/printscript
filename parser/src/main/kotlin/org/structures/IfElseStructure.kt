package org.structures

import org.Location
import org.iterator.PrintScriptIterator
import org.Token
import org.expressionfactory.PatternFactory

class IfElseStructure : Structure {
    override val type = "IfToken"

    override fun getTokens(tokenIterator: PrintScriptIterator<Token>, buffer: ArrayList<Token>): ArrayList<Token> {
        val stack = ArrayDeque<Token>()
        while (tokenIterator.hasNext()) {
            val token = tokenIterator.next()
            buffer.add(token!!)

            when (token.type) {
                "OpenBraceToken" -> stack.add(token)
                "CloseBraceToken" -> {
                    stack.removeLast()
                    if (stack.isEmpty()) { // if 'if' block completed. Check if there is an else
                        var token = tokenIterator.peek() // TODO (NULL POINTER EXCEPTION HERE)
                        if (checkIfElse(token)) { // FIXME ROMPEN LOS TEST DE IF testIfNode DEL INTERPRETER TESTER V11
                            buffer.add(Token("Separator", "", Location(0, 0))) // Add a separator to split the if and else
                            getTokens(tokenIterator, buffer)
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

    private fun checkIfElse(token: Token?): Boolean {
        // I'm stood in the last brace of the if block. Check if there is an else
        if (token == null) {
            return false
        }
        return token.type == "ElseToken" // Check if the next token is an else token
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
