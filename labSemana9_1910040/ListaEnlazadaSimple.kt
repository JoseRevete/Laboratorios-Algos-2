class ListaEnlazadaSimple<T> : Iterable<Nodo<T>>{
    private var cabeza: Nodo<T>? = null

    fun agregar(dato: T) {
        val nuevoNodo = Nodo(dato)
        if (cabeza == null) {
            cabeza = nuevoNodo
        } else {
            var nodoActual = cabeza
            while (nodoActual?.siguiente != null) {
                nodoActual = nodoActual.siguiente
            }
            nodoActual?.siguiente = nuevoNodo
        }
    }

    fun estaVacia(): Boolean {
        return cabeza == null
    }

    fun eliminarPrimero() {
        if (cabeza != null) {
            cabeza = cabeza?.siguiente
        }
    }

    fun primero(): Nodo<T>? {
        return cabeza
    }

    fun tamano(): Int {
        var count = 0
        var nodoActual = cabeza
        while (nodoActual != null) {
            count++
            nodoActual = nodoActual.siguiente
        }
        return count
    }

    fun imprimir(): String {
        var nodoActual = cabeza
        var result = ""
        while (nodoActual != null) {
            result += "${nodoActual.dato} "
            nodoActual = nodoActual.siguiente
        }
        return result.trimEnd()
    }

    override fun iterator(): Iterator<Nodo<T>> {
        return object : Iterator<Nodo<T>> {
            var current = cabeza

            override fun hasNext(): Boolean {
                return current != null
            }

            override fun next(): Nodo<T> {
                val result = current
                current = current?.siguiente
                return result ?: throw NoSuchElementException("No hay más elementos en la lista.")
            }
        }
    }
}