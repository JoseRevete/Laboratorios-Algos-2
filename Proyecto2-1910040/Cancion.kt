import java.io.File

class TAD_Cancion {
    var titulo: String
    var interprete: String
    var ubicacion: String

    constructor(titulo: String, interprete: String, ubicacion: String) {
        this.titulo = titulo
        this.interprete = interprete
        this.ubicacion = ubicacion
    }

    fun obtenerTitulo(): String {
        return titulo
    }

    fun obtenerInterprete(): String {
        return interprete
    }

    fun obtenerUbicacion(): String {
        return ubicacion
    }

    fun esUbicacionValida(): Boolean {
        val file = File(ubicacion)
        return file.exists()
    }

    override fun toString(): String {
        return "Titulo: $titulo, Interprete: $interprete, Ubicacion: $ubicacion"
    }
}

/*fun main() {
    // Crear una instancia de TAD_Cancion
    val cancion = TAD_Cancion("Bohemian Rhapsody", "Queen", "/home/joserevete/Documentos/1.png")

    // Imprimir el título, intérprete y ubicación
    println("Titulo: ${cancion.obtenerTitulo()}")
    println("Interprete: ${cancion.obtenerInterprete()}")
    println("Ubicacion: ${cancion.obtenerUbicacion()}")

    // Imprimir la representación de la cadena de la canción
    println(cancion.toString())
}*/