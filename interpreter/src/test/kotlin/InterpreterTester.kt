import org.Interpreter
import org.common.astnode.statementnode.AssignmentNode
import org.common.astnode.statementnode.PrintStatementNode
import org.common.astnode.statementnode.VariableDeclarationNode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.shared.Location
import org.shared.astnode.ProgramNode
import org.shared.astnode.expressionnode.IdentifierNode
import org.shared.astnode.expressionnode.LiteralNode
import org.shared.astnode.expressionnode.LiteralValue

class InterpreterTester {

    @Test
    fun testInterpretAssignment() {
        // Create a mock ASTNode representing an assignment
        val variableDeclarationNode = VariableDeclarationNode(
            "VariableDeclarationNode",
            Location(1, 1),
            IdentifierNode("IdentifierNode", Location(1, 1), "x", "Number"),
            LiteralNode("LiteralNode",Location(1, 1), LiteralValue.NumberValue(42)),
            "let"
        )

        val printStatementNode = PrintStatementNode(
            "PrintStatementNode",
            Location(1, 1),
            LiteralNode("LiteralNode", Location(1, 1), LiteralValue.NumberValue(42))
        )

        val programNode = ProgramNode("ProgramNode", Location(1, 1), listOf(variableDeclarationNode, printStatementNode))

        val interpreter = Interpreter()
        interpreter.interpret(programNode)

        // Verify the symbol table contains the correct value
        val symbolTable = interpreter.getSymbolTable()
        assertEquals(42, symbolTable["x"])
    }

}