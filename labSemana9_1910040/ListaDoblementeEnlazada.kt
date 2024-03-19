class ListaDoblementeEnlazada<T> : Iterable<Nodo<T>> {
    private var cabeza: Nodo<T>? = null
    private var cola: Nodo<T>? = null

    fun agregar(dato: T) {
        val nuevoNodo = Nodo(dato)
        if (cabeza == null) {
            cabeza = nuevoNodo
            cola = nuevoNodo
        } else {
            nuevoNodo.anterior = cola
            cola?.siguiente = nuevoNodo
            cola = nuevoNodo
        }
    }

    fun estaVacia(): Boolean {
        return cabeza == null
    }

    fun agregarAlFinal(dato: T) {
        agregar(dato)
    }

    fun hacerCopiaDeLista(ListaDoblementeEnlazada: ListaDoblementeEnlazada<T>): ListaDoblementeEnlazada<T> {
        val nuevaLista = ListaDoblementeEnlazada<T>()
        for (nodo in ListaDoblementeEnlazada) {
            nuevaLista.agregar(nodo.dato)
        }
        return nuevaLista
    }

    fun agregarAlInicio(dato: T) {
        val nuevoNodo = Nodo(dato)
        if (cabeza == null) {
            cabeza = nuevoNodo
            cola = nuevoNodo
        } else {
            nuevoNodo.siguiente = cabeza
            cabeza?.anterior = nuevoNodo
            cabeza = nuevoNodo
        }
    }

    fun eliminarPrimero() {
        if (cabeza != null) {
            cabeza = cabeza?.siguiente
            cabeza?.anterior = null
        }
    }

    fun modificarUnNodo(nodo: Nodo<T>, dato: T) {
        nodo.dato = dato
    }

    fun eliminarUltimo() {
        if (cola != null) {
            cola = cola?.anterior
            cola?.siguiente = null
        }
    }

    fun siguiente(nodo: Nodo<T>): Nodo<T>? {
        return nodo.siguiente
    }

    fun primero(): Nodo<T>? {
        return cabeza
    }

    fun ultimo(): Nodo<T>? {
        return cola
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