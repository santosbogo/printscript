package org

import org.junit.jupiter.api.Test
import java.io.File

class ParserSemanticTesterV10 {

    @Test
    fun testSingleFile() {
        val lexer = Lexer(LexiconFactory().createLexiconV10())
        val parser = ParserFactory.createParserV10()
        val reader = TestReader()
        val file = File("src/test/resources/examples-v10/variabledeclaration.txt")
        val (code, nodes, shouldSucceed) = reader.readTokens(file.path)
        val lexerResult = lexer.tokenize(code)

        try {
            val ast = parser.parse(lexerResult.tokens)
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
