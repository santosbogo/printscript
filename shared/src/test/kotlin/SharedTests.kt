import org.Location
import org.astnode.expressionnode.BinaryExpressionNode
import org.astnode.expressionnode.IdentifierNode
import org.astnode.expressionnode.LiteralNode
import org.astnode.expressionnode.LiteralValue
import org.junit.jupiter.api.Test

class SharedTests {

    @Test
    fun testShared() {
        val symbolTable: MutableMap<String, Pair<String, LiteralValue>> = mutableMapOf("x" to Pair("let", LiteralValue.NumberValue(5)))
        val identifierNode = IdentifierNode(
            "IdentifierNode",
            Location(1, 2),
            "x",
            "dataType",
            "let"
        )
        val literalNode = LiteralNode(
            "LiteralNode",
            Location(1, 2),
            LiteralValue.StringValue("hello")
        )
        val binaryNode = BinaryExpressionNode(
            "BinaryExpressionNode",
            Location(0, 0),
            literalNode,
            literalNode,
            "+"
        )
        val nodes = listOf(identifierNode, literalNode, binaryNode)
        nodes.forEach { node ->
            node.type
            node.location
        }
        val numberLiteral = LiteralNode(
            "LiteralNode",
            Location(1, 2),
            LiteralValue.NumberValue(5)
        )
        numberLiteral.toString()
        literalNode.toString()
        identifierNode.getType(symbolTable)
        identifierNode.dataType
    }
}
