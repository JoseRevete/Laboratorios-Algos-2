// Funcion main que recibe una lista de palabras y las imprime en orden de aparición
fun main(args: Array<String>) {
    // Eliminar palabras repetidas
    val argsSinRepetir = args.distinct().toTypedArray()
    // Crear tabla de hash
    val tabla = TablaDeHash(argsSinRepetir.size)
    // Agregar palabras a la tabla
    for (i in args.indices) {
        tabla.agregar(args[i], argsSinRepetir.indexOf(args[i]))
    }
    var secuencia = ""
    // Imprimir palabras en orden de aparición
    for (i in argsSinRepetir.indices) {
        secuencia += tabla.imprimirValores(i)
    }
    println(secuencia.trim())
}