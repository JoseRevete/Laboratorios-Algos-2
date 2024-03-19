// Se importa la librería File
import java.io.File

// Se inicializa la variable lineasDiccionario
var lineasDiccionario: Int = 0

// Se crea la función principal main
fun main(args: Array<String>) : Unit {
    // Se inicializan las variables nombreDiccionario y nombreAgenda
    val nombreDiccionario = args[1]
    val diccionario = File(nombreDiccionario)
    val nombreAgenda = args[0]
    val agenda = File(nombreAgenda)
    // Se inicializa la variable lineasDiccionario con el tamaño del diccionario
    lineasDiccionario = diccionario.readLines().size
    // Se crea una matriz con el tamaño del diccionario
    val matriz = Array(lineasDiccionario) { Array(2) { "" } }
    // Se inicializa la variable palabras con las palabras del diccionario
    val palabras = diccionario.readLines()
    // Se reemplazan los guiones y las comillas de las palabras
    val palabrasSinGuiones = palabras.map { it.replace("-", "").replace("\"", "") }
    // Se codifican las palabras del diccionario
    for (palabra in palabrasSinGuiones) { 
        var codificacionPalabra = ""
        val caracteres = palabra.toCharArray() 
        for (caracter in caracteres) {
            when (caracter) {
                'E', 'e' -> codificacionPalabra += "0"
                'J', 'N', 'Q', 'j', 'n', 'q' -> codificacionPalabra += "1"
                'R', 'W', 'X', 'r', 'w', 'x' -> codificacionPalabra += "2"
                'D', 'S', 'Y', 'd', 's', 'y' -> codificacionPalabra += "3"
                'F', 'T', 'f', 't' -> codificacionPalabra += "4"
                'A', 'M', 'a', 'm' -> codificacionPalabra += "5"
                'C', 'I', 'V', 'c', 'i', 'v' -> codificacionPalabra += "6"
                'B', 'K', 'U', 'b', 'k', 'u' -> codificacionPalabra += "7"
                'L', 'O', 'P', 'l', 'o', 'p' -> codificacionPalabra += "8"
                'G', 'H', 'Z', 'g', 'h', 'z' -> codificacionPalabra += "9"
            }
        }
        matriz[palabrasSinGuiones.indexOf(palabra)][0] = codificacionPalabra
        matriz[palabrasSinGuiones.indexOf(palabra)][1] = palabras[palabrasSinGuiones.indexOf(palabra)]
    }
    // Se crea una tabla hash con las palabras del diccionario
    val tablaHash = TablaHash<Array<String>>(lineasDiccionario)
    for (palabra in matriz) {
        val codificacion = listOf(palabra[0])
        val palabraOriginal = listOf(palabra[1])
        val array = (codificacion + palabraOriginal).toTypedArray()
        tablaHash.insertar(array)
    }
    // Se inicializa la variable telefonos con los teléfonos de la agenda
    val telefonos = agenda.readLines()
    // Se reemplazan los guiones y las barras de los teléfonos
    val telefonosSinGuiones = telefonos.map { it.replace("-", "").replace("/", "") }
    var j = 0
    // Se codifican los teléfonos de la agenda
    for (telefono in telefonosSinGuiones) {
        // Se inicializan las listas listaTelefono y listaTelefono2
        val listaTelefono = ListaDoblementeEnlazada<String>()
        val listaTelefono2 = ListaDoblementeEnlazada<String>()
        // Se inicializa la variable caracteres con los caracteres del teléfono
        val caracteres = telefono.toCharArray()
        // Se agregan los caracteres a las listas listaTelefono y listaTelefono2
        for (caracter in caracteres) {
            listaTelefono.agregarAlFinal(caracter.toString())
            listaTelefono2.agregarAlFinal(caracter.toString())
        }
        // Se inicializa la variable nodoActual con el primer nodo de la lista listaTelefono
        var nodoActual = listaTelefono.primero()
        var i = 0
        // Se inicializan las listas listaPrincipio y listaPrincipio2
        var listaPrincipio = ListaDoblementeEnlazada<String>()
        var listaPrincipio2 = ListaDoblementeEnlazada<String>()
        // Se codifican los teléfonos
        while (listaTelefono.tamano() > i) {
            // Se inicializan las listas codificacionTelefono y codificacion2
            var codificacionTelefono = ListaDoblementeEnlazada<String>()
            var codificacion2 : ListaDoblementeEnlazada<String> = ListaDoblementeEnlazada()
            if (i == 0) {}
            else {
                // Se inicializa la variable nodoActual con el siguiente nodo de la lista listaTelefono
                nodoActual = listaTelefono.siguiente(nodoActual!!)
                // Se agregan los nodos de la lista listaTelefono a las listas listaPrincipio y listaPrincipio2
                listaPrincipio.agregarAlFinal(nodoActual?.anterior?.dato!!)
                listaPrincipio2.agregarAlFinal(nodoActual.anterior?.dato!!)
                // Se eliminan los primeros nodos de las listas listaTelefono y listaTelefono2
                listaTelefono.eliminarPrimero()
                listaTelefono2.eliminarPrimero()
                // Se llama a la función codificacionTelefonos
                codificacion2 = codificacionTelefonos(listaPrincipio, tablaHash, listaPrincipio2)
            }
            // Se llama a la función codificacionTelefonos
            var codificacion = codificacionTelefonos(listaTelefono, tablaHash, listaTelefono2)

            if (i == 0) {codificacionTelefono = codificacion}
            else {
                // Se unen las listas codificacion2 y codificacion
                for (nodo in codificacion2) {
                    for (nodo2 in codificacion) {
                        codificacionTelefono.agregarAlFinal(nodo.dato +" "+ nodo2.dato)}
                }
            }
            i++
            // Se llama a la función imprimirResultados
            imprimirResultados(codificacionTelefono, telefonos[j])
        }
        j++
    }
}

