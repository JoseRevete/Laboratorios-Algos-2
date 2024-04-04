import java.io.File
import java.io.FileInputStream

class TAD_Reproductor {
    var actual: TAD_Cancion
    var player: PausablePlayer? = null
    var reproduciendo : Boolean

    constructor(c: TAD_Cancion) {
        this.actual = c
        this.reproduciendo = false
    }

    fun cargarCancion(c: TAD_Cancion) {
        this.actual = c
        if (c.esUbicacionValida()) {
            val input = FileInputStream(this.actual.obtenerUbicacion())
            val file = File(c.ubicacion)
            if (file.exists()) {
                try {
                    player = PausablePlayer(input)
                }
                catch(e: javazoom.jl.decoder.JavaLayerException) {
                    println("Error al cargar la canción: ${e.message}")
                }
            } else {
                println("El archivo ${this.actual.obtenerUbicacion()} no existe")
            }
        } else {
            println("La ubicación ${this.actual.obtenerUbicacion()} no es válida")
        }
    }

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

    fun parar() {
        try {
            player?.stop()
            this.reproduciendo = false
        }
        catch(e: javazoom.jl.decoder.JavaLayerException) {
            println("Error al detener la canción: ${e.message}")
        }
    }

    fun pausa() {
        try {
            player?.pause()
        }
        catch(e: javazoom.jl.decoder.JavaLayerException) {
            println("Error al pausar la canción: ${e.message}")
        }
    }

    fun estaTocandoCancion(): Boolean {
        return reproduciendo
    }
}


/*fun main() {
    // Crear una instancia de TAD_Cancion
    val cancion = TAD_Cancion("sample", "Desconocido", "/home/joserevete/canciones/sample31.mp3")

    // Crear una instancia de TAD_Reproductor
    val reproductor = TAD_Reproductor(cancion)

    println("info de cancion: ${cancion.toString()}")

    // Cargar la canción en el reproductor
    reproductor.cargarCancion(cancion)

    // Reproducir la canción
    reproductor.reproducir()

    // Esperar 5 segundos
    Thread.sleep(5000)

    // Verificar si la canción está tocando
    println("¿Está tocando la canción? ${reproductor.estaTocandoCancion()}")

    // Pausar la canción
    reproductor.pausa()

    // Verificar si la canción está tocando
    println("¿Está tocando la canción? ${reproductor.estaTocandoCancion()}")

    // Parar la canción
    reproductor.parar()

    // Verificar si la canción está tocando
    println("¿Está tocando la canción? ${reproductor.estaTocandoCancion()}")
}*/