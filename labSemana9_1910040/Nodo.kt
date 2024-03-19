class Nodo<T>(var dato: T){
    var siguiente: Nodo<T>? = null
    var anterior: Nodo<T>? = null

    override fun toString(): String {
        return if (dato is Array<*>) {
            "Nodo(dato=${(dato as Array<*>).toList()})"
        } else {
            "Nodo(dato=$dato)"
        }
    }
}