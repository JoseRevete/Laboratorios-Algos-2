// Clase de la tabla de hash que se encarga de almacenar los valores repetidos en la tabla
class TablaDeHash(private val numerosRepetidos: Int) {
    // Crear tabla de hash
    private val tabla = Array<String?>(numerosRepetidos) { "" }

    // Agregar elementos a la tabla
    fun agregar(elemento: String, indiceEnTabla: Int) {
        val indice = indiceEnTabla
        tabla[indice] += elemento + " "
    }

    // Obtener elementos de la tabla
    fun obtener(indiceEnTabla: Int): String? {
        return tabla[indiceEnTabla]
    }

    // Imprimir valores de la tabla
    fun imprimirValores(indice: Int) : String {
        return tabla[indice]!!
    }
}