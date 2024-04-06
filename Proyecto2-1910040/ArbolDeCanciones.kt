import java.io.File

// Clase que contiene un árbol binario de búsqueda de canciones
class Arbol_Canciones {
    var raiz: Nodo? = null
    var izq: Nodo? = null
    var der: Nodo? = null
    var padre: Nodo? = null
    var valor: Nodo? = null
    var minInterprete: String? = null
    var minTitulo: String? = null
    var maxInterprete: String? = null
    var maxTitulo: String? = null

    // Función que agrega una canción al árbol
    fun agregar(c: TAD_Cancion) {
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
                else {println("La canción: ${c.obtenerTitulo()} de ${c.obtenerInterprete()} ya existe"); terminar = true}
            }
        }
    }

    // Función que busca una canción en el árbol
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

    // Función que elimina una canción del árbol
    fun eliminar(interprete: String, titulo: String) : Boolean {
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
            return true
        }
        else {println("La canción no existe"); return false}
    }

    // Función que convierte el árbol en una lista de canciones
    fun imprimirArbolCompleto() {
        if (raiz != null) {
            println("Lista de Reproducción:")
            println("---------------------------------------")
            imprimirArbol(raiz)
            println("---------------------------------------")
        }
        else {println("El árbol está vacío")}
    }

    // Función que imprime el árbol recursivamente
    fun imprimirArbol(nodo: Nodo?) {
        if (nodo != null) {
            imprimirArbol(nodo.izq)
            println(nodo)
            imprimirArbol(nodo.der)
        }
    }

    // Función que convierte el árbol en una lista de canciones
    override fun toString(): String {
        var str : String
        if (raiz != null) {
            str = toString(raiz)
        }
        else {str = "El árbol está vacío"}
        return str
    }

    // Función que convierte el árbol en una lista de canciones
    fun toString(nodo: Nodo?): String {
        var str = ""
        if (nodo != null) {
            str += toString(nodo.izq)
            str += nodo.toString() + "\n"
            str += toString(nodo.der)
        }
        return str
    }
}