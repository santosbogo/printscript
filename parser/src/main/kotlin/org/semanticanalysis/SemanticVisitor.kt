package org.semanticanalysis

import org.astnode.ASTNode
import org.astnode.ProgramNode
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.VisitorHelper
import org.astnode.astnodevisitor.VisitorResult
import org.astnode.expressionnode.LiteralValue
import org.astnode.expressionnode.IdentifierNode
import org.astnode.expressionnode.LiteralNode
import org.astnode.expressionnode.BinaryExpressionNode
import org.astnode.expressionnode.ReadEnvNode
import org.astnode.expressionnode.ReadInputNode
import org.astnode.statementnode.AssignmentNode
import org.astnode.statementnode.VariableDeclarationNode

class SemanticVisitor : ASTNodeVisitor {
    val symbolTable: MutableMap<String, Pair<String, LiteralValue>> = mutableMapOf()

    override fun visit(node: ASTNode): VisitorResult {
        return when (node) {
            is ProgramNode -> visitProgramNode(node)
            is AssignmentNode -> visitAssignmentNode(node)
            is VariableDeclarationNode -> visitVariableDeclarationNode(node)
            is LiteralNode -> visitLiteralNode(node)
            is IdentifierNode -> visitIdentifierNode(node)
            is BinaryExpressionNode -> visitBinaryExpressionNode(node)
            is ReadInputNode -> visitFutureValue()
            is ReadEnvNode -> visitFutureValue()
            else -> VisitorResult.Empty
        }
    }

    private fun visitProgramNode(node: ProgramNode): VisitorResult {
        val statements = node.statements
        statements.forEach { it.accept(this) }
        return VisitorResult.MapResult(symbolTable)
    }

    private fun visitAssignmentNode(node: AssignmentNode): VisitorResult {
        val variableIdentifier = node.identifier
        val value = node.value.accept(this) as VisitorResult.LiteralValueResult
        val expectedType = symbolTable[variableIdentifier.name]?.second?.getType() ?: "Undefined"
        val tuple = Pair(variableIdentifier.kind, getFuturePlaceholder(value, expectedType))
        symbolTable[variableIdentifier.name] = tuple
        return VisitorResult.MapResult(symbolTable)
    }

    private fun visitVariableDeclarationNode(node: VariableDeclarationNode): VisitorResult {
        val variableIdentifier = node.identifier
        val value = node.init.accept(this) as VisitorResult.LiteralValueResult
        val expectedType = variableIdentifier.dataType
        val tuple = Pair(variableIdentifier.kind, getFuturePlaceholder(value, expectedType))
        symbolTable[variableIdentifier.name] = tuple
        return VisitorResult.MapResult(symbolTable)
    }

    private fun visitLiteralNode(node: LiteralNode): VisitorResult {
        return VisitorResult.LiteralValueResult(node.value)
    }

    private fun visitIdentifierNode(node: IdentifierNode): VisitorResult {
        val value = symbolTable[node.name]?.second
        if (value != null) {
            return when (value) {
                is LiteralValue.StringValue -> VisitorResult.LiteralValueResult(value)
                is LiteralValue.NumberValue -> VisitorResult.LiteralValueResult(value)
                is LiteralValue.BooleanValue -> VisitorResult.LiteralValueResult(value)
                else -> VisitorResult.LiteralValueResult(value)
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

        val resultLiteralValue: LiteralValue = VisitorHelper()
            .evaluateBinaryExpression(leftValue, rightValue, node.operator)

        return VisitorResult.LiteralValueResult(resultLiteralValue)
    }

    private fun visitFutureValue(): VisitorResult {
        return VisitorResult.LiteralValueResult(LiteralValue.PromiseValue)
    }

    private fun getFuturePlaceholder(
        result: VisitorResult.LiteralValueResult,
        expectedType: String
    ): LiteralValue {
        if (result.value !is LiteralValue.PromiseValue) {
            return result.value
        }
        return when (expectedType) {
            "string" -> LiteralValue.StringValue("FutureValue")
            "number" -> LiteralValue.NumberValue(1)
            "boolean" -> LiteralValue.BooleanValue(true)
            else -> throw Exception("Type $expectedType not supported")
        }
    }
}
