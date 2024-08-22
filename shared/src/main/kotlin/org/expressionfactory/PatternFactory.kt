package org.expressionfactory

class PatternFactory {
    companion object {
        fun getBinaryExpressionPattern(): String {
            // id or literal token, added by more of these.
            return """^(IdentifierToken|StringToken|NumberToken)(\s*(PlusToken|MinusToken|MultiplyToken|DivisionToken)\s*(IdentifierToken|StringToken|NumberToken))*$"""
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
