import java.io.File

/* Funcion principal para ejecutar otros algoritmos que ayudaran a resolver el problema de entre varias ciudades en un mapa, dividir el mapa en dos partes iguales, buscar la ruta que genere mas beneficio y luego unir las rutas para obtener las rutas.
   La primera linea es la cantidad de ciudades que se encuentran en el mapa.
   Las siguientes lineas son las coordenadas de las ciudades, de la forma "x y z", donde x es la coordenada en el eje "x", y es la coordenada en el eje "y" y z es la recompensa de ir a esa ciudad.
   La salida es una lista de rutas que generan el mayor beneficio posible.
   El beneficio de una ruta es la suma de las recompensas de las ciudades que la componen menos la distancia recorrida entre las ciudades.
*/
fun main(args: Array<String>) : Unit {
    val nombreArchivo = args[0]
    val archivo = File(nombreArchivo)
    var ciudades = archivo.bufferedReader().readLines().first().toInt()
    var coordenadasList = archivo.bufferedReader().readLines().drop(1).map { 
        it.split(" ").take(2).map { num -> num.toInt() } }
    var recompensaList = archivo.bufferedReader().readLines().drop(1).map { 
        it.split(" ").map { num -> num.toInt() }[2]}
    
    var coordenadass : List<List<Int>> = coordenadasList.mapIndexed { index, list -> 
    list + index }
    var coordenadas : Array<List<Int>> = coordenadass.toTypedArray()
    
    var recompensa : Array<Array<Int>> = Array(ciudades) { Array(2) {0} }
    println("ciudades: "+ciudades.toString())
    for (i in 0 until ciudades) {
        recompensa[i][0] = recompensaList[i]
        recompensa[i][1] = i
    }

    println("coordenadas: "+coordenadas.contentToString())
    println("recompensa: "+recompensa.map { it.contentToString() })
    var ruta = divideAndConquerPRMB(ciudades, coordenadas, recompensa)
    println("Ruta: "+ruta.first+" ganancia: "+ruta.second.toString())
}





fun divideAndConquerPRMB( ciudades: Int, coordenadas: Array<List<Int>>, recompensa: Array<Array<Int>>) : Pair<String, Double>{
    var ruta = Pair("", 0.0)
    if (ciudades <= 3) {
        ruta = resolverMiniPRMB(coordenadas, recompensa)}
    else if (ciudades == 0){}
    else {
        var maxX = coordenadas.maxBy { it[0] }!![0]
        var maxXmitad = maxX / 2
        var coordenadasIzq = coordenadas.filter { it[0] <= maxXmitad }
        var coordenadasDer = coordenadas.filter { it[0] > maxXmitad }
        var recompensaIzq = MutableList(coordenadasIzq.size) { arrayOf(0, 0) }
        var recompensaDer = MutableList(coordenadasDer.size) { arrayOf(0, 0)}
        var i = 0
        var j = 0
        while (i < coordenadasIzq.size){
            j = i
            while (j < ciudades){
                if (coordenadasIzq[i][2] == recompensa[j][1]) {
                    recompensaIzq[i] = recompensa[j]
                    break}
                j++}
            i++}
        i = 0   
        j = 0
        while (i < coordenadasDer.size){
            j = i
            while (j < ciudades){
                if (coordenadasDer[i][2] == recompensa[j][1]) {
                    recompensaDer[i] = recompensa[j]
                    break}
                j++}
            i++}
        
        var coordenadasIzqq = coordenadasIzq.toTypedArray()
        var coordenadasDerr = coordenadasDer.toTypedArray()
        var rutaIzq = divideAndConquerPRMB(coordenadasIzq.size, coordenadasIzqq, recompensaIzq.toTypedArray())
        var rutaDer = divideAndConquerPRMB(coordenadasDer.size, coordenadasDerr, recompensaDer.toTypedArray())
        ruta = unirRutasCasos(rutaIzq, rutaDer, coordenadasIzqq, coordenadasDerr)
    }
    println("rutaenalgos: "+ruta.first+" "+ruta.second.toString())
    return ruta
}




