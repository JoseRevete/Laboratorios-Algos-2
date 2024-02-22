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
    for (i in 0 until ciudades) {
        recompensa[i][0] = recompensaList[i]
        recompensa[i][1] = i
    }

    var ruta = divideAndConquerPRMB(ciudades, coordenadas, recompensa)
    println("La ruta que genera mas beneficio es: " + ruta.first + " con un beneficio de: " + ruta.second)
}





fun divideAndConquerPRMB( ciudades: Int, coordenadas: Array<List<Int>>, recompensa: Array<Array<Int>>) : Pair<String, Double>{
    var ruta = Pair("", 0.0)
    var suma = 0
    var divisionEnY = false
    for (i in 0 until coordenadas.size) {
        for (j in 0 until coordenadas.size) {
            if (coordenadas[i][1] == coordenadas[j][1] && i != j) {suma += 1}
        }
        if (suma > 3) {divisionEnY = true}
    }

    if (0 < ciudades && ciudades <= 3) {
        ruta = resolverMiniPRMB(coordenadas, recompensa)}
    else if (ciudades == 0){}
    else {

        if (divisionEnY) {
            var maxY = coordenadas.maxBy { it[1] }!![1]
            var maxYmitad = maxY / 2
            var coordenadasArriba = coordenadas.filter { it[1] <= maxYmitad }
            var coordenadasAbajo = coordenadas.filter { it[1] > maxYmitad }
            var recompensaArriba = MutableList(coordenadasArriba.size) { arrayOf(0, 0) }
            var recompensaAbajo = MutableList(coordenadasAbajo.size) { arrayOf(0, 0)}
            var i = 0
            var j = 0
            while (i < coordenadasArriba.size){
                j = i
                while (j < ciudades){
                    if (coordenadasArriba[i][2] == recompensa[j][1]) {
                        recompensaArriba[i] = recompensa[j]
                        break}
                    j++}
                i++}
            i = 0   
            j = 0
            while (i < coordenadasAbajo.size){
                j = i
                while (j < ciudades){
                    if (coordenadasAbajo[i][2] == recompensa[j][1]) {
                        recompensaAbajo[i] = recompensa[j]
                        break}
                    j++}
                i++}
            
            var coordenadasArribaa = coordenadasArriba.toTypedArray()
            var coordenadasAbajoo = coordenadasAbajo.toTypedArray()
            var rutaArriba = divideAndConquerPRMB(coordenadasArriba.size, coordenadasArribaa, recompensaArriba.toTypedArray())
            var rutaAbajo = divideAndConquerPRMB(coordenadasAbajo.size, coordenadasAbajoo, recompensaAbajo.toTypedArray())
            ruta = unirRutasCasos(rutaArriba, rutaAbajo, coordenadasArribaa, coordenadasAbajoo)
        }


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
    }
    return ruta
}




fun resolverMiniPRMB(coordenadas: Array<List<Int>>, recompensa: Array<Array<Int>>) : Pair<String, Double> {
    val ciudades = coordenadas.size
    var ruta = Pair("", 0.0)
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
        var ruta12 = ganancia12.first.split(" ")
        var ruta23 = ganancia23.first.split(" ")
        var ruta13 = ganancia13.first.split(" ")
        var ganancia123 = Pair(ganancia12.first+" " + ruta23[1], ganancia12.second + ganancia23.second)
        var ganancia132 = Pair(ganancia13.first+" " + ruta23[0] , ganancia13.second + ganancia23.second)
        var ganancia213 = Pair(ruta12[1]+" "+ ganancia13.first, ganancia12.second + ganancia13.second)

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
    else {println("Error1")}
    return ruta
}

fun gananciaRuta(coordenadas: Array<List<Int>>, recompensa: Array<Array<Int>>) : Pair<String, Double> {
    var ganancia = 0.0
    var premio = 0.0
    var rutaString = ""
    for (i in 0 until coordenadas.size) {
        premio += recompensa[i][0].toDouble()
        if (i == coordenadas.size - 1) {rutaString += recompensa[i][1].toString()}
        else {rutaString += recompensa[i][1].toString() + " "}

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
    var rutaPrincipio = (rutaIzq.first +" "+ rutaDer.first).split(" ")
    var rutaGananciaPrincipio = 0.0
    var rutasPermutadas = permutaciones(rutaPrincipio)
    var coordenadas = coordenadasIzq + coordenadasDer
    var mayorGanancia = 0.0
    var indice = 0
    var i = 0


    for (permutaciones in rutasPermutadas) {
        var c1 = coordenadasIzq[0]
        var c2 = coordenadasDer[0]
        for (i in 0 until permutaciones.size - 1) {
            for (j in 0 until coordenadas.size) {
                if (coordenadas[j][2] == permutaciones[i].toInt()) {c1 = coordenadas[j]}
                if (coordenadas[j][2] == permutaciones[i+1].toInt()) {c2 = coordenadas[j]}
            }
            mayorGanancia += c1[2] + c2[2] - costo(arrayOf(c1, c2))
        }

        if (mayorGanancia > rutaGananciaPrincipio) {
            rutaGananciaPrincipio = mayorGanancia
            indice = i}
        i++
    }

    if (mayorGanancia > rutaIzq.second) {
        if (mayorGanancia > rutaDer.second) {ruta = Pair(rutasPermutadas[indice].joinToString(" "), mayorGanancia)}
        else {ruta = rutaDer}
    
    } else {
        if (rutaDer.second > rutaIzq.second) {ruta = rutaDer}
        else {ruta = rutaIzq}
    }
    return ruta
}

fun permutaciones(rutaPrincipio: List<String>): List<List<String>> {
    if (rutaPrincipio.size == 1) return listOf(rutaPrincipio)

    val permutacion = mutableListOf<List<String>>()
    val caracter = rutaPrincipio[0]
    val rem = rutaPrincipio.drop(1)
    for (perm in permutaciones(rem)) {
        for (i in 0..perm.size) {
            val nuevaPermutacion = perm.toMutableList()
            nuevaPermutacion.add(i, caracter)
            permutacion.add(nuevaPermutacion)
        }
    }
    return permutacion
}