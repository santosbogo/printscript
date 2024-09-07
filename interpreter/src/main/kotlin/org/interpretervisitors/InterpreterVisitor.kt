package org.interpretervisitors

import org.astnode.astnodevisitor.ASTNodeVisitor
import org.inputers.InputProvider
import org.printers.Printer

interface InterpreterVisitor : ASTNodeVisitor {
    val printer: Printer
    val inputProvider: InputProvider
}
