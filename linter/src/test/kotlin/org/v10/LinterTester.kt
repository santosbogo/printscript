package org.v10

import TestReader
import org.Lexer
import org.Linter
import org.LinterFactory
import org.Parser
import org.astnode.ProgramNode
import org.junit.jupiter.api.Test
import java.io.File

class LinterTester {
    @Test
    fun testSingleWarning() {
        val file = File("src/test/resources/examples/namingFormatExample2.txt")

        // me devuelve el codigo que entra, la warning q tiene q devolver, y si debería funcionar.
        val reader = TestReader()
        val (code, expectedWarning, shouldSucceed) = reader.readTokens(file.path)

        // meto el codigo en el lexer, obtengo tokens
        val lexer = Lexer()
        val lexerResult = lexer.tokenize(code)

        // meto tokens en el parser, obtengo los nodos.
        val parser = Parser()
        val parserResult = parser.parse(lexerResult.tokens)
        val programNode = parserResult.programNode!!

        val linterFactory = LinterFactory()
        val linter = linterFactory.createDefaultLinter("src/test/kotlin/org/jsons/defaultJson.json")

        // compare the linter output with the expectedWarning.
        compareResults(linter, code, programNode, expectedWarning, shouldSucceed)
    }

    @Test
    fun testMultipleWarnings() {
        val dir = File("src/test/resources/examples")

        // declaro todas las clases q voy a usar.
        val reader = TestReader()
        val lexer = Lexer()

        // para cada archivo de texto, corro el test. Me permite correr varios tests automaticos.
        dir.listFiles {
            file ->
            file.isFile && file.extension == "txt" // debe ser un txt y un file.
        }?.forEach {
            file ->
            val (code, expectedWarnings, shouldSucceed) = reader.readTokens(file.path)
            val lexerResult = lexer.tokenize(code)

            val parser = Parser()
            val parserResult = parser.parse(lexerResult.tokens)
            val programNode = parserResult.programNode!!

            val linterFactory = LinterFactory()
            val linter = linterFactory.createDefaultLinter("src/test/kotlin/org/jsons/defaultJson.json")

            compareResults(linter, code, programNode, expectedWarnings, shouldSucceed)
        }
    }

    private fun compareResults(
        linter: Linter,
        code: String,
        programNode: ProgramNode,
        expectedWarnings: List<String>,
        shouldSucceed: Boolean
    ) {
        val reportList = linter.lint(programNode).getList()
        if (!shouldSucceed) {
            assert(false) { "Expected an error but test passed for code $code" }
        }

        // voy chequeando q los warnings q me devuelve el linter sean los q espero.
        expectedWarnings.forEachIndexed() {
            index, expectedWarning ->
            assert(reportList[index] == expectedWarning) {
                "Mismatch in code \"$code\": expected \"$expectedWarning\", found \"${reportList[index]}\""
            }
        }
    }
}