fun resolverMiniPRMB(coordenadas: Array<List<Int>>, recompensa: Array<Array<Int>>) : Pair<String, Double> {
    val ciudades = coordenadas.size
    var ruta = Pair("", 0.0)
    println("")
    println("------------------------")
    println("ciudades en resolverMiniPRMB: "+ciudades.toString())
    println("coordenadas en resolverMiniPRMB: "+coordenadas.contentToString())
    println("recompensa en resolverMiniPRMB: "+recompensa.map { it.contentToString() })
    if (ciudades == 1) {
        var ganancia = recompensa[0][0]
        ruta = Pair(coordenadas[0][2].toString(), ganancia.toDouble())
    }
    else if (ciudades == 2) {
        var ganancia = gananciaRuta(coordenadas, recompensa)
        
        if (ganancia.second > recompensa[0][0]) {
            if (ganancia.second > recompensa[1][0]) {
                ruta = ganancia
                }
            else {ruta = Pair(coordenadas[0][1].toString(), recompensa[0][0].toDouble())}
        } else {
            if (recompensa[1][0] > recompensa[0][0]) {
                ruta = Pair(coordenadas[1][2].toString(), recompensa[1][0].toDouble())}
            else {ruta = Pair(coordenadas[0][2].toString(), recompensa[0][0].toDouble())}
        }
    }
    else if (ciudades == 3) {
        var ganancia12 = gananciaRuta(coordenadas.sliceArray(0 until 2), recompensa.sliceArray(0 until 2))
        var ganancia23 = gananciaRuta(coordenadas.sliceArray(1 until 3), recompensa.sliceArray(1 until 3))
        var ganancia13 = gananciaRuta(coordenadas.slice(listOf(0, 2)).toTypedArray(), recompensa.slice(listOf(0, 2)).toTypedArray())
        var ganancia123 = Pair(ganancia12.first + ganancia23.first, ganancia12.second + ganancia23.second)
        var ganancia132 = Pair(ganancia13.first + coordenadas[2][2].toString()+" "+coordenadas[1][2].toString() , ganancia13.second + ganancia23.second)
        var ganancia213 = Pair(coordenadas[1][2].toString()+" "+coordenadas[0][2].toString()+" " + ganancia13.first, ganancia12.second + ganancia13.second)
        var ruta1 = Pair("", 0.0)
        var ruta2 = Pair("", 0.0)
        var ruta3 = Pair("", 0.0)

        if (ganancia123.second > ganancia132.second) {
            if (ganancia123.second > ganancia213.second) {
                ruta3 = Pair(ganancia123.first, ganancia123.second)}
            else {ruta3 = Pair(ganancia213.first, ganancia213.second)}
        } else {
            if (ganancia132.second > ganancia213.second) {
                ruta3 = Pair(ganancia132.first, ganancia132.second)}
            else {ruta3 = Pair(ganancia213.first, ganancia213.second)}
        }

        if (ganancia12.second > ganancia23.second) {
            if (ganancia12.second > ganancia13.second) {
                ruta2 = Pair(ganancia12.first, ganancia12.second)}
            else {ruta2 = Pair(ganancia13.first, ganancia13.second)}
        } else {
            if (ganancia23.second > ganancia13.second) {
                ruta2 = Pair(ganancia23.first, ganancia23.second)}
            else {ruta2 = Pair(ganancia13.first, ganancia13.second)}
        }

        if (recompensa[0][0] > recompensa[1][0]) {
            if (recompensa[0][0] > recompensa[2][0]) {
                ruta1 = Pair(coordenadas[0][2].toString(), recompensa[0][0].toDouble())}
            else {ruta1 = Pair(coordenadas[2][2].toString(), recompensa[2][0].toDouble())}
        } else {
            if (recompensa[1][0] > recompensa[2][0]) {
                ruta1 = Pair(coordenadas[1][2].toString(), recompensa[1][0].toDouble())}
            else {ruta1 = Pair(coordenadas[2][2].toString(), recompensa[2][0].toDouble())}
        }

        if (ruta1.second > ruta2.second) {
            if (ruta1.second > ruta3.second) {
                ruta = ruta1}
            else {ruta = ruta3}
        } else {
            if (ruta2.second > ruta3.second) {
                ruta = ruta2}
            else {ruta = ruta3}
        }
    }
    else {println("Error")}
    println("ruta: "+ruta.first+" "+ruta.second.toString())
    return ruta
}

fun gananciaRuta(coordenadas: Array<List<Int>>, recompensa: Array<Array<Int>>) : Pair<String, Double> {
    var ganancia = 0.0
    var premio = 0.0
    var rutaString = ""
    for (i in 0 until coordenadas.size) {
        premio += recompensa[i][0].toDouble()
        rutaString += recompensa[i][1].toString() + " "
    }
    var costos = costo(coordenadas)
    ganancia = premio - costos
    var rutaGanancia = Pair(rutaString, ganancia)
    return rutaGanancia
}

