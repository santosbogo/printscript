import org.common.Location
import org.common.astnode.ProgramNode
import org.common.astnode.expressionnode.IdentifierNode
import org.common.astnode.expressionnode.LiteralNode
import org.common.astnode.expressionnode.LiteralValue
import org.common.astnode.statementnode.PrintStatementNode
import org.common.astnode.statementnode.VariableDeclarationNode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class InterpreterTester {

    @Test
    fun testInterpretAssignment() {
        // Create a mock ASTNode representing an assignment
        val variableDeclarationNode = VariableDeclarationNode(
            "VariableDeclarationNode",
            Location(1, 1),
            IdentifierNode("IdentifierNode", Location(1, 1), "x", "Number"),
            LiteralNode("LiteralNode", Location(1, 1), LiteralValue.NumberValue(42)),
            "let"
        )

        val printStatementNode = PrintStatementNode(
            "PrintStatementNode",
            Location(1, 1),
            LiteralNode("LiteralNode", Location(1, 1), LiteralValue.NumberValue(42))
        )

        val programNode = ProgramNode("ProgramNode", Location(1, 1), listOf(variableDeclarationNode, printStatementNode))

        val interpreter = MockInterpreter()
        interpreter.interpret(programNode)

        // Verify the symbol table contains the correct value
        val printsList = interpreter.getPrintsList()
        assertEquals(printsList, listOf(42))
    }
}
