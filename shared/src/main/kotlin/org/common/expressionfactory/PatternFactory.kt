package org.common.expressionfactory

class PatternFactory {
    companion object {
        fun getBinaryExpressionPattern(): String {
            // id or literal token, added by more of these.
            return """^(IdentifierToken|StringToken|NumberToken)(\s*(PlusToken|MinusToken|MultiplyToken|DivisionToken)\s*(IdentifierToken|StringToken|NumberToken))*$"""
        }

        fun getNamingFormatPattern(namingPatternName: String): String {
            if (namingPatternName == "camelcase") {
                // starts with a lowercase letter, followed by any number of letters or numbers, and then an uppercase letter, followed by any number of letters or numbers.
                return """^[a-z][a-zA-Z0-9]*[A-Z][a-zA-Z0-9]*$"""
            } else if (namingPatternName == "snakecase") {
                // starts with a lowercase letter, followed by any number of letters or numbers, and then an underscore, followed by any number of letters or numbers.
                return """^[a-z][a-zA-Z0-9]*_[a-zA-Z0-9]*$"""
            } else {
                throw IllegalArgumentException("Invalid naming pattern name")
            }
        }
    }
}
