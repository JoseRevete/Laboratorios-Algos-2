fun main(args: String) : Unit {
    
    val arbol = CodigoMorseAD()
    arbol.insertar("", "E", "T")
    arbol.insertar("E", "I", "A")
    arbol.insertar("I", "S", "U")
    arbol.insertar("A", "R", "W")
    arbol.insertar("S", "H", "V")
    arbol.insertar("U", "F", "")
    arbol.insertar("R", "L", "")
    arbol.insertar("W", "P", "J")
    arbol.insertar("T", "N", "M")
    arbol.insertar("N", "D", "K")
    arbol.insertar("M", "G", "O")
    arbol.insertar("D", "B", "X")
    arbol.insertar("K", "C", "Y")
    arbol.insertar("G", "Z", "Q")
    arbol.insertar("O", "", "")

    val mensajeSeparadoPor = args.split("/")
    var decodificacion = ""
    var decodif = ""
    for (i in mensajeSeparadoPor) {
        decodif = arbol.decodificarMensaje(i)
        if (decodif == "-1") {
            println("Error, codigo Morse no valido")
            return
        }
        decodificacion += arbol.decodificarMensaje(i) + " "
    }
    decodificacion = decodificacion.trim()
}