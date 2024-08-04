package org.parser

import org.token.Token
import org.parser.astnode.ASTNode
import org.tokenTypes.SemicolonType

class AST(private val tokens: List<Token>) {
    private val statements = ArrayList<ASTNode>();
    private val buffer = ArrayList<Token>();

    fun parse(): ArrayList<ASTNode> {
        for (token in tokens) {
            buffer.add(token);
            if (token.type == SemicolonType()) {
                statements.add(getNodes(buffer));
                buffer.clear();
            }
        }

        if (buffer.isNotEmpty()) {
            throw Exception("Unexpected end of input. Missing semicolon at the end of the file.");
        }

        return statements;
    }

    private fun getNodes(buffer: ArrayList<Token>): ASTNode {
        TODO("Not yet implemented");
    }
}