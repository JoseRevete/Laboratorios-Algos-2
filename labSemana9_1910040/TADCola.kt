class Cola<T> {
    private var cola = ListaDoblementeEnlazada<T>()

    init {
        println("Se creó una cola exitosamente")
    }

    private var size = 0

    fun getSize(): Int {
        return cola.tamano()
    }

    fun estaVacia(): Boolean {
        return cola.estaVacia()
    }

    fun encolar(dato: T) {
        cola.agregar(dato)
    }

    fun desencolar(): T {
        if (estaVacia())
            throw IllegalStateException("La cola está vacía")
        
        val dato = cola.primero()?.dato ?: throw NoSuchElementException("La cola está vacía.")
        cola.eliminarPrimero()
        size--
        return dato
    }

    fun desencolarUltimo(): T {
        if (estaVacia())
            throw IllegalStateException("La cola está vacía")
        
        val dato = cola.ultimo()?.dato ?: throw NoSuchElementException("La cola está vacía.")
        cola.eliminarUltimo()
        size--
        return dato
    }

    fun primero(): T {
        if (cola.estaVacia())
            throw IllegalStateException("La cola está vacía")
        
        return cola.primero()?.dato ?: throw NoSuchElementException("La cola está vacía.")
    }

    override fun toString(): String {
        return cola.imprimir()
    }

    inner class iteradorCola(ColaActual: Cola<T>) : Iterator<T> {
        var nodoActual: Nodo<T>? = ColaActual.cola.primero()

        override fun hasNext(): Boolean {
            return this.nodoActual != null
        }

        override fun next(): T {
            if(this.nodoActual == null){
                throw NoSuchElementException("No hay más elementos en la cola")
            }
            
            val dato = this.nodoActual!!.dato
            this.nodoActual = nodoActual?.siguiente
            return dato
        }
    }

    fun iterador(): Iterator<T> {
        return iteradorCola(this)
    }
}