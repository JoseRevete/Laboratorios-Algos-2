import java.io.File

/* Funcion principal para ejecutar otros algoritmos que ayudaran a resolver el problema de entre varias ciudades en un mapa, dividir el mapa en dos partes iguales, buscar la ruta que genere mas beneficio y luego unir las rutas para obtener las rutas.
   La primera linea es la cantidad de ciudades que se encuentran en el mapa.
   Las siguientes lineas son las coordenadas de las ciudades, de la forma "x y z", donde x es la coordenada en el eje "x", y es la coordenada en el eje "y" y z es la recompensa de ir a esa ciudad.
   La salida es una lista de rutas que generan el mayor beneficio posible.
   El beneficio de una ruta es la suma de las recompensas de las ciudades que la componen menos la distancia recorrida entre las ciudades.
*/

// Funcion principal para ejecutar el algoritmo.
fun main(args: Array<String>) : Unit {
    // Se lee el archivo que contiene las coordenadas de las ciudades.
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
    
    // Se crea una matriz con las recompensas y el indice de la ciudad.
    var recompensa : Array<Array<Int>> = Array(ciudades) { Array(2) {0} }
    for (i in 0 until ciudades) {
        recompensa[i][0] = recompensaList[i]
        recompensa[i][1] = i
    }

    // Se obtiene la ruta que genera el mayor beneficio.
    var maxX = coordenadas.maxBy { it[0] }[0]
    var maxY = coordenadas.maxBy { it[1] }[1]
    var recompensaObtenida = 0.0
    // Se ejecuta el algoritmo divide and conquer para obtener la ruta que genere el mayor beneficio.
    var ruta = divideAndConquerPRMB(ciudades, coordenadas, recompensa, 0.0, maxX.toDouble(), 0.0, maxY.toDouble())
    if (ruta.third == 0) {
        for (i in 0 until ruta.first.split(" ").size) {
            for (j in 0 until recompensa.size) {
                if (ruta.first.split(" ")[i].toInt() == recompensa[j][1]) {
                    recompensaObtenida += recompensa[j][0].toDouble()
                }
            }
        }
        
        // Se imprime la ruta que genera el mayor beneficio y el beneficio obtenido.
        recompensaObtenida -= ruta.second}
    else {recompensaObtenida = ruta.second}
    // Recompesa pero con 4 decimales
    recompensaObtenida = Math.round(recompensaObtenida * 10000.0) / 10000.0
    println(ruta.first)
    println(recompensaObtenida)
}




