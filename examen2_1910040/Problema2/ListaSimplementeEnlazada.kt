class ListaEnlazadaSimple<T> : Iterable<T> {
    private var cabeza: Node<T>? = null
    private var tamanio = 0

    fun esVacia(): Boolean = tamanio == 0

    fun numElementos(): Int = tamanio

    fun agregarAlFinal(valor: T): ListaEnlazadaSimple<T> {
        val nuevo = Node(valor)
        if (cabeza == null) {
            cabeza = nuevo
        } else {
            var actual = cabeza
            while (actual?.next != null) {
                actual = actual.next
            }
            actual?.next = nuevo
        }
        tamanio++
        return this
    }

    fun obtener(indice: Int): T? {
        var actual = cabeza
        var indiceActual = 0
        while (actual != null && indiceActual < indice) {
            actual = actual.next
            indiceActual++
        }
        return actual?.valor
    }

    override fun toString(): String {
        var s: String
        if (esVacia()) {
            s = "Lista vacia"
        } else {
            var actual: Node<T>? = cabeza
            s = actual.toString()
            actual = actual?.next
            while (actual != null) {
                s = "$s -> ${actual.toString()}"
                actual = actual.next
            }
        }
        return s
    }

    inner class ListaIterato<T>(l: ListaEnlazadaSimple<T>) : Iterator<T> {
        var actual = l.cabeza

        override fun hasNext(): Boolean = (actual != null)

        override fun next(): T {
            if (actual == null) {
                throw NoSuchElementException("Error, no hay mas elementos que iterar")
            }
            val valor = actual!!.valor
            actual = actual?.next
            return valor
        }
    }

    override operator fun iterator(): Iterator<T> = ListaIterato(this)
}