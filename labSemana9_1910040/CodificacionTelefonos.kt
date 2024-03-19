import java.io.File


var lineasDiccionario: Int = 0

fun main(args: Array<String>) : Unit {
    val nombreDiccionario = args[1]
    val diccionario = File(nombreDiccionario)
    val nombreAgenda = args[0]
    val agenda = File(nombreAgenda)
    lineasDiccionario = diccionario.readLines().size
    val matriz = Array(lineasDiccionario) { Array(2) { "" } }
    val palabras = diccionario.readLines()
    val palabrasSinGuiones = palabras.map { it.replace("-", "").replace("\"", "") }
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
    val tablaHash = TablaHash<Array<String>>(lineasDiccionario)
    for (palabra in matriz) {
        val codificacion = listOf(palabra[0])
        val palabraOriginal = listOf(palabra[1])
        val array = (codificacion + palabraOriginal).toTypedArray()
        tablaHash.insertar(array)
    }
    for (i in 0 until lineasDiccionario) {
    }
    val telefonos = agenda.readLines()
    val telefonosSinGuiones = telefonos.map { it.replace("-", "").replace("/", "") }
    var j = 0
    for (telefono in telefonosSinGuiones) {
        val listaTelefono = ListaDoblementeEnlazada<String>()
        val listaTelefono2 = ListaDoblementeEnlazada<String>()
        val caracteres = telefono.toCharArray()
        for (caracter in caracteres) {
            listaTelefono.agregarAlFinal(caracter.toString())
            listaTelefono2.agregarAlFinal(caracter.toString())
        }
        var nodoActual = listaTelefono.primero()
        var i = 0
        var listaPrincipio = ListaDoblementeEnlazada<String>()
        var listaPrincipio2 = ListaDoblementeEnlazada<String>()
        while (listaTelefono.tamano() > i) {
            var codificacionTelefono = ListaDoblementeEnlazada<String>()
            var codificacion2 : ListaDoblementeEnlazada<String> = ListaDoblementeEnlazada()
            if (i == 0) {}
            else {
                nodoActual = listaTelefono.siguiente(nodoActual!!)
                listaPrincipio.agregarAlFinal(nodoActual?.anterior?.dato!!)
                listaPrincipio2.agregarAlFinal(nodoActual.anterior?.dato!!)
                listaTelefono.eliminarPrimero()
                listaTelefono2.eliminarPrimero()
                codificacion2 = codificacionTelefonos(listaPrincipio, tablaHash, listaPrincipio2)
            }
            var codificacion = codificacionTelefonos(listaTelefono, tablaHash, listaTelefono2)
            if (i == 0) {codificacionTelefono = codificacion}
            else {
                for (nodo in codificacion2) {
                    for (nodo2 in codificacion) {
                        codificacionTelefono.agregarAlFinal(nodo.dato +" "+ nodo2.dato)}
                }
            }
            i++
            imprimirResultados(codificacionTelefono, telefonos[j])
        }
        j++
    }
}

fun dameTamanoTabla(): Int {
    return lineasDiccionario
}



fun codificacionTelefonos(listaTelefono: ListaDoblementeEnlazada<String>, tablaHash: TablaHash<Array<String>>, listaOriginal : ListaDoblementeEnlazada<String>): ListaDoblementeEnlazada<String> {
    var listaT = listaTelefono
    var listaFinal = ListaDoblementeEnlazada<String>()
    var booleano = true
    if (listaT.tamano() < listaOriginal.tamano()) {
        booleano = false}
    var listaDeOpciones : ListaDoblementeEnlazada<String> = ListaDoblementeEnlazada()
    var  i  = 1
    var suma = 0
    var listaTelefonoEnString = listaT.imprimir()
    listaTelefonoEnString = listaTelefonoEnString.replace(" ", "")
    for (nodo in listaT) {
        suma += nodo.dato.toInt() * i
        i++}
    val hash = suma % dameTamanoTabla()
    if (listaT.tamano() == 1) {
        listaDeOpciones = ListaDoblementeEnlazada<String>()
        try{
            for (arrays in tablaHash.get(hash)!!) {
                if (arrays.dato[0] == listaTelefonoEnString) {listaDeOpciones.agregarAlFinal(arrays.dato[1])}
            }}
        catch(e: NullPointerException) {}
        if (listaDeOpciones.estaVacia()) {
            listaDeOpciones.agregarAlFinal(listaTelefonoEnString)}
        return listaDeOpciones
    }
    try {
        for (arrays in tablaHash.get(hash)!!) {
            if (arrays.dato[0] == listaTelefonoEnString) {
                listaDeOpciones.agregarAlFinal(arrays.dato[1])}
        }}
    catch(e: NullPointerException) {}
    var z = 1
        while (z < listaOriginal.tamano()) {
            var listaUnion = ListaDoblementeEnlazada<String>()
            repeat(z) {
                listaFinal.agregarAlInicio(listaT.ultimo()?.dato!!)
                listaT.eliminarUltimo()
            }
            var codif1 = codificacionTelefonos(listaT, tablaHash, listaOriginal)
            var codif2 = codificacionTelefonos(listaFinal, tablaHash, listaOriginal)
            for (nodo in codif1) {
                for (nodo2 in codif2) {
                    try {
                        nodo.dato.toInt()
                        nodo2.dato.toInt()
                        continue}
                    catch(e: NumberFormatException) {listaUnion.agregarAlFinal(nodo.dato +" "+ nodo2.dato)}
                    }
                }
            for (elemento in listaUnion) {
                listaDeOpciones.agregarAlFinal(elemento.dato)
            }
            if (!booleano) {return listaDeOpciones}
            z++
            listaT = ListaDoblementeEnlazada()
            listaFinal = ListaDoblementeEnlazada()
            for (elemento in listaOriginal) {
                listaT.agregarAlFinal(elemento.dato)
            }
        }
    
    return listaDeOpciones
}

fun imprimirResultados(lista: ListaDoblementeEnlazada<String>, telefono: String) {
    for (nodo in lista) {
        var palabra = depurar(nodo.dato)
        if (palabra == "-1") {continue}
        else{println("$telefono: ${nodo.dato}")}
    }
}

fun depurar(palabra: String): String {
    var palabraDepurada = ""
    val caracteres = palabra.replace(" ", "").toCharArray()
    var i = 0
    while (i < caracteres.size -1) {
        if (caracteres[i].isDigit() && caracteres[i+1].isDigit()) {
            palabraDepurada = "-1"
            break}
        i++
    }
    return palabraDepurada
}