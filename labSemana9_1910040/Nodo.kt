class Nodo<T>(var dato: T){
    var siguiente: Nodo<T>? = null

    override fun toString(): String {
        return "Nodo(dato=$dato)"
    }
}