fun costo(coordenadas: Array<List<Int>>) : Double {
    var cost = 0.0
    for (i in 0 until coordenadas.size - 1) {
        cost += Math.sqrt(Math.pow((coordenadas[i][0] - coordenadas[i+1][0]).toDouble(), 2.0) + Math.pow((coordenadas[i][1] - coordenadas[i+1][1]).toDouble(), 2.0))
    }
    return cost
}

// Funcion para unir las rutas.
fun unirRutasCasos(rutaIzq: Pair<String, Double>, rutaDer: Pair<String, Double>, coordenadasIzq : Array<List<Int>>, coordenadasDer : Array<List<Int>>) : Pair<String, Double> {
    var ruta = Pair("", 0.0)
    var rutaExtremos = unirExtremos(rutaIzq, rutaDer, coordenadasIzq, coordenadasDer)
    var rutaMedios = unirMedios(rutaIzq, rutaDer, coordenadasIzq, coordenadasDer)
    var rutaMediosExtremos = unirMediosExtremos(rutaIzq, rutaDer, coordenadasIzq, coordenadasDer)
    if (rutaExtremos.second > rutaMedios.second) {
        if (rutaExtremos.second > rutaMediosExtremos.second) {
            ruta = rutaExtremos}
        else {ruta = rutaMediosExtremos}
    } else {
        if (rutaMedios.second > rutaMediosExtremos.second) {
            ruta = rutaMedios}
        else {ruta = rutaMediosExtremos}
    }
    return ruta
}

fun unirExtremos(rutaIzq: Pair<String, Double>, rutaDer: Pair<String, Double>, coordenadasIzq : Array<List<Int>>, coordenadasDer : Array<List<Int>>) : Pair<String, Double> {
    var ruta = Pair("", 0.0)
    var rutaIzqString = rutaIzq.first.split(" ")
    var rutaDerString = rutaDer.first.split(" ")
    // coordenadasIzq y coordenadasDer son las coordenadas de las ciudades que componen las rutas, estas son listas
    var coordIzqPrincipio = List(1) {0}
    var coordDerPrincipio = List(1) {0}
    var coordIzqFinal = List(1) {0}
    var coordDerFinal = List(1) {0}
    for (i in 0 until coordenadasIzq.size) {
        if (rutaIzqString[0] == coordenadasIzq[i][2].toString()) {
            coordIzqPrincipio = coordenadasIzq[i]}
        if (rutaIzqString[rutaIzqString.size - 1] == coordenadasIzq[i][2].toString()) {
            coordIzqFinal = coordenadasIzq[i]}
    }
    for (i in 0 until coordenadasDer.size) {
        if (rutaDerString[0] == coordenadasDer[i][2].toString()) {
            coordDerPrincipio = coordenadasDer[i]}
        if (rutaDerString[rutaDerString.size - 1] == coordenadasDer[i][2].toString()) {
            coordDerFinal = coordenadasDer[i]}
    }
    var costo1 = costo(arrayOf(coordIzqPrincipio, coordDerPrincipio))
    var costo2 = costo(arrayOf(coordIzqFinal, coordDerFinal))
    var costo3 = costo(arrayOf(coordIzqPrincipio, coordDerFinal))
    var costo4 = costo(arrayOf(coordIzqFinal, coordDerPrincipio))
    // Encontrar menor costo
    var costos = listOf(costo1, costo2, costo3, costo4)
    var menorCosto = costos.min()
    var menorCostoIndex = costos.indexOf(menorCosto)
    var rutaString = ""
    if (menorCostoIndex == 0) {
        for (i in rutaIzqString.size - 1 downTo 0) {
            rutaString += rutaIzqString[i] + " "
        }
        rutaString += rutaDer.first}
    else if (menorCostoIndex == 1) {
        rutaString = rutaIzq.first + " "
        for (i in rutaDerString.size - 1 downTo 0) {
            rutaString += rutaDerString[i] + " "
        }}
    else if (menorCostoIndex == 2) {
        for (i in rutaIzqString.size - 1 downTo 0) {
            rutaString += rutaIzqString[i] + " "
        }
        for (i in rutaDerString.size - 1 downTo 0) {
            rutaString += rutaDerString[i] + " "
        }}
    else if (menorCostoIndex == 3) {rutaString = rutaIzq.first + " " + rutaDer.first}
    else {println("Error")}
    var ganancia = rutaIzq.second + rutaDer.second - menorCosto!!
    ruta = Pair(rutaString, ganancia)
    return ruta
}

