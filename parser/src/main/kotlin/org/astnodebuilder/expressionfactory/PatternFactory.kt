package org.astnodebuilder.expressionfactory

class PatternFactory {
    companion object {
        fun getExpressionPattern(): String {
            //id or literal token, added by more of these.
            return """^(IdentifierToken|StringToken|NumberToken)(\s*(PlusToken|MinusToken|MultiplyToken|DivisionToken)\s*(IdentifierToken|StringToken|NumberToken))*$"""
        }
    }
}