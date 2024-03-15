class Pila {
    // Implementación de la pila usando una lista doblemente enlazada circular
    private val lista = ListaDoblementeEnlazadaCircular()
    init {
        println("Se ha creado con exito la pila")
    }
    // Métodos de la pila
    // Empila un valor
    fun empilar(valor: Int) {
        lista.agregarAlFrente(valor)
    }
    // Desempila un valor
    fun desempilar(): Int? {
        val valor = lista.tope()
        lista.eliminarPrimero()
        return valor
    }

    // Consulta el valor en la cima de la pila
    fun tope() = lista.tope()
    // Consulta si la pila está vacía
    fun estaVacia() = lista.estaVacia()
    // Consulta el tamaño de la pila
    fun tamano() = lista.tamano()
    // Iterador de la pila
    operator fun iterator() = lista.iterator()
    // Representación de la pila como cadena
    override fun toString() = lista.toString()
}