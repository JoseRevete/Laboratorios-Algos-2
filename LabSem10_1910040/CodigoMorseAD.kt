// Se crea la clase CodigoMorseAD
class CodigoMorseAD {
    // Se crea la clase Nodo
    class Nodo(var valor: String) {
        var izquierdo: Nodo? = null
        var derecho: Nodo? = null
    }

    private var raiz: Nodo? = null

    // Se crea el metodo CrearCodigoMorse para crear el arbol con los valores del codigo Morse
    fun CrearCodigoMorse() : Unit{
        val valores = listOf(
        Triple("", "E", "T"),
        Triple("E", "I", "A"),
        Triple("I", "S", "U"),
        Triple("A", "R", "W"),
        Triple("S", "H", "V"),
        Triple("U", "F", ""),
        Triple("R", "L", ""),
        Triple("W", "P", "J"),
        Triple("T", "N", "M"),
        Triple("N", "D", "K"),
        Triple("M", "G", "O"),
        Triple("D", "B", "X"),
        Triple("K", "C", "Y"),
        Triple("G", "Z", "Q"),
        Triple("O", "", "")
        )

        for ((padre, hijo1, hijo2) in valores) {
            insertar(padre, hijo1, hijo2)
        }
    }

    // Se crea el metodo insertar para insertar los valores en el arbol
    fun insertar(padre: String, hijo1: String, hijo2: String) : Unit{
        val nodoPadre = buscar(padre) ?: if (raiz == null) {
            raiz = Nodo(padre)
            raiz
        } else {return}
        if (hijo1.isNotEmpty()) {nodoPadre?.izquierdo = Nodo(hijo1)}
        if (hijo2.isNotEmpty()) {nodoPadre?.derecho = Nodo(hijo2)}
    }

    // Se crea el metodo buscar para buscar un valor en el arbol
    fun buscar(valor: String, nodo: Nodo? = raiz): Nodo? {
        if (nodo == null || nodo.valor == valor) {return nodo}
        return buscar(valor, nodo.izquierdo) ?: buscar(valor, nodo.derecho)
    }

    // Se crea el metodo decodificarLetra para decodificar una letra en codigo Morse
    fun decodificarLetra(letra: String): String {
        var letraEnChar: CharArray
        try {letraEnChar = letra.toCharArray()}
        catch(e: ArrayIndexOutOfBoundsException) {
            println("Error: $e")
            return "-1"}
        // Si la letra no es "." o "-", se retorna un mensaje de error
        if (letraEnChar[0].toString() != "." && letraEnChar[0].toString() != "-") {return "-1"}
        var padre = raiz
        // Se recorre la letra en codigo Morse
        for (i in letraEnChar) {
            if (i == '.') {
                try {padre = padre?.izquierdo}
                catch(e: KotlinNullPointerException) {println("Error: $e"); return "-1"}}
            else if (i == '-') {    
                try {padre = padre?.derecho}
                catch(e: KotlinNullPointerException) {println("Error: $e"); return "-1"}}
        }
        // Si el padre es nulo, se retorna un mensaje de error
        if (padre == null) {return "-1"}
        return padre.valor.toString()
    }

    // Se crea el metodo decodificarMensaje para decodificar un mensaje en codigo Morse
    fun decodificarMensaje(mensaje: String): String {
        var decodificacion = ""
        // Se separa el mensaje por el caracter " "
        var letras = mensaje.split(" ")
        // Se recorre el mensaje separado por " "
        for (j in letras) {
            var decodif = decodificarLetra(j)
            // Si el mensaje no es valido, se retorna un mensaje de error
            if (decodif == "-1") {return "Error, codigo Morse no valido"}
            decodificacion += decodif
        }
        return decodificacion
    }
}