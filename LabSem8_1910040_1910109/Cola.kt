class Cola() {
    // Implementación de la cola usando una lista doblemente enlazada circular
    private val lista = ListaDoblementeEnlazadaCircular()

    init {
        println("Se ha creado con exito la cola")
    }
    
    // Métodos de la cola
    // Encola un valor
    fun encolar(valor: Int) {
        lista.agregarAlFinal(valor)
    }
    // Desencola un valor
    fun desencolar(): Int? {
        val valor = lista.tope()
        lista.eliminarPrimero()
        return valor
    }
    // Consulta el valor en el frente de la cola
    fun primero() = lista.tope()
    // Consulta si la cola está vacía
    fun estaVacia() = lista.estaVacia()
    // Consulta el tamaño de la cola
    fun tamano() = lista.tamano()
    // Iterador de la cola
    operator fun iterator() = lista.iterator()
    // Representación de la cola como cadena
    override fun toString() = lista.toString()
}