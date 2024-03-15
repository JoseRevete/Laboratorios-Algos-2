// Clase que representa una lista doblemente enlazada circular
class ListaDoblementeEnlazadaCircular : Iterable<Nodo> {
    // Propiedades de la lista
    // Nodo anterior y siguiente
    internal var ant: Nodo? = null
    internal var prox: Nodo? = null
    private var valor: Int? = null
    internal var tamano: Int = 0

    // Métodos de la lista
    // Agrega un valor al frente de la lista
    fun agregarAlFrente(valor: Int) : Unit {
        val nuevoNodo = Nodo(valor)
        if (estaVacia()) {
            ant = nuevoNodo
            prox = nuevoNodo
            nuevoNodo.ant = nuevoNodo
            nuevoNodo.prox = nuevoNodo
        } else {
            nuevoNodo.prox = prox
            nuevoNodo.ant = ant
            prox?.ant = nuevoNodo
            ant?.prox = nuevoNodo
            prox = nuevoNodo
        }
        tamano++
    }

    // Agrega un valor al final de la lista
    fun agregarAlFinal(valor: Int)  : Unit {
        val nuevoNodo = Nodo(valor)
        if (estaVacia()) {
            ant = nuevoNodo
            prox = nuevoNodo
            nuevoNodo.ant = nuevoNodo
            nuevoNodo.prox = nuevoNodo
        } else {
            nuevoNodo.prox = prox
            nuevoNodo.ant = ant
            prox?.ant = nuevoNodo
            ant?.prox = nuevoNodo
            ant = nuevoNodo
        }
        tamano++
    }
    
    // Busca un valor en la lista
    fun buscar(valor: Int): Nodo? {
        var actual = ant
        do {
            if (actual?.valor == valor) {
                return actual
            }
            actual = actual?.prox
        } while (actual != ant)
        return null
    }
    
    // Consulta si la lista está vacía
    fun estaVacia(): Boolean {
        return ant == null
    }

    // Consulta el tamaño de la lista
    fun tamano(): Int {
        return tamano
    }

    // Consulta el valor en el frente de la lista
    fun tope(): Int? {
        return prox?.valor
    }

    // Elimina un nodo de la lista
    fun eliminar(nodo: Nodo?) : Unit {
        nodo?.let {
            if (it == ant) {
                ant = it.ant
            }
            if (it == prox) {
                prox = it.prox
            }
            it.ant?.prox = it.prox
            it.prox?.ant = it.ant
            tamano--
        }
    }

    // Elimina el nodo al frente de la lista
    fun eliminarPrimero(): Nodo? {
        val nodoEliminado = prox
        try {
            if (nodoEliminado?.prox == nodoEliminado) {
                // La lista solo tiene un nodo
                prox = null
                ant = null
            } else {
                // La lista tiene más de un nodo
                prox = nodoEliminado?.prox
                prox?.ant = null
                ant?.prox = prox
            }
            tamano--
        }
        catch(e : NoSuchElementException) {
            println("No hay mas elementos para iterar")
        }
        finally {return nodoEliminado}
    }

    // Representación de la lista como cadena
    override fun toString(): String {
        val iterador = iterator()
        if (!iterador.hasNext()) {
            return ""
        }

        // Construye la representación de la lista
        val sb = StringBuilder()
        while (iterador.hasNext()) {
            // Agrega el valor del nodo al resultado
            val nodo = iterador.next()
            sb.append(nodo.valor).append(" ")
        }
        // Elimina el último espacio en blanco
        return sb.toString().trimEnd()
    }

    // Iterador de la lista
    override fun iterator(): Iterator<Nodo> {
        return object : Iterator<Nodo> {
            var actual: Nodo? = prox
            var cont = 0

            override fun hasNext(): Boolean {
                return cont < tamano
            }

            override fun next(): Nodo {
                if (!hasNext()) {
                    throw NoSuchElementException()
                }
                val nodo = actual
                actual = actual?.prox
                cont++
                return nodo ?: throw NoSuchElementException()
            }
        }
    }
}