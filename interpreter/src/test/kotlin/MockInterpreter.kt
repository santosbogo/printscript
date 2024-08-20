import org.common.astnode.ASTNode

class MockInterpreter(private val visitor: MockInterpreterVisitor = MockInterpreterVisitor()) {
    fun interpret(node: ASTNode) {
        visitor.visit(node)
    }

    fun getSymbolTable(): Map<String, Any> {
        return visitor.symbolTable
    }

    fun getPrintsList(): List<Any> {
        return visitor.printsList
    }
}
