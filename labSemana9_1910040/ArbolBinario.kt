class Arbol<T> {
    private data class Nodo<T>(val value: T, val children: MutableList<Nodo<T>> = mutableListOf())

    private var root: Nodo<T>? = null

    fun insert(parentValue: T, childValue: T) {
        val parentNode = findNode(root, parentValue)
        if (parentNode != null) {
            parentNode.children.add(Nodo(childValue))
        } else {
            root = Nodo(parentValue)
            root?.children?.add(Nodo(childValue))
        }
    }

    fun search(values: List<T>): Boolean {
        var currentNode = root
        for (i in 0 until values.size - 1) {
            val foundNode = findNode(currentNode, values[i])
            if (foundNode == null || !foundNode.children.any { it.value == values[i + 1] }) {
                return false
            }
            currentNode = foundNode
        }
        return true
    }

    private fun findNode(node: Nodo<T>?, value: T): Nodo<T>? {
        if (node == null) {
            return null
        }
        if (node.value == value) {
            return node
        }
        for (child in node.children) {
            val result = findNode(child, value)
            if (result != null) {
                return result
            }
        }
        return null
    }

    fun imprimir(): String {
        return imprimir(root).trim()
    }

    private fun imprimir(node: Nodo<T>?): String {
        if (node == null) {
            return ""
        }
        val children = node.children.joinToString(" ") { imprimir(it) }
        return "${node.value} $children"
    }
}