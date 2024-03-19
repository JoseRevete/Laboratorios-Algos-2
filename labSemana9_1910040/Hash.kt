// Creando una tabla hash con listas doblemente enlazadas
class TablaHash<T>(private val tamañoTabla: Int) {
    // Se crea un arreglo de listas doblemente enlazadas
    private val tabla: Array<ListaDoblementeEnlazada<Array<String>>?> = arrayOfNulls(tamañoTabla)

    // Se inserta un elemento en la tabla
    fun insertar(elemento: Array<String>) : Unit{
        val posicion = calcularPosicion(elemento[0].toInt())
        if (tabla[posicion] == null) {
            tabla[posicion] = ListaDoblementeEnlazada()
        }
        tabla[posicion]?.agregar(elemento)
    }

    // Se obtiene un elemento de la tabla
    operator fun get(indice: Int): ListaDoblementeEnlazada<Array<String>>? {
        return tabla[indice]
    }

    // Se busca un elemento en la tabla
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

    // Se imprime la tabla
    fun imprimir() : Unit{
        for (i in tabla.indices) {
            println("Posición $i: ${tabla[i]}")
        }
    }

    // Se calcula la posición de un elemento en la tabla
    private fun calcularPosicion(elemento: Int): Int {
        val elementos = elemento.toString().toCharArray()
        var suma = 0
        for (i in 1 until elementos.size+1) {
            suma += elementos[i-1].toString().toInt() * i
        }
        return suma % tamañoTabla
    }
}