// Funcion que divide el mapa en dos partes iguales y busca la ruta que genere mas beneficio.
fun divideAndConquerPRMB( ciudades: Int, coordenadas: Array<List<Int>>, recompensa: Array<Array<Int>>, desdeX: Double, divisionX : Double, desdeY : Double, divisionY : Double) : Triple<String, Double, Int>{

    var ruta = Triple("", 0.0, 0)
    var suma = 0
    var divisionEnY = false
    // Se verifica si hay mas de 3 ciudades en la misma coordenada en el eje "x".
    for (i in 0 until coordenadas.size) {
        for (j in 0 until coordenadas.size) {
            if (coordenadas[i][0] == coordenadas[j][0] && i != j) {suma += 1}
        }
        if (suma > 3) {divisionEnY = true}
    }

    // Se verifica si hay 3 o menos ciudades en el mapa.
    if (0 < ciudades && ciudades <= 3) {
        ruta = resolverMiniPRMB(coordenadas, recompensa)}
    else if (ciudades == 0){}
    else {

        // Se verifica si se debe dividir el mapa en el eje "y" o en el eje "x".
        if (divisionEnY) {
            // Se divide el mapa en dos partes iguales en el eje "y".
            var maxYmitad = (divisionY - desdeY) / 2 + desdeY
            // Se obtienen las coordenadas de las ciudades que se encuentran en la parte de abajo y en la parte de arriba del mapa.
            var coordenadasAbajo = coordenadas.filter { it[1] <= maxYmitad }
            var coordenadasArriba = coordenadas.filter { it[1] > maxYmitad }
            var recompensaArriba = MutableList(coordenadasArriba.size) { arrayOf(0, 0) }
            var recompensaAbajo = MutableList(coordenadasAbajo.size) { arrayOf(0, 0)}
            var i = 0
            var j : Int
            while (i < coordenadasArriba.size){
                j = i
                while (j < ciudades){
                    if (coordenadasArriba[i][2] == recompensa[j][1]) {
                        recompensaArriba[i] = recompensa[j]
                        break}
                    j++}
                i++}
            i = 0   
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
            // Se obtiene la ruta que genera el mayor beneficio en la parte de arriba y en la parte de abajo del mapa ejecutando el algoritmo divide and conquer.
            var rutaArriba = divideAndConquerPRMB(coordenadasArriba.size, coordenadasArribaa, recompensaArriba.toTypedArray(), desdeX, divisionX, maxYmitad.toDouble(), divisionY)
            var rutaAbajo = divideAndConquerPRMB(coordenadasAbajo.size, coordenadasAbajoo, recompensaAbajo.toTypedArray(), desdeX, divisionX, desdeY, maxYmitad.toDouble())
            // Se unen las rutas obtenidas en la parte de arriba y en la parte de abajo del mapa.
            ruta = unirRutasCasos(Pair(rutaArriba.first, rutaArriba.second), Pair(rutaAbajo.first, rutaAbajo.second), coordenadasArribaa, coordenadasAbajoo)
        }


        else {
            // Se divide el mapa en dos partes iguales en el eje "x".
            var maxXmitad = (divisionX - desdeX) / 2 + desdeX
            // Se obtienen las coordenadas de las ciudades que se encuentran en la parte de la izquierda y en la parte de la derecha del mapa.
            var coordenadasIzq = coordenadas.filter { it[0] <= maxXmitad }
            var coordenadasDer = coordenadas.filter { it[0] > maxXmitad }
            var recompensaIzq = MutableList(coordenadasIzq.size) { arrayOf(0, 0) }
            var recompensaDer = MutableList(coordenadasDer.size) { arrayOf(0, 0)}
            var i = 0
            var j : Int
            while (i < coordenadasIzq.size){
                j = i
                while (j < ciudades){
                    if (coordenadasIzq[i][2] == recompensa[j][1]) {
                        recompensaIzq[i] = recompensa[j]
                        break}
                    j++}
                i++}
            i = 0   
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
            // Se obtiene la ruta que genera el mayor beneficio en la parte de la izquierda y en la parte de la derecha del mapa ejecutando el algoritmo divide and conquer.
            var rutaIzq = divideAndConquerPRMB(coordenadasIzq.size, coordenadasIzqq, recompensaIzq.toTypedArray(), desdeX, maxXmitad.toDouble(), desdeY, divisionY)
            var rutaDer = divideAndConquerPRMB(coordenadasDer.size, coordenadasDerr, recompensaDer.toTypedArray(), maxXmitad.toDouble(), divisionX, desdeY, divisionY)
            // Se unen las rutas obtenidas en la parte de la izquierda y en la parte de la derecha del mapa.
            ruta = unirRutasCasos(Pair(rutaIzq.first, rutaIzq.second), Pair(rutaDer.first, rutaDer.second), coordenadasIzqq, coordenadasDerr)
        }
    }
    return ruta
}



