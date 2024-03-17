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
        return values.all { value -> findNode(root, value) != null }
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
}


fun main() {
    val arbol = Arbol<String>()

    // Insertar nodos en el árbol
    println("Insertando nodos en el árbol...")
    arbol.insert("padre", "hijo1")
    arbol.insert("padre", "hijo2")
    arbol.insert("padre", "hijo3")
    arbol.insert("padre", "hijo4")
    arbol.insert("hijo1", "nieto1")
    arbol.insert("hijo1", "nieto2")
    arbol.insert("hijo1", "nieto3")
    arbol.insert("hijo2", "nieto4")
    arbol.insert("hijo2", "nieto5")
    arbol.insert("hijo3", "nieto6")

    // Imprimir el árbol
    println("El árbol debería contener los nodos 'padre', 'hijo1', 'hijo2', 'nieto1' y 'nieto2':")
    println(arbol.imprimir())

    // Buscar valores en el árbol
    println("Buscando secuencias de valores en el árbol...")
    println("La secuencia 'padre', 'hijo1', 'nieto1' debería estar en el árbol: ${arbol.search(listOf("padre", "hijo1", "nieto1"))}") // Debería imprimir: true
    println("La secuencia 'padre', 'hijo2' debería estar en el árbol: ${arbol.search(listOf("padre", "hijo2"))}") // Debería imprimir: true
    println("La secuencia 'padre', 'hijo3' no debería estar en el árbol: ${arbol.search(listOf("padre", "hijo3"))}") // Debería imprimir: false
}