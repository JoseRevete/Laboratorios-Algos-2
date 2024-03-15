// Clase que representa un nodo de una lista doblemente enlazada circular
class Nodo(val valor: Int?) {
    // Propiedades de un nodo
    // Anterior y siguiente
    var ant: Nodo? = null
    var prox: Nodo? = null

    // Representación de un nodo como cadena
    override fun toString(): String {
        return valor.toString()}
}