// Funcion que resuelve el problema de entre 3 o menos ciudades.
fun resolverMiniPRMB(coordenadas: Array<List<Int>>, recompensa: Array<Array<Int>>) : Triple<String, Double, Int> {
    val ciudades = coordenadas.size
    var ruta = Triple("", 0.0, 0)
    // Se verifica si hay 1, 2 o 3 ciudades en el mapa.
    if (ciudades == 1) {
        var ganancia = recompensa[0][0]
        ruta = Triple(coordenadas[0][2].toString(), ganancia.toDouble(), 0)
    }
    else if (ciudades == 2) {
        var ganancia = gananciaRuta(coordenadas, recompensa)
        // Se obtiene la ruta que genera el mayor beneficio.
        if (ganancia.second > recompensa[0][0]) {
            if (ganancia.second > recompensa[1][0]) {ruta = Triple(ganancia.first, ganancia.second, 0)}
            else {ruta = Triple(coordenadas[0][2].toString(), recompensa[0][0].toDouble(), 0)}
        } else {
            if (recompensa[1][0] > recompensa[0][0]) {ruta = Triple(coordenadas[1][2].toString(), recompensa[1][0].toDouble(), 0)}
            else {ruta = Triple(coordenadas[0][2].toString(), recompensa[0][0].toDouble(), 0)}
        }
    }
    else if (ciudades == 3) {
        // Se obtiene la ganancia de las rutas que se pueden formar con las ciudades.
        var ganancia12 = gananciaRuta(coordenadas.sliceArray(0 until 2), recompensa.sliceArray(0 until 2))
        var ganancia23 = gananciaRuta(coordenadas.sliceArray(1 until 3), recompensa.sliceArray(1 until 3))
        var ganancia13 = gananciaRuta(coordenadas.slice(listOf(0, 2)).toTypedArray(), recompensa.slice(listOf(0, 2)).toTypedArray())
        var ruta12 = ganancia12.first.split(" ")
        var ruta23 = ganancia23.first.split(" ")
        var ganancia123 = Pair(ganancia12.first+" " + ruta23[1], ganancia12.second + ganancia23.second - recompensa[1][0])
        var ganancia132 = Pair(ganancia13.first+" " + ruta23[0] , ganancia13.second + ganancia23.second - recompensa[2][0] )
        var ganancia213 = Pair(ruta12[1]+" "+ ganancia13.first, ganancia12.second + ganancia13.second - recompensa[0][0])

        lateinit var ruta1: Pair<String, Double>
        lateinit var ruta2: Pair<String, Double>
        lateinit var ruta3: Pair<String, Double>

        // Se obtiene la ruta que genera el mayor beneficio.
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
                ruta = Triple(ruta1.first, ruta1.second, 0)}
            else {ruta = Triple(ruta3.first, ruta3.second, 0)}
        } else {
            if (ruta2.second > ruta3.second) {
                ruta = Triple(ruta2.first, ruta2.second, 0)}
            else {ruta = Triple(ruta3.first, ruta3.second, 0)}
        }
    }
    else {println("Error")}
    return ruta
}