fun unirMedios(rutaIzq: Pair<String, Double>, rutaDer: Pair<String, Double>, coordenadasIzq : Array<List<Int>>, coordenadasDer : Array<List<Int>>) : Pair<String, Double> {
    var ruta = Pair("", 0.0)
    var rutaString = ""
    var rutaIzqString = rutaIzq.first.split(" ")
    var rutaDerString = rutaDer.first.split(" ")
    // guardar coordenadas de los puntos medios
    var coordIzqMedio = List(rutaIzqString.size) {0}
    var coordDerMedio = List(rutaDerString.size) {0}
    var coordIzqPrincipio = List(1) {0}
    var coordDerPrincipio = List(1) {0}
    var coordIzqFinal = List(1) {0}
    var coordDerFinal = List(1) {0}
    for (i in 0 until coordenadasIzq.size) {
        for (j in 0 until rutaIzqString.size) {
            if (rutaIzqString[j] == coordenadasIzq[i][2].toString()) {
                coordIzqMedio = coordenadasIzq[i]}
        }
    }
    for (i in 0 until coordenadasDer.size) {
        for (j in 0 until rutaDerString.size) {
            if (rutaDerString[j] == coordenadasDer[i][2].toString()) {
                coordDerMedio = coordenadasDer[i]}
        }
    }
    for (i in 0 until coordenadasIzq.size) {
        if (rutaIzqString[0] == coordenadasIzq[i][2].toString()) {
            coordIzqPrincipio = coordenadasIzq[i]}
        if (rutaIzqString[rutaIzqString.size - 1] == coordenadasIzq[i][2].toString()) {
            coordIzqFinal = coordenadasIzq[i]}
    }
    for (i in 0 until coordenadasDer.size) {
        if (rutaDerString[0] == coordenadasDer[i][2].toString()) {
            coordDerPrincipio = coordenadasDer[i]}
        if (rutaDerString[rutaDerString.size - 1] == coordenadasDer[i][2].toString()) {
            coordDerFinal = coordenadasDer[i]}
    }

    // Unir dos puntos del medio
    for (i in 1 until coordIzqMedio.size) {
        for (j in 1 until coordDerMedio.size) {
            var costoPuntosMedio = costo(arrayOf(coordIzqMedio[i], coordDerMedio[j]))
            if (orientacion = "x") {
                if (coordDerPrincipio[1] >= coordDerFinal[1] && coordIzqPrincipio[1] >= coordIzqFinal[1]) {

                    // Caso con rutas hacia arriba y union de extremos
                    var costo1 = costo(arrayOf(coordIzqMedio[i], coordIzqMedio[i+1]))
                    var costo2 = costo(arrayOf(coordDerMedio[j], coordDerMedio[j+1]))
                    var costoIzq = costo(arrayOf(coordIzqPrincipio, coordIzqFinal))
                    var costoDer = costo(arrayOf(coordDerPrincipio, coordDerFinal))
                    var gananciaCaso1 = rutaIzq.second + rutaDer.second - costoDer - costoIzq - costoPuntosMedio + costo1 + costo2

                    // Caso con rutas hacia arriba y union de un extremo y la ruta de abajo
                    var costo3 = costo(arrayOf(coordIzqMedio[i+1], coordDerMedio[i+1]))
                    var gananciaCaso2 = rutaIzq.second + rutaDer.second - costoDer - costo3 - costoPuntosMedio + costo1 + costo2
                    var gananciaCaso3 = rutaIzq.second + rutaDer.second - costoIzq - costo3 - costoPuntosMedio + costo1 + costo2

                    // Caso con rutas hacia abajo y union de extremos
                    var costo4 = costo(arrayOf(coordIzqMedio[i-1], coordIzqMedio[i]))
                    var costo5 = costo(arrayOf(coordDerMedio[j-1], coordDerMedio[j]))
                    var gananciaCaso4 = rutaIzq.second + rutaDer.second - costoDer - costoIzq - costoPuntosMedio + costo4 + costo5

                    // Caso con rutas hacia abajo y union de un extremo y la ruta de arriba
                    var costo6 = costo(arrayOf(coordIzqMedio[i-1], coordDerMedio[i-1]))
                    var gananciaCaso5 = rutaIzq.second + rutaDer.second - costoIzq - costo6 - costoPuntosMedio + costo4 + costo5
                    var gananciaCaso6 = rutaIzq.second + rutaDer.second - costoDer - costo6 - costoPuntosMedio + costo4 + costo5

                    // Caso con ruta hacia arriba y la otra hacia abajo
                    var costo7 = costo(arrayOf(coordIzqPrincipio, coordDerPrincipio))
                    var costo8 = costo(arrayOf(coordIzqFinal, coordDerFinal))
                    var gananciaCaso7 = rutaIzq.second + rutaDer.second - costo7 - costo8 - costoPuntosMedio + costo1 + costo5
                    var gananciaCaso8 = rutaIzq.second + rutaDer.second - costoIzq - costoDer - costoPuntosMedio + costo1 + costo5

                    // Caso con ruta hacia abajo y la otra hacia arriba
                    var gananciaCaso9 = rutaIzq.second + rutaDer.second - costo7 - costo8 - costoPuntosMedio + costo4 + costo2
                    var gananciaCaso10 = rutaIzq.second + rutaDer.second - costoIzq - costoDer - costoPuntosMedio + costo4 + costo2

                    // Encontrar mayor ganancia
                    var ganancias = listOf(gananciaCaso1, gananciaCaso2, gananciaCaso3, gananciaCaso4, gananciaCaso5, gananciaCaso6, gananciaCaso7, gananciaCaso8, gananciaCaso9, gananciaCaso10)
                    var mayorGanancia = ganancias.max()
                    var mayorGananciaIndex = ganancias.indexOf(mayorGanancia)
                    if (mayorGananciaIndex == 0) {
                        for (z in i+1 until coordIzqMedio.size) {rutaString += rutaIzqString[z] + " "}
                        for (z in 0 until i+1) {rutaString += rutaIzqString[z] + " "}
                        for (z in j downTo 0) {rutaString += rutaDerString[z] + " "}
                        for (z in coordDerMedio.size - 1 downTo j+1) {rutaString += rutaDerString[z] + " "}
                    }

                    else if (mayorGananciaIndex == 1) {
                        for (z in 0 until i+1) {rutaString += rutaIzqString[z] + " "}
                        for (z in j downTo 0) {rutaString += rutaDerString[z] + " "}
                        for (z in coordDerMedio.size - 1 downTo j+1) {rutaString += rutaDerString[z] + " "}
                        for (z in i+1 until coordIzqMedio.size) {rutaString += rutaIzqString[z] + " "}
                    }


                    else if (mayorGananciaIndex == 2) {
                        for (z in coordDerMedio.size - 1 downTo j+1) {rutaString += rutaDerString[z] + " "}
                        for (z in i+1 until coordIzqMedio.size) {rutaString += rutaIzqString[z] + " "}
                        for (z in 0 until i+1) {rutaString += rutaIzqString[z] + " "}
                        for (z in j downTo 0) {rutaString += rutaDerString[z] + " "}
                    }

                    else if (mayorGananciaIndex == 3) {
                        for (z in i-1 downTo 0) {rutaString += rutaIzqString[z] + " "}
                        for (z in coordIzqMedio.size - 1 downTo i) {rutaString += rutaIzqString[z] + " "}
                        for (z in j+1 until coordDerMedio.size) {rutaString += rutaDerString[z] + " "}
                        for (z in 0 until j+1) {rutaString += rutaDerString[z] + " "}
                    }

                    else if (mayorGananciaIndex == 4) {
                        for (z in 0 until j) {rutaString += rutaDerString[z] + " "}
                        for (z in i-1 downTo 0) {rutaString += rutaIzqString[z] + " "}
                        for (z in coordIzqMedio.size - 1 downTo i) {rutaString += rutaIzqString[z] + " "}
                        for (z in j until coordDerMedio.size) {rutaString += rutaDerString[z] + " "}
                    }

                    else if (mayorGananciaIndex == 5) {
                        for (z in 0 until i) {rutaString += rutaIzqString[z] + " "}
                        for (z in j-1 downTo 0) {rutaString += rutaDerString[z] + " "}
                        for (z in coordDerMedio.size - 1 downTo j) {rutaString += rutaDerString[z] + " "}
                        for (z in i until coordIzqMedio.size) {rutaString += rutaIzqString[z] + " "}
                    }

                    else if (mayorGananciaIndex == 6) {
                        for (z in j-1 downTo 0) {rutaString += rutaDerString[z] + " "}
                        for (z in 0 until i+1) {rutaString += rutaIzqString[z] + " "}
                        for (z in j+1 until coordDerMedio.size) {rutaString += rutaDerString[z] + " "}
                        for (z in coordIzqMedio.size - 1 downTo i+1) {rutaString += rutaIzqString[z] + " "}
                    }

                    else if (mayorGananciaIndex == 7) {
                        for (z in i+1 until coordIzqMedio.size) {rutaString += rutaIzqString[z] + " "}
                        for (z in 0 until i+1) {rutaString += rutaIzqString[z] + " "}
                        for (z in j+1 until coordDerMedio.size) {rutaString += rutaDerString[z] + " "}
                        for (z in 0 until j) {rutaString += rutaDerString[z] + " "}
                    }

                    else if (mayorGananciaIndex == 8) {
                        for (z in i-1 downTo 0) {rutaString += rutaIzqString[z] + " "}
                        for (z in 0 until j+1) {rutaString += rutaDerString[z] + " "}
                        for (z in i until coordIzqMedio.size) {rutaString += rutaIzqString[z] + " "}
                        for (z in coordDerMedio.size - 1 downTo j+1) {rutaString += rutaDerString[z] + " "}
                    }

                    else if (mayorGananciaIndex == 9) {
                        for (z in i-1 downTo 0) {rutaString += rutaIzqString[z] + " "}
                        for (z in coordIzqMedio.size - 1 downTo i) {rutaString += rutaIzqString[z] + " "}
                        for (z in j downTo 0) {rutaString += rutaDerString[z] + " "}
                        for (z in coordDerMedio.size - 1 downTo j+1) {rutaString += rutaDerString[z] + " "}
                    }

                    else {println("Error")}
                }
                else if (coordDerPrincipio[1] <= coordDerFinal[1] && coordIzqPrincipio[1] <= coordIzqFinal[1]) {

                    // Caso con rutas hacia abajo y union de extremos
                    var costo1 = costo(arrayOf(coordIzqMedio[i], coordIzqMedio[i-1]))
                    var costo2 = costo(arrayOf(coordDerMedio[j], coordDerMedio[j-1]))
                    var costoIzq = costo(arrayOf(coordIzqPrincipio, coordIzqFinal))
                    var costoDer = costo(arrayOf(coordDerPrincipio, coordDerFinal))
                    var gananciaCaso1 = rutaIzq.second + rutaDer.second - costoDer - costoIzq - costoPuntosMedio + costo1 + costo2

                    // Caso con rutas hacia abajo y union de un extremo y la ruta de arriba
                    var costo3 = costo(arrayOf(coordIzqMedio[i-1], coordDerMedio[i-1]))
                    var gananciaCaso2 = rutaIzq.second + rutaDer.second - costoDer - costo3 - costoPuntosMedio + costo1 + costo2
                    var gananciaCaso3 = rutaIzq.second + rutaDer.second - costoIzq - costo3 - costoPuntosMedio + costo1 + costo2

                    // Caso con rutas hacia arriba y union de extremos
                    var costo4 = costo(arrayOf(coordIzqMedio[i+1], coordIzqMedio[i]))
                    var costo5 = costo(arrayOf(coordDerMedio[j+1], coordDerMedio[j]))
                    var gananciaCaso4 = rutaIzq.second + rutaDer.second - costoDer - costoIzq - costoPuntosMedio + costo4 + costo5

                    // Caso con rutas hacia arriba y union de un extremo y la ruta de abajo
                    var costo6 = costo(arrayOf(coordIzqMedio[i+1], coordDerMedio[i+1]))
                    var gananciaCaso5 = rutaIzq.second + rutaDer.second - costoIzq - costo6 - costoPuntosMedio + costo4 + costo5
                    var gananciaCaso6 = rutaIzq.second + rutaDer.second - costoDer - costo6 - costoPuntosMedio + costo4 + costo5

                    // Caso con ruta hacia abajo y la otra hacia arriba
                    var costo7 = costo(arrayOf(coordIzqPrincipio, coordDerPrincipio))
                    var costo8 = costo(arrayOf(coordIzqFinal, coordDerFinal))
                    var gananciaCaso7 = rutaIzq.second + rutaDer.second - costo7 - costo8 - costoPuntosMedio + costo1 + costo5
                    var gananciaCaso8 = rutaIzq.second + rutaDer.second - costoIzq - costoDer - costoPuntosMedio + costo1 + costo5

                    // Caso con ruta hacia arriba y la otra hacia abajo
                    var gananciaCaso9 = rutaIzq.second + rutaDer.second - costo7 - costo8 - costoPuntosMedio + costo4 + costo2
                    var gananciaCaso10 = rutaIzq.second + rutaDer.second - costoIzq - costoDer - costoPuntosMedio + costo4 + costo2

                    // Encontrar mayor ganancia
                    var ganancias = listOf(gananciaCaso1, gananciaCaso2, gananciaCaso3, gananciaCaso4, gananciaCaso5, gananciaCaso6, gananciaCaso7, gananciaCaso8, gananciaCaso9, gananciaCaso10)
                    var mayorGanancia = ganancias.max()
                    var mayorGananciaIndex = ganancias.indexOf(mayorGanancia)
                    if (mayorGananciaIndex == 0) {
                        for (z in i-1 downTo 0) {rutaString += rutaIzqString[z] + " "}
                        for (z in coordIzqMedio.size - 1 downTo i) {rutaString += rutaIzqString[z] + " "}
                        for (z in j until coordDerMedio.size) {rutaString += rutaDerString[z] + " "}
                        for (z in 0 until j) {rutaString += rutaDerString[z] + " "}
                    }

                    else if (mayorGananciaIndex == 1) {
                        for (z in 0 until i) {rutaString += rutaIzqString[z] + " "}
                        for (z in j-1 downTo 0) {rutaString += rutaDerString[z] + " "}
                        for (z in coordDerMedio.size - 1 downTo j) {rutaString += rutaDerString[z] + " "}
                        for (z in i until coordIzqMedio.size) {rutaString += rutaIzqString[z] + " "}
                    }

                    else if (mayorGananciaIndex == 2) {
                        for (z in coordDerMedio.size - 1 downTo j-1) {rutaString += rutaDerString[z] + " "}
                        for (z in i-1 downTo 0) {rutaString += rutaIzqString[z] + " "}
                        for (z in coordIzqMedio.size - 1 downTo i) {rutaString += rutaIzqString[z] + " "}
                        for (z in j until coordDerMedio.size) {rutaString += rutaDerString[z] + " "}
                    }

                    else if (mayorGananciaIndex == 3) {
                        for (z in i+1 until coordIzqMedio.size) {rutaString += rutaIzqString[z] + " "}
                        for (z in 0 until i+1) {rutaString += rutaIzqString[z] + " "}
                        for (z in j downTo 0) {rutaString += rutaDerString[z] + " "}
                        for (z in coordDerMedio.size - 1 downTo j+1) {rutaString += rutaDerString[z] + " "}
                    }

                    else if (mayorGananciaIndex == 4) {
                        for (z in 0 until j) {rutaString += rutaDerString[z] + " "}
                        for (z in i  downTo 0) {rutaString += rutaIzqString[z] + " "}
                        for (z in coordIzqMedio.size - 1 downTo i+1) {rutaString += rutaIzqString[z] + " "}
                        for (z in j+1 until coordDerMedio.size) {rutaString += rutaDerString[z] + " "}
                    }

                    else if (mayorGananciaIndex == 5) {
                        for (z in 0 until i) {rutaString += rutaIzqString[z] + " "}
                        for (z in j downTo 0) {rutaString += rutaDerString[z] + " "}
                        for (z in coordDerMedio.size - 1 downTo j+1) {rutaString += rutaDerString[z] + " "}
                        for (z in i+1 until coordIzqMedio.size) {rutaString += rutaIzqString[z] + " "}
                    }

                    else if (mayorGananciaIndex == 6) {
                        for (z in j+1 until coordDerMedio.size) {rutaString += rutaDerString[z] + " "}
                        for (z in coordIzqMedio.size - 1 downTo i) {rutaString += rutaIzqString[z] + " "}
                        for (z in j downTo 0) {rutaString += rutaDerString[z] + " "}
                        for (z in 0 until i-1) {rutaString += rutaIzqString[z] + " "}
                    }

                    else if (mayorGananciaIndex == 7) {
                        for (z in i-1 downTo 0) {rutaString += rutaIzqString[z] + " "}
                        for (z in coordIzqMedio.size - 1 downTo i) {rutaString += rutaIzqString[z] + " "}
                        for (z in j downTo 0) {rutaString += rutaDerString[z] + " "}
                        for (z in coordDerMedio.size - 1 downTo j+1) {rutaString += rutaDerString[z] + " "}
                    }

                    else if (mayorGananciaIndex == 8) {
                        for (z in i+1 until coordIzqMedio.size) {rutaString += rutaIzqString[z] + " "}
                        for (z in coordDerMedio.size - 1 downTo j) {rutaString += rutaDerString[z] + " "}
                        for (z in i downTo 0) {rutaString += rutaIzqString[z] + " "}
                        for (z in 0 until j) {rutaString += rutaDerString[z] + " "}
                    }

                    else if (mayorGananciaIndex == 9) {
                        for (z in i+1 until coordIzqMedio.size) {rutaString += rutaIzqString[z] + " "}
                        for (z in 0 until i+1) {rutaString += rutaIzqString[z] + " "}
                        for (z in j until coordDerMedio.size) {rutaString += rutaDerString[z] + " "}
                        for (z in 0 until j) {rutaString += rutaDerString[z] + " "}
                    }
                        
                    else {println("Error")}
                }

                else if (coordDerPrincipio[1] >= coordDerFinal[1] && coordIzqPrincipio[1] <= coordIzqFinal[1]) {

                    // Caso con rutas hacia arriba y union de extremos
                    var costo1 = costo(arrayOf(coordIzqMedio[i], coordIzqMedio[i-1]))
                    var costo2 = costo(arrayOf(coordDerMedio[j], coordDerMedio[j+1]))
                    var costoIzq = costo(arrayOf(coordIzqPrincipio, coordIzqFinal))
                    var costoDer = costo(arrayOf(coordDerPrincipio, coordDerFinal))
                    var gananciaCaso1 = rutaIzq.second + rutaDer.second - costoDer - costoIzq - costoPuntosMedio + costo1 + costo2

                    // Caso con rutas hacia arriba y union de un extremo y la ruta de abajo
                    var costo3 = costo(arrayOf(coordIzqMedio[i-1], coordDerMedio[i-1]))
                    var gananciaCaso2 = rutaIzq.second + rutaDer.second - costoDer - costo3 - costoPuntosMedio + costo1 + costo2
                    var gananciaCaso3 = rutaIzq.second + rutaDer.second - costoIzq - costo3 - costoPuntosMedio + costo1 + costo2

                    // Caso con rutas hacia abajo y union de extremos
                    var costo4 = costo(arrayOf(coordIzqMedio[i+1], coordIzqMedio[i]))
                    var costo5 = costo(arrayOf(coordDerMedio[j-1], coordDerMedio[j]))
                    var gananciaCaso4 = rutaIzq.second + rutaDer.second - costoDer - costoIzq - costoPuntosMedio + costo4 + costo5

                    // Caso con rutas hacia abajo y union de un extremo y la ruta de arriba
                    var costo6 = costo(arrayOf(coordIzqMedio[i+1], coordDerMedio[i+1]))
                    var gananciaCaso5 = rutaIzq.second + rutaDer.second - costoIzq - costo6 - costoPuntosMedio + costo4 + costo5
                    var gananciaCaso6 = rutaIzq.second + rutaDer.second - costoDer - costo6 - costoPuntosMedio + costo4 + costo5

                    // Caso con ruta hacia arriba y la otra hacia abajo
                    var costo7 = costo(arrayOf(coordIzqPrincipio, coordDerPrincipio))
                    var costo8 = costo(arrayOf(coordIzqFinal, coordDerFinal))
                    var gananciaCaso7 = rutaIzq.second + rutaDer.second - costo7 - costo8 - costoPuntosMedio + costo1 + costo5
                    var gananciaCaso8 = rutaIzq.second + rutaDer.second - costoIzq - costoDer - costoPuntosMedio + costo1 + costo5

                    // Caso con ruta hacia abajo y la otra hacia arriba
                    var gananciaCaso9 = rutaIzq.second + rutaDer.second - costo7 - costo8 - costoPuntosMedio + costo4 + costo2
                    var gananciaCaso10 = rutaIzq.second + rutaDer.second - costoIzq - costoDer - costoPuntosMedio + costo4 + costo2

                    // Encontrar mayor ganancia
                    var ganancias = listOf(gananciaCaso1, gananciaCaso2, gananciaCaso3, gananciaCaso4, gananciaCaso5, gananciaCaso6, gananciaCaso7, gananciaCaso8, gananciaCaso9, gananciaCaso10)
                    var mayorGanancia = ganancias.max()
                    var mayorGananciaIndex = ganancias.indexOf(mayorGanancia)
                    
                        



                        

           
            }
        }
    }


}

fun unirMediosExtremos{}