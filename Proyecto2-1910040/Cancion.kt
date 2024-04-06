import java.io.File

// Clase que contiene una canción
class TAD_Cancion {
    var titulo: String
    var interprete: String
    var ubicacion: String

    // Constructor de la clase
    constructor(titulo: String, interprete: String, ubicacion: String) {
        this.titulo = titulo
        this.interprete = interprete
        this.ubicacion = ubicacion
    }

    // Función que obtiene el título de la canción
    fun obtenerTitulo(): String {
        return titulo
    }

    // Función que obtiene el intérprete de la canción
    fun obtenerInterprete(): String {
        return interprete
    }

    // Función que obtiene la ubicación de la canción
    fun obtenerUbicacion(): String {
        return ubicacion
    }

    // Función que verifica si la ubicación de la canción es válida
    fun esUbicacionValida(): Boolean {
        val file = File(ubicacion)
        return file.exists()
    }

    // Función que convierte la canción a una cadena
    override fun toString(): String {
        return "Titulo: $titulo, Interprete: $interprete, Ubicacion: $ubicacion"
    }
}