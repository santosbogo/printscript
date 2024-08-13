import org.common.astnode.astnodevisitor.InterpreterVisitor
import org.shared.astnode.ASTNode
import org.shared.astnode.astnodevisitor.ASTNodeVisitor

class MockInterpreter(private val visitor: MockInterpreterVisitor = MockInterpreterVisitor()) {
    fun interpret(node: ASTNode) {
        visitor.visit(node)
    }

    fun getSymbolTable(): Map<String, Any> {
        return visitor.symbolTable
    }

    fun getPrintsList(): List<Any> {
        return visitor.getPrintsList()
    }

}