class CodigoMorseAD {
    class Nodo(var valor: String) {
        var izquierdo: Nodo? = null
        var derecho: Nodo? = null
    }

    private var raiz: Nodo? = null

    fun decodificarLetra(letra: String): String {
        var letraEnChar: CharArray
        try {
            letraEnChar = letra.toCharArray()
        }
        catch(e: ArrayIndexOutOfBoundsException) {
            println("Error: $e")
            return "-1"
        }
        var padre = raiz
        for (i in letraEnChar) {
            if (i == '.') {
                try {padre = padre?.izquierdo}
                catch(e: KotlinNullPointerException) {println("Error: $e"); return "-1"}}
            else if (i == '-') {    
                try {padre = padre?.derecho}
                catch(e: KotlinNullPointerException) {println("Error: $e"); return "-1"}}
        }
        if (padre == null) {return "-1"}
        return padre?.valor.toString()
    }

    fun decodificarMensaje(mensaje: String): String {
        var decodificacion = ""
        var letras = mensaje.split(" ")
        for (j in letras) {
            var decodif = decodificarLetra(j)
            if (decodif == "-1") {return "Error, codigo Morse no valido"}
            decodificacion += decodif + " "
        }
        return decodificacion
    }

    fun insertar(padre: String, hijo1: String, hijo2: String) : Unit{
        val nodoPadre = buscar(padre) ?: if (raiz == null) {
            raiz = Nodo(padre)
            raiz
        } else {return}
        if (hijo1.isNotEmpty()) {nodoPadre?.izquierdo = Nodo(hijo1)}
        if (hijo2.isNotEmpty()) {nodoPadre?.derecho = Nodo(hijo2)}
    }

    fun buscar(valor: String, nodo: Nodo? = raiz): Nodo? {
        if (nodo == null || nodo.valor == valor) {return nodo}
        return buscar(valor, nodo.izquierdo) ?: buscar(valor, nodo.derecho)
    }

    fun imprimir(): String {return imprimir(raiz).trim()}

    private fun imprimir(nodo: Nodo?): String {
        if (nodo == null) {return ""}
        val izquierdo = imprimir(nodo.izquierdo)
        val derecho = imprimir(nodo.derecho)
        return "${nodo.valor} $izquierdo $derecho"}
}