package org.parser

import org.tokens.Token
import org.parser.astnode.ASTNode

class Parser {
    fun parse(tokens: List<Token>): ArrayList<ASTNode> {
        val statements = ArrayList<ASTNode>()
        val buffer = ArrayList<Token>()
        val identifiers = HashMap<String, String>()

        for (token in tokens) {
            buffer.add(token)
            if (token.type == "SEMICOLON") {
                statements.add(getNodes(buffer))
                buffer.clear()
            }
        }

        if (buffer.isNotEmpty()) {
            throw Exception("Unexpected end of input. Missing semicolon at the end of the file.")
        }

        return statements
    }

    private fun getNodes(buffer: ArrayList<Token>): ASTNode {
        TODO("Not yet implemented")
    }
}