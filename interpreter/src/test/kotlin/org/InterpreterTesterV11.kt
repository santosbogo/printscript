package org

import org.astnode.ProgramNode
import org.astnode.expressionnode.IdentifierNode
import org.astnode.expressionnode.LiteralNode
import org.astnode.expressionnode.LiteralValue
import org.astnode.statementnode.PrintStatementNode
import org.astnode.statementnode.VariableDeclarationNode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class InterpreterTesterV11 {
    private fun interpretAndCaptureOutputV11(input: String): String {
        val lexer = LexerFactory.createLexerV11()
        val parser = ParserFactory.createParserV11()
        val interpreter = InterpreterFactory.createInterpreterV11()

        // Perform the interpretation
        val lexerResult = lexer.tokenize(input)
        val parserResult = parser.parse(lexerResult.tokens)
        val interpreterResult = interpreter.interpret(parserResult.programNode!!)

        return interpreterResult.printsList.joinToString(separator = "")
    }
    @Test
    fun testInterpretAssignment() {
        val variableDeclarationNode = VariableDeclarationNode(
            "VariableDeclarationNode",
            Location(1, 1),
            IdentifierNode("IdentifierNode", Location(1, 1), "x", "number", "let"),
            LiteralNode("LiteralNode", Location(1, 1), LiteralValue.NumberValue(42)),
            "let"
        )

        val printStatementNode = PrintStatementNode(
            "PrintStatementNode",
            Location(1, 1),
            LiteralNode("LiteralNode", Location(1, 1), LiteralValue.NumberValue(42))
        )

        val programNode = ProgramNode(
            "ProgramNode",
            Location(1, 1),
            listOf(
                variableDeclarationNode,
                printStatementNode
            )
        )

        val interpreter = InterpreterFactory.createInterpreterV11()

        val interpreterResult = interpreter.interpret(programNode)

        val printsList = interpreterResult.printsList
        Assertions.assertEquals(printsList, listOf("42"))
    }

    private fun interpretAndCaptureOutputV10(input: String): String {
        val lexer = LexerFactory.createLexerV10()
        val parser = ParserFactory.createParserV10()
        val interpreter = InterpreterFactory.createInterpreterV10()

        // Perform the interpretation
        val lexerResult = lexer.tokenize(input)
        val parserResult = parser.parse(lexerResult.tokens)
        val interpreterResult = interpreter.interpret(parserResult.programNode!!)

        return interpreterResult.printsList.joinToString(separator = "")
    }

    @Test
    fun testPrintlnWithNumber() {
        val input = "println(4);"
        val output = interpretAndCaptureOutputV10(input)
        Assertions.assertEquals("4", output)
    }

    @Test
    fun testPrintlnWithString() {
        val input = "let a: string = 'hola'; println(a);"
        val output = interpretAndCaptureOutputV10(input)
        Assertions.assertEquals("hola", output)
    }

    @Test
    fun testPrintlnWithStringAndQuotes() {
        val input = "println(\"hola\");"
        val output = interpretAndCaptureOutputV10(input)
        Assertions.assertEquals("hola", output)
    }

    @Test
    fun testPrintlnWithVariableAssignment() {
        val input = "let b: number = 10; b = 5; println(b);"
        val output = interpretAndCaptureOutputV10(input)
        Assertions.assertEquals("5", output)
    }

    @Test
    fun testPrintlnWithStringConcatenation() {
        val input = "let a: string = 'hola'; let b: number = 5; println(a + b);"
        val output = interpretAndCaptureOutputV10(input)
        Assertions.assertEquals("hola5", output)
    }

    @Test
    fun testPrintlnWithAddition() {
        val input = "println(1 + 4);"
        val output = interpretAndCaptureOutputV10(input)
        Assertions.assertEquals("5", output)
    }

    @Test
    fun testPrintlnWithSubtraction() {
        val input = "println(5 - 1);"
        val output = interpretAndCaptureOutputV10(input)
        Assertions.assertEquals("4", output)
    }

    @Test
    fun testPrintlnWithMultiplication() {
        val input = "println(5 * 2);"
        val output = interpretAndCaptureOutputV10(input)
        Assertions.assertEquals("10", output)
    }

    @Test
    fun testPrintlnWithDivision() {
        val input = "println(10 / 2);"
        val output = interpretAndCaptureOutputV10(input)
        Assertions.assertEquals("5", output)
    }

    @Test
    fun testIfNode() {
        val input = """
            if (true) {
                println('Hello');
            }
        """.trimIndent()
        val output = interpretAndCaptureOutputV11(input)
        Assertions.assertEquals("Hello", output)
    }

    @Test
    fun testIfCompleteNode() {
        val input = """
            if (false) {
                println('Hello');
            } else {
                println('World');
            }
        """.trimIndent()
        val output = interpretAndCaptureOutputV11(input)
        Assertions.assertEquals("World", output)
    }

    @Test
    fun testIfNestedNode() {
        val input = """
            if (true) {
                if (true) {
                    println('Hello');
                    if (true) {
                        println('World');
                    }
                } else {
                    println('Goodbye World');
                }
            }
        """.trimIndent()
        val output = interpretAndCaptureOutputV11(input)
        Assertions.assertEquals("HelloWorld", output)
    }
}
