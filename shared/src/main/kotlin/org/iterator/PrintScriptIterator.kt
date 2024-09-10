package org.iterator

interface PrintScriptIterator<T> {
    var peekedElement: T?

    fun hasNext(): Boolean
    fun next(): T?
    fun peek(): T?
}
