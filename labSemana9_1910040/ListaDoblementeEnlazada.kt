// Se crea una lista doblemente enlazada
class ListaDoblementeEnlazada<T> : Iterable<Nodo<T>> {
    // Se inicializan las variables cabeza y cola
    private var cabeza: Nodo<T>? = null
    private var cola: Nodo<T>? = null

    // Se agrega un nodo a la lista
    fun agregar(dato: T) : Unit{
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

    // Se verifica si la lista está vacía
    fun estaVacia(): Boolean {
        return cabeza == null
    }

    // Se agrega un nodo al final de la lista
    fun agregarAlFinal(dato: T) : Unit{
        agregar(dato)
    }

    // Se agrega un nodo al inicio de la lista
    fun agregarAlInicio(dato: T) : Unit{
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

    // Se elimina el primer nodo de la lista
    fun eliminarPrimero() : Unit{
        if (cabeza != null) {
            cabeza = cabeza?.siguiente
            cabeza?.anterior = null
        }
    }

    // Se modifica un nodo de la lista
    fun modificarUnNodo(nodo: Nodo<T>, dato: T) : Unit{
        nodo.dato = dato
    }

    // Se elimina el último nodo de la lista
    fun eliminarUltimo() : Unit{
        if (cola != null) {
            cola = cola?.anterior
            cola?.siguiente = null
        }
    }

    // Se obtiene el siguiente nodo
    fun siguiente(nodo: Nodo<T>): Nodo<T>? {
        return nodo.siguiente
    }

    // Se obtiene el nodo que está al principio de la lista
    fun primero(): Nodo<T>? {
        return cabeza
    }

    // Se obtiene el nodo que está al final de la lista
    fun ultimo(): Nodo<T>? {
        return cola
    }

    // Se obtiene el tamaño de la lista
    fun tamano(): Int {
        var count = 0
        var nodoActual = cabeza
        while (nodoActual != null) {
            count++
            nodoActual = nodoActual.siguiente
        }
        return count
    }

    // Se imprime la lista
    fun imprimir(): String {
        var nodoActual = cabeza
        var result = ""
        while (nodoActual != null) {
            result += "${nodoActual.dato} "
            nodoActual = nodoActual.siguiente
        }
        return result.trimEnd()
    }

    // Se crea un iterador para la lista
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