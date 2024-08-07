package org.parser

import org.lexer.Token
import org.parser.astnode.ASTGenerator
import org.parser.astnode.ASTNode

class Parser {
    fun parse(tokens: List<Token>): ArrayList<ASTNode> {
        // Parse a list of tokens into a list of ASTNodes
        // It should check for syntax errors and semantic errors.

        // List of statements. Each statement is a mini ASTNode.
        val statements = ArrayList<ASTNode>()

        // Buffer to store tokens until a statement is complete by a semicolon
        val buffer = ArrayList<Token>()

        // Map of identifiers to their types. Used for type checking and declaring existing variables
        val identifiers = HashMap<String, String>()

        for (token in tokens) {
            buffer.add(token)
            if (token.type == "SemicolonToken") {
                val node: ASTNode = ASTGenerator().generate(buffer)
                statements.add(node)
                buffer.clear()
            }
        }

        if (buffer.isNotEmpty()) {
            // If the buffer is not empty, it means the last statement is missing a semicolon.
            throw Exception("Unexpected end of input. Missing semicolon at the end of the file.")
        }

        return statements // Should instead the ProgramNode (root of AST)
    }
}
