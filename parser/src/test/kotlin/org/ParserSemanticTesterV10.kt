package org

import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileInputStream
import java.io.StringReader

class ParserSemanticTesterV10 {

    @Test
    fun testSingleFile() {
        val reader = TestReader()
        val file = File("src/test/resources/examples-v10/variabledeclaration.txt")

        val (code, solution, shouldSucceed) = reader.readTokens(file.path)
        val lexer = Lexer(LexiconFactory().createLexiconV10(), StringReader(code))
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
