package rules

import kotlin.collections.ArrayDeque

class NumberOfSpacesIndentation(private val indentation: Int) : Rule {
    override val name = "NumberOfSpacesIndentation"

    override fun applyRule(input: String): String {
        val stack = ArrayDeque<Char>()
        val buffer = StringBuilder()
        val result = StringBuilder()

        for (char in input) {
            if (char == '\n') {
                result.append('\n')
                continue
            }
            buffer.append(char)

            when (char) {
                '{' -> {
                    result.append(
                        " ".repeat(indentation * stack.size) +
                            buffer.toString().trimEnd()
                    )

                    stack.addLast('{')
                    buffer.clear()
                }

                '}' -> {
                    result.append(
                        " ".repeat(indentation * (stack.size - 1)) +
                            buffer.toString().trimEnd()
                    )
                    stack.removeLast()
                    buffer.clear()
                }

                ';' -> {
                    result.append(
                        " ".repeat(indentation * stack.size) +
                            buffer.toString().trimEnd()
                    )
                    buffer.clear()
                }

                else -> {
                    // Just append other characters
                }
            }
        }

        result.append(buffer.toString().trimEnd())

        return result.toString()
    }
}
