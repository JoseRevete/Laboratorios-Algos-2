class TAD_Lista_De_Reproduccion {
    var nombre: String
    var canciones: Arbol_Canciones

    constructor(nombre: String) {
        this.nombre = nombre
        this.canciones = Arbol_Canciones()
    }

    fun agregarLista(cancion: TAD_Cancion) {canciones.agregar(cancion)}

    fun eliminarCancion(interprete: String, titulo: String) : Unit {canciones.eliminar(interprete, titulo)}

    fun obtenerLR(): List<TAD_Cancion> {return canciones.deArbolASecuencia()}

    fun mostrarLR() {canciones.imprimirArbolCompleto()}

    fun minInterprete(): String {return canciones.minInterprete()}

    fun maxInterprete(): String {return canciones.maxInterprete()}

    fun minTitulo(): String {return canciones.minTitulo()}

    fun maxTitulo(): String {return canciones.maxTitulo()}

    fun esArbolDeBusqCancion(): Boolean {return canciones.esArbolDeBusqCancion()}
}

fun main() {
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
    lista.agregarLista(cancion1)
    lista.agregarLista(cancion2)
    lista.agregarLista(cancion3)
    lista.agregarLista(cancion4)
    lista.agregarLista(cancion5)
    lista.agregarLista(cancion6)

    // Mostrar la lista de reproducción
    lista.mostrarLR()

    // Obtener y mostrar el intérprete y el título mínimos y máximos
    println("Intérprete mínimo: ${lista.minInterprete()}")
    println("Intérprete máximo: ${lista.maxInterprete()}")
    println("Título mínimo: ${lista.minTitulo()}")
    println("Título máximo: ${lista.maxTitulo()}")

    // Verificar si la lista de reproducción es un árbol de búsqueda binario
    println("¿Es un árbol de búsqueda binario? ${lista.esArbolDeBusqCancion()}")

    // Eliminar una canción de la lista de reproducción
    lista.eliminarCancion("Led Zeppelin", "Stairway to Heaven")

    // Mostrar la lista de reproducción después de eliminar la canción
    lista.mostrarLR()
}