// Funcion: Prueba de la clase CodigoMorseAD
fun main(args: Array<String>) : Unit {
    // Se unen los argumentos en un solo string
    val mensajeUnido = args.joinToString(" ")
    // Se crea un objeto de la clase CodigoMorseAD
    val arbol = CodigoMorseAD()
    // Se llama al metodo CrearCodigoMorse para crear el arbol
    arbol.CrearCodigoMorse()
    // Se separa el mensaje por el caracter "/"
    val mensajeSeparadoPor = mensajeUnido.split("/")
    var decodificacion = ""
    var decodif : String
    // Se recorre el mensaje separado por "/"
    for (i in mensajeSeparadoPor) {
        decodif = arbol.decodificarMensaje(i)
        // Si el mensaje no es valido, se imprime un mensaje de error
        if (decodif == "Error, codigo Morse no valido") {
            decodificacion = decodif
            break
        }
        // Se concatena la decodificacion
        decodificacion += arbol.decodificarMensaje(i) + " "
    }
    // Se elimina el espacio al final
    decodificacion = decodificacion.trim()
    println(decodificacion)
}