// Se crea la función dameTamanoTabla para obtener el tamaño de la tabla
fun dameTamanoTabla(): Int {
    return lineasDiccionario
}

// Se crea la función codificacionTelefonos para codificar los teléfonos
fun codificacionTelefonos(listaTelefono: ListaDoblementeEnlazada<String>, tablaHash: TablaHash<Array<String>>, listaOriginal : ListaDoblementeEnlazada<String>): ListaDoblementeEnlazada<String> {
    // Se inicializan las variables listaT, listaFinal y booleano
    var listaT = listaTelefono
    var listaFinal = ListaDoblementeEnlazada<String>()
    var booleano = true
    // Se verifica si es la ultima recursión
    if (listaT.tamano() < listaOriginal.tamano()) {
        booleano = false}
    // Se inicializan las variables listaDeOpciones, i y suma
    var listaDeOpciones : ListaDoblementeEnlazada<String> = ListaDoblementeEnlazada()
    var  i  = 1
    var suma = 0
    // Se inicializa la variable listaTelefonoEnString con la lista listaTelefono en formato String
    var listaTelefonoEnString = listaT.imprimir()
    // Se reemplazan los espacios de la variable listaTelefonoEnString
    listaTelefonoEnString = listaTelefonoEnString.replace(" ", "")
    // Se inicializa la variable hash con el residuo de la suma por la posición cada elemento de la lista listaTelefono
    for (nodo in listaT) {
        suma += nodo.dato.toInt() * i
        i++}
    val hash = suma % dameTamanoTabla()
    // Se verifica si la lista listaT tiene un solo elemento
    if (listaT.tamano() == 1) {
        listaDeOpciones = ListaDoblementeEnlazada<String>()
        // Se verifica si el elemento de la lista listaT está en la tabla hash
        try{
            for (arrays in tablaHash.get(hash)!!) {
                if (arrays.dato[0] == listaTelefonoEnString) {listaDeOpciones.agregarAlFinal(arrays.dato[1])}
            }}
        catch(e: NullPointerException) {}
        // Se verifica si la listaDeOpciones está vacía
        if (listaDeOpciones.estaVacia()) {
            listaDeOpciones.agregarAlFinal(listaTelefonoEnString)}
        return listaDeOpciones
    }
    // Se verifica si la lista listaT tiene más de un elemento
    try {
        for (arrays in tablaHash.get(hash)!!) {
            if (arrays.dato[0] == listaTelefonoEnString) {
                listaDeOpciones.agregarAlFinal(arrays.dato[1])}
        }}
    catch(e: NullPointerException) {}
    var z = 1
    // Se itera z hasta que sea menor que el tamaño de la listaOriginal
    while (z < listaOriginal.tamano()) {
        // Se inicializa la variable listaUnion con una lista vacía
        var listaUnion = ListaDoblementeEnlazada<String>()
        // Se itera z veces para agregar los elementos de la lista listaT a la lista listaFinal y eliminarlos de la lista listaT
        repeat(z) {
            listaFinal.agregarAlInicio(listaT.ultimo()?.dato!!)
            listaT.eliminarUltimo()
        }
        // Se llama a la función codificacionTelefonos
        var codif1 = codificacionTelefonos(listaT, tablaHash, listaOriginal)
        var codif2 = codificacionTelefonos(listaFinal, tablaHash, listaOriginal)
        // Se itera sobre los elementos de las listas codif1 y codif2
        for (nodo in codif1) {
            for (nodo2 in codif2) {
                try {
                    // Se verifica si los elementos de las listas codif1 y codif2 son números
                    nodo.dato.toInt()
                    nodo2.dato.toInt()
                    continue}
                catch(e: NumberFormatException) {listaUnion.agregarAlFinal(nodo.dato +" "+ nodo2.dato)}
                }
            }
        // Se itera sobre los elementos de la lista listaUnion para agregarlos a la lista listaDeOpciones
        for (elemento in listaUnion) {
            listaDeOpciones.agregarAlFinal(elemento.dato)
        }
        // Se verifica si es la ultima recursión
        if (!booleano) {return listaDeOpciones}
        z++
        listaT = ListaDoblementeEnlazada()
        listaFinal = ListaDoblementeEnlazada()
        // Se agregan los elementos de la lista listaOriginal a la lista listaT
        for (elemento in listaOriginal) {
            listaT.agregarAlFinal(elemento.dato)
        }
    }
    return listaDeOpciones
}

// Se crea la función imprimirResultados para imprimir los resultados
fun imprimirResultados(lista: ListaDoblementeEnlazada<String>, telefono: String) : Unit{
    for (nodo in lista) {
        var palabra = depurar(nodo.dato)
        // Se verifica si la palabra devuelta es -1
        if (palabra == "-1") {continue}
        else{println("$telefono: ${nodo.dato}")}
    }
}

// Se crea la función depurar para depurar las palabras
fun depurar(palabra: String): String {
    var palabraDepurada = ""
    // Se reemplazan los espacios de la palabra
    val caracteres = palabra.replace(" ", "").toCharArray()
    var i = 0
    // Se itera sobre los elementos de la lista caracteres
    while (i < caracteres.size -1) {
        // Se verifica si hay dos números seguidos en la palabra
        if (caracteres[i].isDigit() && caracteres[i+1].isDigit()) {
            palabraDepurada = "-1"
            break}
        i++
    }
    return palabraDepurada
}