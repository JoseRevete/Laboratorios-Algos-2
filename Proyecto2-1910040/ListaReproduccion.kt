// Clase que define una lista de reproducción
class TAD_Lista_De_Reproduccion {
    var nombre: String
    var canciones: Arbol_Canciones

    // Constructor de la clase
    constructor(nombre: String) {
        this.nombre = nombre
        this.canciones = Arbol_Canciones()
    }

    // Función que agrega una canción a la lista de reproducción
    fun agregarLista(cancion: TAD_Cancion) {canciones.agregar(cancion)}

    // Función que elimina una canción de la lista de reproducción
    fun eliminarCancion(interprete: String, titulo: String) : Boolean {return canciones.eliminar(interprete, titulo)}

    // Función que obtiene la lista de reproducción
    fun obtenerLR(): List<TAD_Cancion> {return deArbolASecuencia(canciones.raiz)}

    // Función que muestra la lista de reproducción
    fun mostrarLR() {canciones.imprimirArbolCompleto()}

    // Función que obtiene el mínimo intérprete de la lista de reproducción
    fun minInterprete(nodo: Nodo?): String {
        val arbol = Arbol_Canciones()
        arbol.raiz = nodo
        if (arbol.raiz == null) {return ""}
        while (arbol.raiz!!.izq != null) {arbol.raiz = arbol.raiz!!.izq}
        return arbol.raiz!!.c.obtenerInterprete()
    }

    // Función que obtiene el máximo intérprete de la lista de reproducción
    fun maxInterprete(nodo: Nodo?): String {
        val arbol = Arbol_Canciones()
        arbol.raiz = nodo
        if (arbol.raiz == null) {return ""}
        while (arbol.raiz!!.der != null) {arbol.raiz = arbol.raiz!!.der}
        return arbol.raiz!!.c.obtenerInterprete()
    }

    // Función que obtiene el mínimo título de la lista de reproducción
    fun minTitulo(nodo: Nodo?): String {
        val arbol = Arbol_Canciones()
        arbol.raiz = nodo
        if (arbol.raiz == null) {return ""}
        while (arbol.raiz!!.izq != null) {arbol.raiz = arbol.raiz!!.izq}
        return arbol.raiz!!.c.obtenerTitulo()
    }

    // Función que obtiene el máximo título de la lista de reproducción
    fun maxTitulo(nodo: Nodo?): String {
        val arbol = Arbol_Canciones()
        arbol.raiz = nodo
        if (arbol.raiz == null) {return ""}
        while (arbol.raiz!!.der != null) {arbol.raiz = arbol.raiz!!.der}
        return arbol.raiz!!.c.obtenerTitulo()
    }

    // Función que verifica si la lista de reproducción es un árbol de búsqueda
    fun esArbolDeBusqCancion(nodo: Nodo?): Boolean {
        val arbol = Arbol_Canciones()
        var primer: Boolean
        var segundo: Boolean
        var tercero: Boolean
        var cuarto: Boolean
        arbol.raiz = nodo
        if (arbol.raiz == null) {return true}
        if (arbol.raiz!!.der != null) {
            primer = arbol.raiz!!.c.obtenerInterprete() < minInterprete(arbol.raiz!!.der) || (arbol.raiz!!.c.obtenerInterprete() == minInterprete(arbol.raiz!!.der) && arbol.raiz!!.c.obtenerTitulo() < minTitulo(arbol.raiz!!.der))
            segundo = esArbolDeBusqCancion(arbol.raiz!!.der)}
        else {primer = true; segundo = true}
        if (arbol.raiz!!.izq != null) {
            tercero = arbol.raiz!!.c.obtenerInterprete() > maxInterprete(arbol.raiz!!.izq) || (arbol.raiz!!.c.obtenerInterprete() == maxInterprete(arbol.raiz!!.izq) && arbol.raiz!!.c.obtenerTitulo() > maxTitulo(arbol.raiz!!.izq))
            cuarto = esArbolDeBusqCancion(arbol.raiz!!.izq)}
        else {tercero = true; cuarto = true}
        return primer && segundo && tercero && cuarto
    }

    // Función que convierte un árbol en una secuencia
    fun deArbolASecuencia(nodo: Nodo?): List<TAD_Cancion> {
        var result: List<TAD_Cancion>
        if (nodo == null) {return emptyList()}
        result = deArbolASecuencia(nodo.izq) + listOf(nodo.c) + deArbolASecuencia(nodo.der)
        return result
    }
}