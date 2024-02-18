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
        ruta = unirRutas(rutaIzq, rutaDer, coordenadasIzqq, coordenadasDerr)
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
    // El costo de una ruta viene dado por una función c, que corresponde a la distancia recorrida por el agente al visitar cada una de las ciudades de la misma.
    var costo = 0.0
    for (i in 0 until coordenadas.size - 1) {
        costo += Math.sqrt(Math.pow((coordenadas[i][0] - coordenadas[i+1][0]).toDouble(), 2.0) + Math.pow((coordenadas[i][1] - coordenadas[i+1][1]).toDouble(), 2.0))
    }
    ganancia = premio - costo
    var rutaGanancia = Pair(rutaString, ganancia)
    return rutaGanancia
}

// Funcion para unir las rutas de las dos partes del mapa, este debe buscar la mejor ruta para unir las dos partes. Puede  eliminar rutas ya existentes para obtener una mejor ruta en cuanto al benefico.
fun unirRutas(rutaIzq: Pair<String, Double>, rutaDer: Pair<String, Double>, coordenadasIzq : Array<List<Int>>, coordenadasDer : Array<List<Int>>) : Pair<String, Double> {
    var ganancia = 0.0
    var ruta = ""
    println("rutas izq: "+rutaIzq.first+"   ganancia: "+rutaIzq.second.toString())
    println("rutas der: "+rutaDer.first+"   ganancia: "+rutaDer.second.toString())
    println("coordenadas izq: "+coordenadasIzq.contentToString())
    println("coordenadas der: "+coordenadasDer.contentToString())
    var i = 0
    var j = 0
    var rutasizq = rutaIzq.first.split(" ") 
    var rutasder = rutaDer.first.split(" ")
    while (i < rutasizq.size) {
        j = 0
        while (j < coordenadasIzq.size){
            if (rutasizq[i] == coordenadasIzq[j][2].toString()) {
                println("coordenadasIzq: "+ coordenadasIzq[j].toString())
                break}
            j++
        }
        i++
    }
    
    i = 0
    j = 0
    while (i < rutasder.size) {
        j = 0
        while (j < coordenadasDer.size){
            if (rutasder[i] == coordenadasDer[j][2].toString()) {
                println("coordenadasDer: "+ coordenadasDer[j].toString())
                break}
            j++
        }
        i++
    }
        
    return Pair(ruta, ganancia)
}