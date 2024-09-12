package org.interpretervisitors

import org.astnode.ASTNode
import org.astnode.astnodevisitor.VisitorHelper
import org.astnode.astnodevisitor.VisitorResult
import org.astnode.expressionnode.BinaryExpressionNode
import org.astnode.expressionnode.BooleanExpressionNode
import org.astnode.expressionnode.IdentifierNode
import org.astnode.expressionnode.LiteralNode
import org.astnode.expressionnode.LiteralValue
import org.astnode.expressionnode.ReadEnvNode
import org.astnode.expressionnode.ReadInputNode
import org.astnode.statementnode.AssignmentNode
import org.astnode.statementnode.CompleteIfNode
import org.astnode.statementnode.IfNode
import org.astnode.statementnode.PrintStatementNode
import org.astnode.statementnode.VariableDeclarationNode
import org.inputers.InputProvider
import org.printers.Printer

class InterpreterVisitorV11(
    override val printer: Printer,
    override val inputProvider: InputProvider
) : InterpreterVisitor {
    private val symbolTable: MutableMap<String, VariableTripleData> = mutableMapOf()

    private fun putSymbolTable(symbolTable: MutableMap<String, VariableTripleData>) {
        this.symbolTable.putAll(symbolTable)
    }

    override fun visit(node: ASTNode): VisitorResult {
        return when (node) {
            is AssignmentNode -> visitAssignmentNode(node)
            is PrintStatementNode -> visitPrintStatementNode(node)
            is VariableDeclarationNode -> visitVariableDeclarationNode(node)
            is LiteralNode -> visitLiteralNode(node)
            is IdentifierNode -> visitIdentifierNode(node)
            is BinaryExpressionNode -> visitBinaryExpressionNode(node)
            is BooleanExpressionNode -> visitBooleanExpressionNode(node)
            is CompleteIfNode -> visitCompleteIfNode(node)
            is IfNode -> visitIfNode(node)
            is ReadInputNode -> visitReadInputNode(node)
            is ReadEnvNode -> visitReadEnvNode(node)
            else -> throw UnsupportedOperationException("Unsupported node: ${node::class}")
        }
    }

    private fun visitAssignmentNode(node: AssignmentNode): VisitorResult {
        val variableIdentifier = node.identifier
        val value = node.value.accept(this) as VisitorResult.LiteralValueResult
        val dataType = symbolTable[variableIdentifier.name]!!.dataType

        try {
            symbolTable[variableIdentifier.name] = symbolTable[variableIdentifier.name]!!
                .changeValue(castValueAsExpected(dataType, value))
        } catch (e: Exception) {
            throw Exception(
                "Value ${value.value} " +
                    "cannot be casted to ${variableIdentifier.dataType}"
            )
        }

        return VisitorResult.MapResult(symbolTable)
    }

    private fun visitPrintStatementNode(node: PrintStatementNode): VisitorResult {
        val value = node.value.accept(this) as VisitorResult.LiteralValueResult
        when (value.value) {
            is LiteralValue.StringValue -> {
                val stringValue = (value.value as LiteralValue.StringValue).value
                val cleanedStringValue = stringValue
                    .replace("'", "")
                    .replace("\"", "")
                printer.print(cleanedStringValue)
            }
            is LiteralValue.NumberValue -> {
                printer.print((value.value as LiteralValue.NumberValue).value.toString())
            }
            is LiteralValue.BooleanValue -> {
                printer.print((value.value as LiteralValue.BooleanValue).value.toString())
            }
            else -> {
                throw Exception("Unsupported value type")
            }
        }
        return VisitorResult.Empty
    }

    private fun visitVariableDeclarationNode(node: VariableDeclarationNode): VisitorResult {
        val variableIdentifier = node.identifier
        val value = node.init.accept(this) as VisitorResult.LiteralValueResult

        try {
            val tripleData = VariableTripleData(
                variableIdentifier.kind,
                variableIdentifier.dataType,
                castValueAsExpected(variableIdentifier.dataType, value)
            )
            symbolTable[variableIdentifier.name] = tripleData
        } catch (e: Exception) {
            throw Exception(
                "Value ${value.value} " +
                    "cannot be casted to ${variableIdentifier.dataType}"
            )
        }

        return VisitorResult.MapResult(symbolTable)
    }

    private fun castValueAsExpected(
        dataType: String,
        value: VisitorResult.LiteralValueResult
    ): LiteralValue {
        if (value.value is LiteralValue.NullValue) {
            return value.value
        }

        return when (dataType) {
            "string" -> {
                value.value as LiteralValue.StringValue
            }
            "number" -> {
                value.value as LiteralValue.NumberValue
            }
            "boolean" -> {
                value.value as LiteralValue.BooleanValue
            }
            else -> {
                throw Exception("Unsupported data type")
            }
        }
    }

    private fun visitLiteralNode(node: LiteralNode): VisitorResult {
        return VisitorResult.LiteralValueResult(node.value)
    }

    private fun visitIdentifierNode(node: IdentifierNode): VisitorResult {
        val value = symbolTable[node.name]?.literalValue
        if (value != null) {
            return when (value) {
                is LiteralValue.StringValue -> VisitorResult.LiteralValueResult(value)
                is LiteralValue.NumberValue -> VisitorResult.LiteralValueResult(value)
                is LiteralValue.BooleanValue -> VisitorResult.LiteralValueResult(value)
                else -> {
                    throw Exception("Unsupported value type")
                }
            }
        } else {
            throw Exception("Variable ${node.name} not declared")
        }
    }

    private fun visitBinaryExpressionNode(node: BinaryExpressionNode): VisitorResult {
        val leftResult = node.left.accept(this) as VisitorResult.LiteralValueResult
        val rightResult = node.right.accept(this) as VisitorResult.LiteralValueResult

        val leftValue = leftResult.value
        val rightValue = rightResult.value

        val resultLiteralValue: LiteralValue = VisitorHelper().evaluateBinaryExpression(
            leftValue,
            rightValue,
            node.operator
        )

        return VisitorResult.LiteralValueResult(resultLiteralValue)
    }

    private fun visitBooleanExpressionNode(node: BooleanExpressionNode): VisitorResult {
        val bool = node.bool.accept(this) as VisitorResult.LiteralValueResult
        return VisitorResult.LiteralValueResult(bool.value)
    }

    private fun visitIfNode(node: IfNode): VisitorResult {
        val bool = node.boolean.accept(this) as VisitorResult.LiteralValueResult
        val statements = node.ifStatements
        if ((bool.value as LiteralValue.BooleanValue).value) {
            subInterpret(statements)
        }
        return VisitorResult.Empty
    }

    private fun visitCompleteIfNode(node: CompleteIfNode): VisitorResult {
        val bool = node.ifNode.boolean.accept(this) as VisitorResult.LiteralValueResult
        val ifStatements = node.ifNode.ifStatements
        val elseStatements = node.elseNode?.elseStatements
        if ((bool.value as LiteralValue.BooleanValue).value) {
            subInterpret(ifStatements)
        } else {
            subInterpret(elseStatements!!)
        }
        return VisitorResult.Empty
    }

    private fun subInterpret(statements: List<ASTNode>) {
        val interpreter = InterpreterVisitorV11(printer, inputProvider)
        interpreter.putSymbolTable(symbolTable)
        statements.forEach { it.accept(interpreter) }
    }

    private fun visitReadInputNode(node: ReadInputNode): VisitorResult {
        val message = node.message
        printer.print(message.toString())

        val input = inputProvider.input()

        return VisitorResult.LiteralValueResult(LiteralValue.StringValue(input))
    }

    private fun visitReadEnvNode(node: ReadEnvNode): VisitorResult {
        val envVar = System.getenv(node.variableName)
        return VisitorResult.LiteralValueResult(
            LiteralValue.StringValue(envVar)
        )
    }
}
