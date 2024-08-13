import org.Interpreter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.shared.Location
import org.shared.astnode.expressionnode.IdentifierNode
import org.shared.astnode.expressionnode.LiteralNode
import org.shared.astnode.expressionnode.LiteralValue
import org.common.astnode.statementnode.AssignmentNode

class InterpreterTester {

    @Test
    fun testInterpretAssignment() {
        // Create a mock ASTNode representing an assignment
        val node = AssignmentNode(
            type = "AssignmentNode",
            location = Location(1, 1),
            identifierNode = IdentifierNode("IdentifierNode", Location(1, 1), "x", "Int"),
            value = LiteralNode("LiteralNode", Location(1, 1), LiteralValue.NumberValue(42))
        )

        val interpreter = Interpreter()
        interpreter.interpret(node)

        // Verify the symbol table contains the correct value
        val symbolTable = interpreter.getSymbolTable()
        assertEquals(42, symbolTable["x"])
    }

}