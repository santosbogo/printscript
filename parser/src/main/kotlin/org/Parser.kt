package org

import org.astnode.ASTNode
import org.astnode.ProgramNode
import org.iterator.PrintScriptIterator
import org.semanticanalysis.SemanticAnalyzer
import org.structures.Structure

class Parser(
    private val astGenerator: ASTGenerator,
    private val semanticAnalyzer: SemanticAnalyzer,
    private val supportedStructures: List<Structure>,
    private val tokenIterator: PrintScriptIterator<Token>,
) : PrintScriptIterator<ASTNode> {

    private var peekedElement: ASTNode? = null

    fun parse(tokenIterator: PrintScriptIterator<Token>): ASTNode {
        val buffer = ArrayList<Token>()

        while (tokenIterator.hasNext()) {
            val token = tokenIterator.next()!!
            buffer.add(token)
            if (checkIfStructureToken(token.type)) {
                return handleStructure(token.type, tokenIterator, buffer)
            } else if (token.type == "SemicolonToken") {
                return handleStatement(buffer)
            }
        }

        // If the buffer is not empty, there was a missing semicolon
        if (buffer.isNotEmpty()) {
            throw Exception("Unexpected end of input. Missing semicolon or brace at the end of the file.")
        }
        // never reaches this point.
        return ProgramNode("EndOfLine", Location(0, 0), emptyList())
    }

    // Simply get all the tokens concerning that structure and handle them
    private fun handleStructure(type: String, tokenIterator: PrintScriptIterator<Token>, buffer: ArrayList<Token>): ASTNode {
        supportedStructures.forEach {
            if (it.type == type) {
                it.getTokens(tokenIterator, buffer)
            }
        }
        return handleStatement(buffer)
    }

    // If the token is a semicolon, generate an AST node from the buffer and analyze it.
    private fun handleStatement(buffer: ArrayList<Token>): ASTNode {
        try {
            val node: ASTNode = astGenerator.generate(buffer, this)
            try {
                semanticAnalyzer.analyze(node)
                buffer.clear()
                return node
            } catch (e: Exception) {
                throw Exception("Semantic error: ${e.message}")
            }
        } catch (e: Exception) {
            throw Exception("Syntactic error: ${e.message}")
        }
    }

    private fun checkIfStructureToken(string: String): Boolean {
        val structureTokens = supportedStructures.map { it.type }
        return structureTokens.contains(string)
    }

    override fun hasNext(): Boolean {
        if (peekedElement != null) {
            return true
        }
        return tokenIterator.hasNext()
    }

    override fun next(): ASTNode {
        if (peekedElement != null) {
            val temp = peekedElement!!
            peekedElement = null
            return temp
        }
        // le pide al lexer tokens hasta que arma un ASTNode.
        return parse(tokenIterator)
    }

    override fun peek(): ASTNode? {
        if (hasNext()) {
            if (peekedElement != null) {
                return peekedElement
            }
            peekedElement = next()
            return peekedElement
        }
        return null
    }

    fun collectAllASTNodes(): List<ASTNode> {
        val newParser = Parser(astGenerator, semanticAnalyzer, supportedStructures, tokenIterator)
        val nodes = mutableListOf<ASTNode>()
        while (newParser.hasNext()) {
            nodes.add(newParser.next())
        }
        return nodes
    }
}
