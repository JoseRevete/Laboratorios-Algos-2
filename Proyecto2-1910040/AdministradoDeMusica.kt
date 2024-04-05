import java.util.Scanner
import java.io.File

fun main(args : Array<String>) {
    val scanner = Scanner(System.`in`)
    val file = File(args[0])
    val canciones = file.readLines().map { it.split(";") }
    val lista = TAD_Lista_De_Reproduccion("Mi lista")
    println("Cargando lista de reproducción...")
    for (cancion in canciones) {
        println("Canción0: ${cancion[0]}")
        println("Canción1: ${cancion[1]}")
        println("Canción2: ${cancion[2]}")

        var song = TAD_Cancion(cancion[0], cancion[1], cancion[2])
        lista.agregarLista(song)
    }
    println("Lista de reproducción cargada")
    var salir = false
    var cancionActual = 0
    while (!salir) {
        val reproductor = TAD_Reproductor(lista.obtenerLR()[cancionActual])
        println("1. Cargar lista de reproduccion.")
        println("2. Mostrar lista de reproduccion.")
        println("3. Eliminar cancion.")
        println("4. Reproducir.")
        println("5. Pausar.")
        println("6. Parar.")
        println("7. Próxima canción.")
        println("8. Salir del administrador de música.")
        print("Seleccione una opción: ")
        val opcion = scanner.nextInt()
        when (opcion) {
            1 -> {
                println("Ingrese el nombre del archivo de la lista de reproducción: ")
                val archivo = scanner.next()
                val file = File(archivo)
                val canciones = file.readLines().map { it.split(";") }
                for (cancion in canciones) {
                    var song = TAD_Cancion(cancion[0], cancion[1], cancion[2])
                    lista.agregarLista(song)
                }
                println("Lista de reproducción cargada")
            }
            2 -> {
                lista.mostrarLR()
            }
            3 -> {
                lista.mostrarLR()
                print("Ingrese el intérprete de la canción a eliminar: ")
                val interprete = scanner.next()
                print("Ingrese el título de la canción a eliminar: ")
                val titulo = scanner.next()
                lista.eliminarCancion(interprete, titulo)
                println("Canción eliminada")
            }
            4 -> {
                reproductor.cargarCancion(lista.obtenerLR()[cancionActual])
                reproductor.reproducir()
            }
            5 -> {
                reproductor.pausa()
            }
            6 -> {
                reproductor.parar()
            }
            7 -> {
                cancionActual++
                if (reproductor.estaTocandoCancion()) {
                    reproductor.parar()
                    reproductor.cargarCancion(lista.obtenerLR()[cancionActual])
                    reproductor.reproducir()
                } else {
                    reproductor.cargarCancion(lista.obtenerLR()[cancionActual])
                    reproductor.reproducir()
                }
            }
            8 -> {
                salir = true
            }
        }
    }
}