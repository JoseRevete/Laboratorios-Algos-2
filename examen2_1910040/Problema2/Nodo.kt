class Node<T>(valor: T){
    var valor: T = valor
    var next: Node<T>? = null

    override fun toString(): String {
        return "${valor}"
    }
}
