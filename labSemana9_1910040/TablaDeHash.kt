class TablaDeHash {
    private val tamanoTabla = dameTamanoTabla()
    private val tabla = Array(tamanoTabla) { ListaDobleEnlazada<String>() }
    private val maxIteraciones = tamanoTabla

    private fun funcionHash(valor: Int): Int {
        val factorMultiplicativo = 0.6180339887 // Valor recomendado para evitar colisiones
        val producto = (valor * factorMultiplicativo).toInt()
        val parteDecimal = producto - producto.toInt()
        return (tamanoTabla * parteDecimal).toInt()
    }

    private fun rehash(valor: List<String>, iteraciones: Int): Boolean {
        println("Rehashing adentro")
        println("Valor: ${valor[0]}")
        println("Iteraciones: $iteraciones")
        println("Max iteraciones: $maxIteraciones")
        if (iteraciones >= maxIteraciones) {
            return false
        }
        val hash = funcionHash(valor[0].toInt())
        println("Hash: $hash")
        if (tabla[hash].isEmpty()) {
            tabla[hash].insertar(valor[0])
            return true
        } else {
            return rehash(valor, iteraciones + 1)
        }
    }

    fun insertar(valores: List<String>) {
        println("Insertando valores: $valores")
        val valor = valores[0].toString()
        println("Valor: $valor")
        val numero = valor.toInt()
        val hash = funcionHash(numero)
        println("Hash: $hash")
        if (tabla[hash].isEmpty()) {
            tabla[hash].insertar(valor)
        } else {
            println("Rehashing")
            rehash(valores, 0)
        }
    }

    fun imprimir() {
        for (indice in 0 until tamanoTabla) {
            println("Índice $indice: ${tabla[indice].toString()}")
        }
    }

    fun buscar(valor: List<String>): Boolean {
        val numero = valor[0].toInt()
        val hash = funcionHash(numero)
        return tabla[hash].buscar(valor[0])
    }
}

class Nodo<T>(val valor: T) {
    var siguiente: Nodo<T>? = null
    var anterior: Nodo<T>? = null
}