// Funcion que obtiene la ganancia de una ruta.
fun gananciaRuta(coordenadas: Array<List<Int>>, recompensa: Array<Array<Int>>) : Pair<String, Double> {
    var ganancia : Double
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


// Funcion que obtiene el costo de una ruta.
fun costo(coordenadas: Array<List<Int>>) : Double {
    var cost = 0.0
    for (i in 0 until coordenadas.size - 1) {
        cost += Math.sqrt(Math.pow((coordenadas[i][0] - coordenadas[i+1][0]).toDouble(), 2.0) + Math.pow((coordenadas[i][1] - coordenadas[i+1][1]).toDouble(), 2.0))
    }
    return cost
}


// Clase para guardar las coordenadas de las ciudades.
data class Ciudad(var array: Array<Int>, var entero: Int, var booleano: Boolean)

// Funcion para unir las rutas.
fun unirRutasCasos(rutaIzq: Pair<String, Double>, rutaDer: Pair<String, Double>, coordenadasIzq : Array<List<Int>>, coordenadasDer : Array<List<Int>>) : Triple<String, Double, Int> {
    var ruta = Triple("", 0.0, 0)
    var rutaPrincipio : List<String>
    
    // Se verifica si se obtuvieron rutas en la parte de la izquierda y en la parte de la derecha del mapa.
    if (rutaIzq.first != "" && rutaDer.first != "") {
        rutaPrincipio = (rutaIzq.first +" "+ rutaDer.first).split(" ")
        // Se unen las coordenadas de las ciudades obtenidas en la parte de la izquierda y en la parte de la derecha del mapa.
        var coordenadas = coordenadasIzq + coordenadasDer
        var datosCiudades = MutableList(rutaPrincipio.size) { Ciudad(Array<Int>(3) { 0 }, 0, false) }

        // Se guardan las coordenadas de las ciudades obtenidas en la parte de la izquierda y en la parte de la derecha del mapa y si estas ciudades ya fueron visitadas.
        for  (i in 0 until rutaPrincipio.size) {
            for (j in 0 until coordenadas.size) {
                if (coordenadas[j][2] == rutaPrincipio[i].toInt()) {
                    datosCiudades[i].array = coordenadas[j].toTypedArray()
                    datosCiudades[i].entero = coordenadas[j][2]
                    datosCiudades[i].booleano = false
                }
            }
        }

        // Se crea una copia de las coordenadas de las ciudades obtenidas en la parte de la izquierda y en la parte de la derecha del mapa.
        val numeroDeCopias = rutaPrincipio.size
        val copiasDeDatosCiudades = MutableList(numeroDeCopias) { datosCiudades.toMutableList() }
        var costoMinimo = Double.MAX_VALUE
        for (i in 0 until rutaPrincipio.size) {
            // Se llama a la funcion que escoge el vecino que genere el menor costo.
            var rutas = escogerVecino(rutaPrincipio[i].toInt() , copiasDeDatosCiudades[i], rutaPrincipio)
            if (rutas.second < costoMinimo) {
                costoMinimo = rutas.second
                ruta = Triple(rutas.first, costoMinimo, 0)}
        }
    }
    else if (rutaIzq.first != "" && rutaDer.first == "") {
        ruta = Triple(rutaIzq.first, rutaIzq.second, 1)
    }
    else if (rutaIzq.first == "" && rutaDer.first != "") {
        ruta = Triple(rutaDer.first, rutaDer.second, 1)
    }
    return ruta
}                



// Funcion que verifica si todas las ciudades ya fueron visitadas.
fun todasVisitadas(booleano: BooleanArray) : Boolean {
    var todas = true
    for (i in 0 until booleano.size) {
        if (booleano[i] == false) {todas = false}
    }
    return todas
}


// Funcion que obtiene la distancia entre dos ciudades.
fun costoRuta(ciudad: Array<Int>, rutas: Array<Array<Int>>) : Array<Array<Any>> {
    var distancia = Array(rutas.size) { Array<Any>(2) {0} }
    for (i in 0 until rutas.size) {
        distancia[i][0] = rutas[i][2]
        distancia[i][1] = costo(arrayOf(ciudad.toList(), rutas[i].toList()))
    }
    return distancia
}


// Funcion que escoge el vecino que genere el menor costo.
fun escogerVecino(ciudadComienzo : Int, datosCiudades: MutableList<Ciudad>, rutaPrincipio: List<String>) : Pair<String, Double> {
    var i = 1
    var ciudad = ciudadComienzo
    var costo = 0.0
    var ciudades = "" + ciudad.toString() + " "
    // Se inicializa el booleano de las ciudades.
    datosCiudades.forEach { it.booleano = false }
    while (i < rutaPrincipio.size) {
        var rutasCaR1 = Array<Int>(3) {0}
        var rutasCaR2 = Array<Array<Int>>(rutaPrincipio.size - i) { Array<Int>(3) {0} }
        var k = 0
        // Se obtienen las rutas que se pueden formar con las ciudades que no han sido visitadas.
        if (i == 1) {
            for (j in 0 until rutaPrincipio.size) {
                if (ciudad == datosCiudades[j].entero) {
                    rutasCaR1 = datosCiudades[j].array
                    datosCiudades[j].booleano = true
                    break}
            }
        }
        else {
            for (j in 0 until rutaPrincipio.size) {
                if (ciudad == datosCiudades[j].entero) {
                    rutasCaR1 = datosCiudades[j].array
                    datosCiudades[j].booleano = true
                }
            }
        }

        for (j in 0 until rutaPrincipio.size) {
            if (datosCiudades[j].booleano == false && datosCiudades[j].entero != ciudad) {
                rutasCaR2[k] = datosCiudades[j].array
                k++
            }
        }
        // Se obtiene la ruta que genere el menor costo.
        var ruta = distancias(Pair(rutasCaR1, rutasCaR2))
        ciudad = ruta.first
        costo += ruta.second
        if (i == rutaPrincipio.size - 1) {ciudades += ciudad.toString()}
        else {ciudades += ciudad.toString() + " "}

        i++
        // Se verifica si todas las ciudades ya fueron visitadas.
        if (datosCiudades.all { it.booleano }) { break }
        
    }
    return Pair(ciudades, costo)
}


// Funcion que obtiene la distancia entre dos ciudades.
fun distancias(rutasCaR: Pair<Array<Int>, Array<Array<Int>>>) : Pair<Int, Double> {
    lateinit var distancia: Array<Array<Any>>
    var ciudad = rutasCaR.first
    var rutas = rutasCaR.second
    distancia = costoRuta(ciudad, rutas)
    // Encontrar la ruta con menor costo en el segundo indice de la matriz distancia y guardar el indice de la ruta con menor costo.
    var minimoCosto = distancia.minBy { it[1].toString().toDouble() }
    var indice = distancia.indexOf(minimoCosto)
    return Pair(distancia[indice][0].toString().toInt(), minimoCosto[1].toString().toDouble())
}