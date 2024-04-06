// Clase que define un nodo de un árbol binario de búsqueda
class Nodo(var c: TAD_Cancion) {
    var izq: Nodo? = null
    var der: Nodo? = null
    var padre: Nodo? = null
    var valor: Nodo? = null

    // Función que convierte el nodo a una cadena
    override fun toString(): String {
        return c.toString()
    }
}