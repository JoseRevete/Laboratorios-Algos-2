class TAD_Lista_De_Reproduccion {
    var nombre: String
    var canciones: Arbol_Canciones

    constructor(nombre: String) {
        this.nombre = nombre
        this.canciones = Arbol_Canciones()
    }

    fun agregarLista(cancion: TAD_Cancion) {canciones.agregar(cancion)}

    fun eliminarCancion(interprete: String, titulo: String) : Unit {canciones.eliminar(interprete, titulo)}

    fun obtenerLR(): List<TAD_Cancion> {return deArbolASecuencia(canciones.raiz)}

    fun mostrarLR() {canciones.imprimirArbolCompleto()}

    fun minInterprete(nodo: Nodo?): String {
        val arbol = Arbol_Canciones()
        arbol.raiz = nodo
        if (arbol.raiz == null) {return ""}
        while (arbol.raiz!!.izq != null) {arbol.raiz = arbol.raiz!!.izq}
        return arbol.raiz!!.c.obtenerInterprete()
    }

    fun maxInterprete(nodo: Nodo?): String {
        val arbol = Arbol_Canciones()
        arbol.raiz = nodo
        if (arbol.raiz == null) {return ""}
        while (arbol.raiz!!.der != null) {arbol.raiz = arbol.raiz!!.der}
        return arbol.raiz!!.c.obtenerInterprete()
    }

    fun minTitulo(nodo: Nodo?): String {
        val arbol = Arbol_Canciones()
        arbol.raiz = nodo
        if (arbol.raiz == null) {return ""}
        while (arbol.raiz!!.izq != null) {arbol.raiz = arbol.raiz!!.izq}
        return arbol.raiz!!.c.obtenerTitulo()
    }

    fun maxTitulo(nodo: Nodo?): String {
        val arbol = Arbol_Canciones()
        arbol.raiz = nodo
        if (arbol.raiz == null) {return ""}
        while (arbol.raiz!!.der != null) {arbol.raiz = arbol.raiz!!.der}
        return arbol.raiz!!.c.obtenerTitulo()
    }

    
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

    fun deArbolASecuencia(nodo: Nodo?): List<TAD_Cancion> {
        var result: List<TAD_Cancion>
        if (nodo == null) {return emptyList()}
        result = deArbolASecuencia(nodo.izq) + listOf(nodo.c) + deArbolASecuencia(nodo.der)
        return result
    }
}

/*fun main() {
    // Crear una nueva lista de reproducción
    val lista = TAD_Lista_De_Reproduccion("Mi lista")

    // Crear algunas canciones
    val cancion1 = TAD_Cancion("Bohemian Rhapsody", "Queen", "/ubicacion/de/la/cancion1")
    val cancion2 = TAD_Cancion("Stairway to Heaven", "Led Zeppelin", "/ubicacion/de/la/cancion2")
    val cancion3 = TAD_Cancion("Hotel California", "Eagles", "/ubicacion/de/la/cancion3")
    val cancion4 = TAD_Cancion("Callaíta", "Bad Bunny", "/ubicacion/de/la/cancion4")
    val cancion5 = TAD_Cancion("La Noche de Anoche", "Bad Bunny", "/ubicacion/de/la/cancion5")
    val cancion6 = TAD_Cancion("Callaíta", "Feid", "/ubicacion/de/la/cancion6")

    // Agregar las canciones a la lista de reproducción
    
    lista.agregarLista(cancion2)
    lista.agregarLista(cancion3)
    lista.agregarLista(cancion4)
    lista.agregarLista(cancion1)
    lista.agregarLista(cancion5)
    lista.agregarLista(cancion6)

    // Mostrar la lista de reproducción
    lista.mostrarLR()
    println("")
    println("")
    println(lista.obtenerLR())
    println("")
    println("")
    // Mostrar la canción Callaíta de Bad Bunny
    var cancion = lista.canciones.buscar(cancion4)
    // imprimir tipo de la variable cancion
    println(cancion?.javaClass)
    println(cancion)
    println("imprimiendo hijo derecho de cancion4 ${cancion?.der}")
    println("imprimiendo hijo izquierdo de cancion4 ${cancion?.izq}")

    println("")
    println("raiz de la lista de reproduccion ${lista.canciones.raiz?.c}")
    println("hijo der de raiz de la lista de reproduccion ${lista.canciones.raiz?.der?.c}")
    println("hijo izq de raiz de la lista de reproduccion ${lista.canciones.raiz?.izq?.c}")

    // Obtener y mostrar el intérprete y el título mínimos y máximos
    println("Intérprete mínimo: ${lista.minInterprete(lista.canciones.raiz)}")
    println("Intérprete máximo: ${lista.maxInterprete(lista.canciones.raiz)}")
    println("Título mínimo: ${lista.minTitulo(lista.canciones.raiz)}")
    println("Título máximo: ${lista.maxTitulo(lista.canciones.raiz)}")

    // Verificar si la lista de reproducción es un árbol de búsqueda binario
    println("¿Es un árbol de búsqueda binario? ${lista.esArbolDeBusqCancion(lista.canciones.raiz)}")

    // Eliminar una canción de la lista de reproducción
    lista.eliminarCancion("Led Zeppelin", "Stairway to Heaven")

    // Mostrar la lista de reproducción después de eliminar la canción
    lista.mostrarLR()
}*/