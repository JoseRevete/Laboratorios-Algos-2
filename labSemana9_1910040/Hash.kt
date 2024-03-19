class TablaHash<T>(private val tamañoTabla: Int) {
    private val tabla: Array<ListaDoblementeEnlazada<Array<String>>?> = arrayOfNulls(tamañoTabla)

    fun insertar(elemento: Array<String>) {
        val posicion = calcularPosicion(elemento[0].toInt())
        if (tabla[posicion] == null) {
            tabla[posicion] = ListaDoblementeEnlazada()
        }
        tabla[posicion]?.agregar(elemento)
    }

    operator fun get(indice: Int): ListaDoblementeEnlazada<Array<String>>? {
        return tabla[indice]
    }

    fun buscar(elemento: Array<String>): Boolean {
        val posicion = calcularPosicion(elemento[0].toInt())
        var nodoActual = tabla[posicion]?.primero()
        while (nodoActual != null) {
            if (nodoActual.dato == elemento) {
                return true
            }
            nodoActual = tabla[posicion]?.siguiente(nodoActual)
        }
        return false
    }

    fun imprimir() {
        for (i in tabla.indices) {
            println("Posición $i: ${tabla[i]}")
        }
    }

    private fun calcularPosicion(elemento: Int): Int {
        val elementos = elemento.toString().toCharArray()
        var suma = 0
        for (i in 1 until elementos.size+1) {
            suma += elementos[i-1].toString().toInt() * i
        }
        return suma % tamañoTabla
    }
}