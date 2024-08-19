import org.common.astnode.ASTNode
import org.common.astnode.ProgramNode
import org.common.astnode.astnodevisitor.ASTNodeVisitor
import org.common.astnode.astnodevisitor.types.VisitorResult
import org.common.astnode.statementnode.AssignmentNode
import org.common.astnode.statementnode.PrintStatementNode
import org.common.astnode.statementnode.VariableDeclarationNode
import org.common.astnode.expressionnode.BinaryExpressionNode
import org.common.astnode.expressionnode.IdentifierNode
import org.common.astnode.expressionnode.LiteralNode
import org.common.astnode.expressionnode.LiteralValue

class MockInterpreterVisitor : ASTNodeVisitor {
    val symbolTable: MutableMap<String, Any> = mutableMapOf()
    val printsList: MutableList<Any> = mutableListOf()

    override fun visit(node: ASTNode): VisitorResult {
        return when (node) {
            is ProgramNode -> visitProgramNode(node)
            is AssignmentNode -> visitAssignmentNode(node)
            is PrintStatementNode -> visitPrintStatementNode(node)
            is VariableDeclarationNode -> visitVariableDeclarationNode(node)
            is LiteralNode -> visitLiteralNode(node)
            is IdentifierNode -> visitIdentifierNode(node)
            is BinaryExpressionNode -> visitBinaryExpressionNode(node)
            else -> throw UnsupportedOperationException("Unsupported node: ${node::class}")
        }
    }


    override fun visitProgramNode(node: ProgramNode): VisitorResult {
        val statements = node.statements
        statements.forEach { it.accept(this) }
        return VisitorResult.MapResult(symbolTable)
    }

    override fun visitAssignmentNode(node: AssignmentNode): VisitorResult {
        val variableIdentifier = node.identifierNode
        val value = node.value.accept(this) as VisitorResult.LiteralValueResult
        symbolTable[variableIdentifier.name] = value
        return VisitorResult.MapResult(symbolTable)
    }

    override fun visitPrintStatementNode(node: PrintStatementNode): VisitorResult {
        val value = node.value.accept(this) as VisitorResult.LiteralValueResult
        when (value.value) {
            is LiteralValue.StringValue -> printsList.add((value.value as LiteralValue.StringValue).value) // printeo el valor, del literalValue que está en el literalValueResult.
            is LiteralValue.NumberValue -> printsList.add((value.value as LiteralValue.NumberValue).value)
        }
        return VisitorResult.Empty
    }

    override fun visitVariableDeclarationNode(node: VariableDeclarationNode): VisitorResult {
        val variableIdentifier = node.identifier
        val value = node.init.accept(this) as VisitorResult.LiteralValueResult
        symbolTable[variableIdentifier.name] = value
        return VisitorResult.MapResult(symbolTable)
    }

    override fun visitLiteralNode(node: LiteralNode): VisitorResult {
        // Devuelvo el valor tal cual, para que pueda ser usado en su contexto(asignacion o expresion)
        return VisitorResult.LiteralValueResult(node.value)

    }

    override fun visitIdentifierNode(node: IdentifierNode): VisitorResult {
        val value = symbolTable[node.name]
        if (value != null) {
            return when (value) {
                //devuelvo el valor q tiene asignado, para que pueda ser usado en su contexto(asignacion/printeo/expresion)
                is LiteralValue.StringValue -> VisitorResult.LiteralValueResult(value)
                is LiteralValue.NumberValue -> VisitorResult.LiteralValueResult(value)
                else -> throw UnsupportedOperationException("Unsupported type: ${value::class}")
            }
        } else {
            throw Exception("Variable ${node.name} not declared")
        }
    }

    override fun visitBinaryExpressionNode(node: BinaryExpressionNode): VisitorResult {
        val leftResult = node.left.accept(this) as VisitorResult.LiteralValueResult
        val rightResult = node.right.accept(this) as VisitorResult.LiteralValueResult

        val leftValue = leftResult.value
        val rightValue = rightResult.value

        val resultLiteralValue: LiteralValue = when (node.operator) {
            "+" -> {
                when {
                    (leftValue is LiteralValue.StringValue) || (rightValue is LiteralValue.StringValue) ->
                        LiteralValue.StringValue(leftValue.toString() + rightValue.toString())

                    leftValue is LiteralValue.NumberValue && rightValue is LiteralValue.NumberValue ->
                        LiteralValue.NumberValue(leftValue.value.toDouble() + rightValue.value.toDouble())

                    else -> throw UnsupportedOperationException("Unsupported types for +")
                }
            }

            "-" -> {
                when {
                    leftValue is LiteralValue.NumberValue && rightValue is LiteralValue.NumberValue ->
                        LiteralValue.NumberValue(leftValue.value.toDouble() - rightValue.value.toDouble())

                    else -> throw UnsupportedOperationException("Unsupported types for -")
                }
            }

            "*" -> {
                when {
                    leftValue is LiteralValue.NumberValue && rightValue is LiteralValue.NumberValue ->
                        LiteralValue.NumberValue(leftValue.value.toDouble() * rightValue.value.toDouble())

                    else -> throw UnsupportedOperationException("Unsupported types for *")
                }
            }

            "/" -> {
                when {
                    rightValue is LiteralValue.NumberValue && rightValue.value.toDouble() == 0.0 ->
                        throw ArithmeticException("Division by zero")

                    leftValue is LiteralValue.NumberValue && rightValue is LiteralValue.NumberValue ->
                        LiteralValue.NumberValue(leftValue.value.toDouble() / rightValue.value.toDouble())

                    else -> throw UnsupportedOperationException("Unsupported types for /")
                }
            }

            else -> {
                throw UnsupportedOperationException("Unsupported operator: ${node.operator}")
            }
        }

        return VisitorResult.LiteralValueResult(resultLiteralValue)
    }
}

