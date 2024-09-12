package org

class LexiconFactory {
    fun createLexiconV10(): Lexicon {
        return Lexicon(
            listOf(
                "let" to "DeclarationToken",

                "=" to "AssignationToken",
                ";" to "SemicolonToken",
                ":" to "ColonToken",
                "\\+" to "PlusToken",
                "\\-" to "MinusToken",
                "\\*" to "MultiplyToken",
                "/" to "DivisionToken",
                "\\(" to "OpenParenthesisToken",
                "\\)" to "CloseParenthesisToken",

                "println" to "PrintToken",
                "number" to "TypeToken",
                "string" to "TypeToken",

                "[0-9]+" to "NumberToken",
                "[0-9]+\\.[0-9]+" to "NumberToken",
                "\".*\"" to "StringToken",
                "\'.*\'" to "StringToken",

                "[a-zA-Z_][a-zA-Z0-9_]*" to "IdentifierToken"
            )
        )
    }

    fun createLexiconV11(): Lexicon {
        return Lexicon(
            listOf(
                "let" to "DeclarationToken",
                "const" to "DeclarationToken",

                "=" to "AssignationToken",
                ";" to "SemicolonToken",
                ":" to "ColonToken",
                "\\+" to "PlusToken",
                "\\-" to "MinusToken",
                "\\*" to "MultiplyToken",
                "/" to "DivisionToken",
                "\\(" to "OpenParenthesisToken",
                "\\)" to "CloseParenthesisToken",
                "\\{" to "OpenBraceToken",
                "\\}" to "CloseBraceToken",

                "println" to "PrintToken",
                "if" to "IfToken",
                "else" to "ElseToken",
                "readInput" to "ReadInputToken",
                "readEnv" to "ReadEnvironmentToken",

                "number" to "TypeToken",
                "string" to "TypeToken",
                "boolean" to "TypeToken",

                "true" to "BooleanToken",
                "false" to "BooleanToken",
                "[0-9]+" to "NumberToken",
                "[0-9]+\\.[0-9]+" to "NumberToken",
                "\".*\"" to "StringToken",
                "\'.*\'" to "StringToken",

                "[a-zA-Z_][a-zA-Z0-9_]*" to "IdentifierToken"
            )
        )
    }
}
