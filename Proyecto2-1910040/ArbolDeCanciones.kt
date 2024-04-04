import java.io.File

class Arbol_Canciones {
    var raiz: Nodo? = null
    var minInterprete: String? = null
    var minTitulo: String? = null
    var maxInterprete: String? = null
    var maxTitulo: String? = null

    fun agregar(c: TAD_Cancion) {
        if (minInterprete == null || c.obtenerInterprete() < minInterprete!!) {minInterprete = c.obtenerInterprete()}
        if (minTitulo == null || c.obtenerTitulo() < minTitulo!!) {minTitulo = c.obtenerTitulo()}
        if (maxInterprete == null || c.obtenerInterprete() > maxInterprete!!) {maxInterprete = c.obtenerInterprete()}
        if (maxTitulo == null || c.obtenerTitulo() > maxTitulo!!) {maxTitulo = c.obtenerTitulo()}

        if (raiz == null) {raiz = Nodo(c)}
        else {
            var nodoActual = raiz
            var terminar = false
            while (!terminar) {
                if ((nodoActual!!.c.obtenerInterprete() < c.obtenerInterprete()) || (nodoActual!!.c.obtenerInterprete() == c.obtenerInterprete() && nodoActual!!.c.obtenerTitulo() < c.obtenerTitulo())) {
                    if (nodoActual.der == null) {
                        nodoActual.der = Nodo(c)
                        nodoActual.der!!.padre = nodoActual
                        terminar = true}
                    else {nodoActual = nodoActual.der}
                }
                else if ((nodoActual!!.c.obtenerInterprete() > c.obtenerInterprete()) || (nodoActual!!.c.obtenerInterprete() == c.obtenerInterprete() && nodoActual!!.c.obtenerTitulo() > c.obtenerTitulo())) {
                        if (nodoActual.izq == null) {
                            nodoActual.izq = Nodo(c)
                            nodoActual.izq!!.padre = nodoActual
                            terminar = true}
                            else {nodoActual = nodoActual.izq}
                    }
                else {println("La canción ya existe"); terminar = true}
            }
        }
    }

    fun buscar(c: TAD_Cancion): Nodo? {
        var terminar = false
        var nodoActual = raiz
        while (!terminar) {
            if ((nodoActual!!.c.obtenerInterprete() < c.obtenerInterprete()) || (nodoActual!!.c.obtenerInterprete() == c.obtenerInterprete() && nodoActual!!.c.obtenerTitulo() < c.obtenerTitulo())) {
                if (nodoActual!!.der == null) {return null}
                else {nodoActual = nodoActual!!.der}
            }
            else if ((nodoActual!!.c.obtenerInterprete() > c.obtenerInterprete()) || (nodoActual!!.c.obtenerInterprete() == c.obtenerInterprete() && nodoActual!!.c.obtenerTitulo() > c.obtenerTitulo())) {
                if (nodoActual!!.izq == null) {return null}
                else {nodoActual = nodoActual!!.izq}
            }
            else {return nodoActual}
        }
        return null
    }

    fun eliminar(interprete: String, titulo: String) {
        val cancion = TAD_Cancion(titulo, interprete, "")
        val nodo = buscar(cancion)
        if (nodo != null) {
            if (nodo.izq == null && nodo.der == null) {
                if (nodo.padre != null) {
                    if (nodo.padre!!.izq == nodo) {nodo.padre!!.izq = null}
                    else {nodo.padre!!.der = null}
                }
                else {raiz = null}
            }
            else if (nodo.izq == null) {
                if (nodo.padre != null) {
                    if (nodo.padre!!.izq == nodo) {nodo.padre!!.izq = nodo.der}
                    else {nodo.padre!!.der = nodo.der}
                }
                else {raiz = nodo.der}
            }
            else if (nodo.der == null) {
                if (nodo.padre != null) {
                    if (nodo.padre!!.izq == nodo) {nodo.padre!!.izq = nodo.izq}
                    else {nodo.padre!!.der = nodo.izq}
                }
                else {raiz = nodo.izq}
            }
            else {
                var nodoReemplazo = nodo.der
                while (nodoReemplazo!!.izq != null) {nodoReemplazo = nodoReemplazo.izq}
                nodo.c = nodoReemplazo.c
                if (nodoReemplazo.padre!!.izq == nodoReemplazo) {nodoReemplazo.padre!!.izq = null}
                else {nodoReemplazo.padre!!.der = null}
            }
        }
        else {println("La canción no existe")}
    }

