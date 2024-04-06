import java.util.Scanner
import java.io.File
import java.util.InputMismatchException

// Funcion principal
fun main(args : Array<String>) {
    // Mensaje de bienvenida
    println("\u001B[32mBienvenido al ADMINISTRADOR DE MUSICA\u001B[0m")
    println("Debe ingresar el numero de la opción que desea realizar")
    println()
    // Scanner para leer la entrada del usuario
    val scanner = Scanner(System.`in`)
    // Verificar que se haya ingresado un archivo
    val file = File(args[0])
    // Leer las canciones del archivo y separarlas por ;
    val canciones = file.readLines().map { it.split(";") }
    val lista = TAD_Lista_De_Reproduccion("Mi lista")
    // Agregar las canciones a la lista de reproducción
    for (cancion in canciones) {
        var song = TAD_Cancion(cancion[1], cancion[0], cancion[2])
        lista.agregarLista(song)
    }
    var salir = false
    var cancionActual = 0
    var booleano = false
    // Reproductor de música
    var reproductor = TAD_Reproductor(lista.obtenerLR()[cancionActual])
    // Menú de opciones
    while (!salir) {
        println("\u001B[32mOpciones disponibles:\u001B[0m")
        println("\u001B[34m1. Cargar lista de reproduccion.\u001B[0m")
        println("\u001B[34m2. Mostrar lista de reproduccion.\u001B[0m")
        println("\u001B[34m3. Eliminar cancion.\u001B[0m")
        println("\u001B[34m4. Reproducir.\u001B[0m")
        println("\u001B[34m5. Pausar.\u001B[0m")
        println("\u001B[34m6. Parar.\u001B[0m")
        println("\u001B[34m7. Próxima canción.\u001B[0m")
        println("\u001B[34m8. Volver a escuchar Lista de Reproducción.\u001B[0m")
        println("\u001B[34m9. Ayuda.\u001B[0m")
        println("\u001B[34m10. Salir del administrador de música.\u001B[0m")
        var verificar = false
        var opcion = 0
        // Verificar que la opción ingresada sea un número entre 1 y 10
        while (!verificar) {
            try {
                print("Seleccione una opción: ")
                opcion = scanner.nextInt()
                while (opcion < 1 || opcion > 10) {
                    println("\u001B[31mOpción inválida: Debe ser un número entre 1 y 10\u001B[0m")
                    print("Seleccione una opción: ")
                    opcion = scanner.nextInt()
                }
                verificar = true
            } catch(e: InputMismatchException) {
                println("\u001B[31mOpción inválida\u001B[0m")
                scanner.nextLine()
            }
        }
        // Realizar la acción correspondiente a la opción seleccionada
        when (opcion) {
            1 -> {
                // Cargar una lista de reproducción desde un archivo
                println("Ingrese el nombre del archivo de la lista de reproducción: ")
                val archivo = scanner.next()
                val nuevoArchivo = File(archivo)
                val cancionesN = nuevoArchivo.readLines().map { it.split(";") }
                for (cancion in cancionesN) {
                    var song = TAD_Cancion(cancion[1], cancion[0], cancion[2])
                    lista.agregarLista(song)
                }
                println("Lista de reproducción cargada")
                println("Recomendacion: Utilice la opción 8 para volver a escuchar la lista de reproducción en el nuevo orden")
            }
            2 -> {
                // Mostrar la lista de reproducción actual
                println()
                lista.mostrarLR()
            }
            3 -> {
                // Eliminar una canción de la lista de reproducción
                println()
                lista.mostrarLR()
                println()
                print("Ingrese el intérprete de la canción a eliminar: ")
                val interprete = scanner.next()
                print("Ingrese el título de la canción a eliminar: ")
                val titulo = scanner.next()
                var comprobar = lista.eliminarCancion(interprete, titulo)
                if (comprobar) {println(); println("Canción eliminada")}
            }
            4 -> {
                // Reproducir la canción actual
                try {
                    if (booleano) {
                        reproductor.resumir()
                    }
                    else {
                        reproductor = TAD_Reproductor(lista.obtenerLR()[cancionActual])
                        reproductor.cargarCancion(lista.obtenerLR()[cancionActual])
                        reproductor.reproducir()
                        booleano = reproductor.estaTocandoCancion()
                        println()
                        println("Reproduciendo: ${lista.obtenerLR()[cancionActual].titulo} - ${lista.obtenerLR()[cancionActual].interprete}")
                    }
                } catch(e: IndexOutOfBoundsException) {
                    println()
                    println("\u001B[31mNo hay más canciones en la lista de reproducción\u001B[0m")
                }
            }
            5 -> {
                // Pausar la canción actual
                try {
                    if (booleano) {
                    reproductor.pausa()
                    println()
                    println("Canción pausada: ${lista.obtenerLR()[cancionActual].titulo} - ${lista.obtenerLR()[cancionActual].interprete}")
                    booleano = reproductor.estaTocandoCancion()}
                    else {println("\u001B[31mNo hay canción en reproducción\u001B[0m")}
                }
                catch(e: IndexOutOfBoundsException) {
                    println()
                    println("\u001B[31mNo hay más canciones en la lista de reproducción o no hay canción en reproducción\u001B[0m")
                }
            }
            6 -> {
                // Detener la canción actual y al reproducir inicia desde el comienzo esta
                try {
                    if (booleano) {
                        reproductor.parar()
                        booleano = reproductor.estaTocandoCancion()}
                    else {
                        println()
                        println("\u001B[31mNo hay canción en reproducción\u001B[0m")
                    }
                } catch(e: IndexOutOfBoundsException) {
                    println()
                    println("\u001B[31mNo hay más canciones en la lista de reproducción o no hay canción en reproducción\u001B[0m")
                }
            }
            7 -> {
                // Reproducir la siguiente canción en la lista de reproducción
                try {
                    cancionActual++
                    if (booleano) {
                        reproductor.parar()
                        reproductor = TAD_Reproductor(lista.obtenerLR()[cancionActual])
                        var bool = reproductor.cargarCancion(lista.obtenerLR()[cancionActual])
                        if (bool) {
                            reproductor.reproducir()
                            booleano = reproductor.estaTocandoCancion()
                            println()
                            println("Reproduciendo: ${lista.obtenerLR()[cancionActual].titulo} - ${lista.obtenerLR()[cancionActual].interprete}")}
                    } else {
                        reproductor = TAD_Reproductor(lista.obtenerLR()[cancionActual])
                        var bool = reproductor.cargarCancion(lista.obtenerLR()[cancionActual])
                        if (bool) {
                            reproductor.reproducir()
                            booleano = reproductor.estaTocandoCancion()
                            println()
                            println("Reproduciendo: ${lista.obtenerLR()[cancionActual].titulo} - ${lista.obtenerLR()[cancionActual].interprete}")}
                    }
                }
                catch(e: IndexOutOfBoundsException) {
                    println()
                    println("\u001B[31mNo hay más canciones en la lista de reproducción\u001B[0m")
                }
            }
            8 -> {
                // Volver a reproducir la lista de reproducción desde el principio
                cancionActual = 0
                if (booleano) {
                    reproductor.parar()
                    reproductor = TAD_Reproductor(lista.obtenerLR()[cancionActual])
                    reproductor.cargarCancion(lista.obtenerLR()[cancionActual])
                    reproductor.reproducir()
                    booleano = reproductor.estaTocandoCancion()
                    println()
                    println("Reproduciendo: ${lista.obtenerLR()[cancionActual].titulo} - ${lista.obtenerLR()[cancionActual].interprete}")
                }
                else {
                    reproductor = TAD_Reproductor(lista.obtenerLR()[cancionActual])
                    reproductor.cargarCancion(lista.obtenerLR()[cancionActual])
                    reproductor.reproducir()
                    booleano = reproductor.estaTocandoCancion()
                    println()
                    println("Reproduciendo: ${lista.obtenerLR()[cancionActual].titulo} - ${lista.obtenerLR()[cancionActual].interprete}")
                }
            }
            9 -> {
                // Mostrar las opciones disponibles
                println()
                println("\u001B[33m//////////////////////////////////////////////////\u001B[0m")
                println("\u001B[33mAyuda\u001B[0m")
                println("\u001B[33m1. Cargar lista de reproducción: Permite cargar una lista de reproducción desde un archivo.\u001B[0m")
                println("\u001B[33m2. Mostrar lista de reproducción: Muestra la lista de reproducción actual.\u001B[0m")
                println("\u001B[33m3. Eliminar canción: Permite eliminar una canción de la lista de reproducción.\u001B[0m")
                println("\u001B[33m4. Reproducir: Reproduce la canción actual.\u001B[0m")
                println("\u001B[33m5. Pausar: Pausa la canción actual.\u001B[0m")
                println("\u001B[33m6. Parar: Detiene la canción actual y al reproducir inicia desde el comienzo esta.\u001B[0m")
                println("\u001B[33m7. Próxima canción: Reproduce la siguiente canción en la lista de reproducción.\u001B[0m")
                println("\u001B[33m8. Volver a escuchar Lista de Reproducción: Vuelve a reproducir la lista de reproducción desde el principio.\u001B[0m")
                println("\u001B[33m9. Ayuda: Muestra las opciones disponibles.\u001B[0m")
                println("\u001B[33m10. Salir del administrador de música: Sale del administrador de música.\u001B[0m")
                println("\u001B[33m//////////////////////////////////////////////////\u001B[0m")
            }
            10 -> {
                // Salir del administrador de música
                salir = true
            }
        }
        println()
    }
}