import org.Interpreter
import org.Lexer
import org.Location
import org.Parser
import org.astnode.ProgramNode
import org.astnode.expressionnode.IdentifierNode
import org.astnode.expressionnode.LiteralNode
import org.astnode.expressionnode.LiteralValue
import org.astnode.statementnode.PrintStatementNode
import org.astnode.statementnode.VariableDeclarationNode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class InterpreterTester {

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

        val interpreter = Interpreter()

        val interpreterResult = interpreter.interpret(programNode)

        val printsList = interpreterResult.printsList
        assertEquals(printsList, listOf("42"))
    }

    private fun interpretAndCaptureOutput(input: String): String {
        val lexer = Lexer()
        val parser = Parser()
        val interpreter = Interpreter()

        // Perform the interpretation
        val lexerResult = lexer.tokenize(input)
        val parserResult = parser.parse(lexerResult.tokens)
        val interpreterResult = interpreter.interpret(parserResult.programNode!!)

        return interpreterResult.printsList.joinToString(separator = "")
    }

    @Test
    fun testPrintlnWithNumber() {
        val input = "println(4);"
        val output = interpretAndCaptureOutput(input)
        assertEquals("4", output)
    }

    @Test
    fun testPrintlnWithString() {
        val input = "let a: string = 'hola'; println(a);"
        val output = interpretAndCaptureOutput(input)
        assertEquals("hola", output)
    }

    @Test
    fun testPrintlnWithStringAndQuotes() {
        val input = "println(\"hola\");"
        val output = interpretAndCaptureOutput(input)
        assertEquals("hola", output)
    }

    @Test
    fun testPrintlnWithVariableAssignment() {
        val input = "let b: number = 10; b = 5; println(b);"
        val output = interpretAndCaptureOutput(input)
        assertEquals("5", output)
    }

    @Test
    fun testPrintlnWithStringConcatenation() {
        val input = "let a: string = 'hola'; let b: number = 5; println(a + b);"
        val output = interpretAndCaptureOutput(input)
        assertEquals("hola5", output)
    }

    @Test
    fun testPrintlnWithAddition() {
        val input = "println(1 + 4);"
        val output = interpretAndCaptureOutput(input)
        assertEquals("5", output)
    }

    @Test
    fun testPrintlnWithSubtraction() {
        val input = "println(5 - 1);"
        val output = interpretAndCaptureOutput(input)
        assertEquals("4", output)
    }

    @Test
    fun testPrintlnWithMultiplication() {
        val input = "println(5 * 2);"
        val output = interpretAndCaptureOutput(input)
        assertEquals("10", output)
    }

    @Test
    fun testPrintlnWithDivision() {
        val input = "println(10 / 2);"
        val output = interpretAndCaptureOutput(input)
        assertEquals("5", output)
    }
}
