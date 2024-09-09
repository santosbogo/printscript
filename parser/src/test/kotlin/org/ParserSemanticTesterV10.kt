package org

import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileInputStream

class ParserSemanticTesterV10 {

    @Test
    fun testSingleFile() {
        val reader = TestReader()
        val file = File("src/test/resources/examples-v10/variabledeclaration.txt")
        val (code, nodes, shouldSucceed) = reader.readTokens(file.path)
        val lexer = Lexer(LexiconFactory().createLexiconV10(), FileInputStream(code))
        val parser = ParserFactory.createParserV10(lexer)

        try {
            val astNodes = parser.collectAllASTNodes()
            if (!shouldSucceed) {
                assert(false) { "Expected an error but test passed for file ${file.name}" }
            }
        } catch (e: Exception) {
            if (shouldSucceed) {
                assert(false) { "Unexpected error in file ${file.name}: ${e.message}" }
            }
        }
    }
}
