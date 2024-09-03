package org.expressionfactory

class PatternFactory {
    companion object {
        fun getBinaryExpressionPattern(): String {
            return """^(BooleanToken|IdentifierToken|StringToken|NumberToken)(\s*(PlusToken|MinusToken|MultiplyToken|DivisionToken)\s*(IdentifierToken|StringToken|NumberToken))*$"""
        }

        fun getIfPattern(): String {
            return "^IfToken\\s+OpenParenthesisToken\\s+BooleanToken\\s+CloseParenthesisToken\\s+OpenBraceToken\\s+.*?\\s+CloseBraceToken$"
        }

        fun getIfWithElsePattern(): String {
            return "^IfToken\\s+OpenParenthesisToken\\s+BooleanToken\\s+CloseParenthesisToken\\s+OpenBraceToken\\s+.*?\\s+" +
                "CloseBraceToken\\s+ElseToken\\s+OpenBraceToken\\s+.*?\\s+CloseBraceToken$"
        }

        fun getReadInputPattern(): String {
            return "^ReadInputToken\\s+OpenParenthesisToken\\s+StringToken\\s+CloseParenthesisToken$"
        }

        fun getReadEnvironmentPattern(): String {
            return "^ReadEnvironmentToken\\s+OpenParenthesisToken\\s+StringToken\\s+CloseParenthesisToken$"
        }

        fun getNamingFormatPattern(namingPatternName: String): String {
            return when (namingPatternName) {
                "camelCase" -> {
                    // starts with a lowercase letter, followed by any number of letters or numbers, and then an uppercase letter, followed by any number of letters or numbers.
                    """^[a-z][a-zA-Z0-9]*[A-Z][a-zA-Z0-9]*$"""
                }
                "snake-case" -> {
                    // starts with a lowercase letter, followed by any number of letters or numbers, and then an underscore, followed by any number of letters or numbers.
                    """^[a-z][a-zA-Z0-9]*_[a-zA-Z0-9]*$"""
                }
                else -> {
                    throw IllegalArgumentException("Invalid naming pattern name")
                }
            }
        }
    }
}
