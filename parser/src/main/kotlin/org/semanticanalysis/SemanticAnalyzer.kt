package org.semanticanalysis

import org.astnode.ASTNode
import org.semanticanalysis.semanticchecks.SemanticCheck

class SemanticAnalyzer(private val checks: List<SemanticCheck>) {
    private val semanticVisitor: SemanticVisitor = SemanticVisitor()

    fun analyze(node: ASTNode) {
        // para el nodo q entra, chequea todas las reglas semanticas
        for (check in checks) {
            check.check(node, semanticVisitor.symbolTable)
        }
        // luego lo visita, agregando a la tabla las variablescreadas/asignadas.
        semanticVisitor.visit(node)
    }
}