    fun imprimirArbolCompleto() {
        if (raiz != null) {
            imprimirArbol(raiz)
        }
        else {println("El árbol está vacío")}
    }

    fun imprimirArbol(nodo: Nodo?) {
        if (nodo != null) {
            imprimirArbol(nodo.izq)
            println(nodo)
            imprimirArbol(nodo.der)
        }
    }

    override fun toString(): String {
        var str = ""
        if (raiz != null) {
            str = toString(raiz)
        }
        else {str = "El árbol está vacío"}
        return str
    }

    fun toString(nodo: Nodo?): String {
        var str = ""
        if (nodo != null) {
            str += toString(nodo.izq)
            str += nodo.toString() + "\n"
            str += toString(nodo.der)
        }
        return str
    }

    fun esArbolDeBusqCancion(nodo: Nodo? = raiz, minInterprete: String? = null, minTitulo: String? = null): Boolean {
        if (nodo != null) {
            if ((minInterprete != null && minTitulo != null) && ((nodo.c.obtenerInterprete() < minInterprete) || (nodo.c.obtenerInterprete() == minInterprete && nodo.c.obtenerTitulo() < minTitulo))) {
                return false
            }
            if ((minInterprete != null && minTitulo != null) && ((nodo.c.obtenerInterprete() > minInterprete) || (nodo.c.obtenerInterprete() == minInterprete && nodo.c.obtenerTitulo() > minTitulo))) {
                return false
            }
            return esArbolDeBusqCancion(nodo.izq, nodo.c.obtenerInterprete(), nodo.c.obtenerTitulo()) && esArbolDeBusqCancion(nodo.der, nodo.c.obtenerInterprete(), nodo.c.obtenerTitulo())
        }
        return true
    }

    fun deArbolASecuencia(nodo: Nodo? = raiz): List<TAD_Cancion> {
        return when (nodo) {
            null -> emptyList()
            else -> deArbolASecuencia(nodo.izq) + nodo.c + deArbolASecuencia(nodo.der)
        }
    }

    fun minInterprete(): String {
        return minInterprete!!
    }

    fun maxInterprete(): String {
        return maxInterprete!!
    }

    fun minTitulo(): String {
        return minTitulo!!
    }

    fun maxTitulo(): String {
        return maxTitulo!!
    }
}




class Nodo(var c: TAD_Cancion) {
    var izq: Nodo? = null
    var der: Nodo? = null
    var padre: Nodo? = null
    var valor: Nodo? = null

    override fun toString(): String {
        return c.toString()
    }
}


/*fun main() {
    // Crear una instancia de Arbol_Canciones
    val arbol = Arbol_Canciones()

    // Crear algunas canciones
    val cancion1 = TAD_Cancion("Bohemian Rhapsody", "Queen", "/ubicacion/de/la/cancion1")
    val cancion2 = TAD_Cancion("Stairway to Heaven", "Led Zeppelin", "/ubicacion/de/la/cancion2")
    val cancion3 = TAD_Cancion("Hotel California", "Eagles", "/ubicacion/de/la/cancion3")
    val cancion4 = TAD_Cancion("Callaíta", "Bad Bunny", "/ubicacion/de/la/cancion4")
    val cancion5 = TAD_Cancion("La Noche de Anoche", "Bad Bunny", "/ubicacion/de/la/cancion5")
    val cancion6 = TAD_Cancion("Callaíta", "Feid", "/ubicacion/de/la/cancion6")

    // Agregar las canciones al árbol
    arbol.agregar(cancion1)
    arbol.agregar(cancion2)
    arbol.agregar(cancion3)
    arbol.agregar(cancion4)
    arbol.agregar(cancion5)
    arbol.agregar(cancion6)

    // Imprimir el árbol completo
    arbol.ImprimirArbolCompleto()

    // Buscar una canción en el árbol
    val cancionBuscada = arbol.buscar(cancion2)
    println("Canción buscada: ${cancionBuscada}")

    // Eliminar una canción del árbol
    arbol.eliminar(cancion2)

    // Imprimir el árbol completo después de eliminar la canción
    arbol.ImprimirArbolCompleto()
}*/