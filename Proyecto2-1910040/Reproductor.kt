import java.io.File
import java.io.FileInputStream

// Clase que contiene un reproductor de canciones
class TAD_Reproductor {
    var actual: TAD_Cancion
    var player: PausablePlayer? = null
    var reproduciendo = false

    // Constructor de la clase
    constructor(c: TAD_Cancion) {
        this.actual = c
        this.reproduciendo = false
    }

    // Función que carga una canción en el reproductor
    fun cargarCancion(c: TAD_Cancion) : Boolean{
        this.actual = c
        if (c.esUbicacionValida()) {
            val input = FileInputStream(this.actual.obtenerUbicacion())
            val file = File(c.ubicacion)
            if (file.exists()) {
                try {
                    player = PausablePlayer(input)
                    return true
                }
                catch(e: javazoom.jl.decoder.JavaLayerException) {
                    println("Error al cargar la canción: ${e.message}")
                    return false
                }
            } else {
                println("El archivo ${this.actual.obtenerUbicacion()} no existe")
                return false
            }
        } else {println("La ubicación ${this.actual.obtenerUbicacion()} no es válida"); return false}
    }

    // Función que reproduce una canción
    fun reproducir() {
        try {
            player?.play()
            this.reproduciendo = true
        }
        catch(e: javazoom.jl.decoder.JavaLayerException) {
            println("Error al reproducir la canción: ${e.message}")
            this.reproduciendo = false
        }
    }

    // Función que detiene una canción
    fun parar() {
        try {
            player?.stop()
            this.reproduciendo = false
        }
        catch(e: javazoom.jl.decoder.JavaLayerException) {
            println("Error al detener la canción: ${e.message}")
        }
    }

    // Función que resume una canción
    fun resumir() {
        try {
            player?.resume()
            this.reproduciendo = true
        }
        catch(e: javazoom.jl.decoder.JavaLayerException) {
            println("Error al resumir la canción: ${e.message}")
        }
    }

    // Función que pausa una canción
    fun pausa() {
        try {
            player?.pause()
        }
        catch(e: javazoom.jl.decoder.JavaLayerException) {
            println("Error al pausar la canción: ${e.message}")
        }
    }

    // Función que verifica si el reproductor está tocando una canción
    fun estaTocandoCancion(): Boolean {
        return reproduciendo
    }
}