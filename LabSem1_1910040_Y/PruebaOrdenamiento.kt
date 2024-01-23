import kotlin.system.measureTimeMillis
import kotlin.system.exitProcess
import kotlin.random.Random

fun main(args: Array<String>) {
    var tnum : Int = 0
    var secuencia : String = ""
    var algoritmo : String = ""
    var num : Int = 0

    for (i in 0 until 8 step 2) {
        if (args[i] == "-t") {tnum = args[i +1].toInt()}
        else if (args[i] == "-s") {secuencia = args[i +1]}
        else if (args[i] == "-a") {algoritmo = args[i +1]}
        else if (args[i] == "-n") {num = args[i +1].toInt()}
        else {println("Error al indicar los argumentos")
            exitProcess(1)}
    }
    if (secuencia == "random") {random(tnum, num, algoritmo)}
    else if (secuencia == "randomd") {randomd(tnum, num, algoritmo)}
    else if (secuencia == "sorted") {sorted(tnum, num, algoritmo)}
    else if (secuencia == "sortedd") {sortedd(tnum, num, algoritmo)}
    else if (secuencia == "inv") {inv(tnum, num, algoritmo)}
    else if (secuencia == "zu") {zu(tnum, num, algoritmo)}
    else if (secuencia == "media") {media(tnum, num, algoritmo)}
    else {println("Error al indicar los argumentos")
        exitProcess(1)}
}

fun estaEnOrdenAscendente(secuencia: Array<Number>): Boolean {
    val n = secuencia.size
    for (i in 0 until n-1) {
	    if (secuencia [i] > secuencia [i+1]) {return false} 
    }
    return true
}

fun algoritmoAEjecutar(array : Array<Number>, tnum: Int, algoritmo: String) {
    var array1 : Array<Number>
    var array2 : Array<Number>
    var timeProm : Double

    val mediaArray = array.map { it.toDouble() }.average()
    val diferenciasCuadradas = Array<Double>(array.size) { 0.0 }
    for (i in array.indices) {
        val diferencia = array[i].toDouble() - mediaArray
        diferenciasCuadradas[i] = diferencia * diferencia}
    val mediaDiferenciasCuadradas = diferenciasCuadradas.average()
    val desviacionEstandar = kotlin.math.sqrt(mediaDiferenciasCuadradas)

    if (algoritmo == "is,bs") {

        val copies = Array(tnum*2) { array.copyOf() }
        var i = 0
        timeProm = 0.0
        repeat(tnum) {
            val elapsedTime1 = measureTimeMillis {array1 = insertionSort(copies[i])}
            println("Insertion Sort ---> Tiempo transcurrido: " + elapsedTime1.toString() + " ms")
            if (estaEnOrdenAscendente(array1)) {println("El arreglo está ordenado")}
            else {println("El arreglo no está ordenado")
                exitProcess(1)}
            timeProm += elapsedTime1
            i++
            println("")
        }
        println("Tiempo promedio Insertion Sort: " + (timeProm/tnum).toString() + " ms")
        println("Desviación estándar Insertion Sort: " + desviacionEstandar.toString())
        println("------------------------------------")
        println("")

        timeProm = 0.0
        repeat(tnum) {
            val elapsedTime2 = measureTimeMillis {array2 = bubbleSort(copies[i])}
            println("Bubble Sort ---> Tiempo transcurrido: " + elapsedTime2.toString() + " ms")
            if (estaEnOrdenAscendente(array2)) {println("El arreglo está ordenado")}
            else {println("El arreglo no está ordenado")
                exitProcess(1)}
            timeProm += elapsedTime2
            i++
            println("")
        }
        println("Tiempo promedio Bubble Sort: " + (timeProm/tnum).toString() + " ms")
        println("Desviación estándar Bubble Sort: " + desviacionEstandar.toString())
        println("")
    }

    else if (algoritmo == "is") {
        val copies = Array(tnum) { array.copyOf() }
        var i = 0
        timeProm = 0.0
        repeat(tnum) {
            val elapsedTime1 = measureTimeMillis {array1 = insertionSort(copies[i])}
            println("Insertion Sort ---> Tiempo transcurrido: " + elapsedTime1.toString() + " ms")
            if (estaEnOrdenAscendente(array1)) {println("El arreglo está ordenado")}
            else {println("El arreglo no está ordenado")
                exitProcess(1)}
            timeProm += elapsedTime1
            i++
            println("")
        }
        println("Tiempo promedio Insertion Sort: " + (timeProm/tnum).toString() + " ms")
        println("Desviación estándar Insertion Sort: " + desviacionEstandar.toString())
        println("")
    }

    else if (algoritmo == "bs") {
        val copies = Array(tnum) { array.copyOf() }
        var i = 0
        timeProm = 0.0
        repeat(tnum) {
            val elapsedTime2 = measureTimeMillis {array2 = bubbleSort(copies[i])}
            println("Bubble Sort ---> Tiempo transcurrido: " + elapsedTime2.toString() + " ms")
            if (estaEnOrdenAscendente(array2)) {println("El arreglo está ordenado")}
            else {println("El arreglo no está ordenado")
                exitProcess(1)}
            timeProm += elapsedTime2
            i++
        }
        println("Tiempo promedio Bubble Sort: " + (timeProm/tnum).toString() + " ms")
        println("Desviación estándar Bubble Sort: " + desviacionEstandar.toString())
    }

    else {println("Error al indicar los argumentos")}
}

fun random(tnum: Int, num: Int, algoritmo: String) {
    val N = num
    val A = Array<Number>(N) { Random.nextInt(0, N+1) }
    algoritmoAEjecutar(A, tnum, algoritmo)
}

fun randomd(tnum: Int, num: Int, algoritmo: String) {
    val N = num
    val A = Array<Number>(N) { Random.nextDouble(0.0, 1.1) }
    algoritmoAEjecutar(A, tnum, algoritmo)
}

fun sorted(tnum: Int, num: Int, algoritmo: String) {
    val N = num
    val A = Array<Number>(N) { it + 1 }
    algoritmoAEjecutar(A, tnum, algoritmo)
}

fun sortedd(tnum: Int, num: Int, algoritmo: String) {
    val N = num
    val A = if (N == 1) Array<Number>(N) { 1.0 } else Array<Number>(N) { it.toDouble() / (N - 1) }
    algoritmoAEjecutar(A, tnum, algoritmo)
}

fun inv(tnum: Int, num: Int, algoritmo: String) {
    val N = num
    val A = Array<Number>(N) { N - it }
    algoritmoAEjecutar(A, tnum, algoritmo)
}

fun zu(tnum: Int, num: Int, algoritmo: String) {
    val N = num
    val A = Array<Number>(N) { Random.nextInt(2) }
    algoritmoAEjecutar(A, tnum, algoritmo)
}

fun media(tnum: Int, num: Int, algoritmo: String) {
    val N = num
    val A = Array<Number>(N) {
        if (it < N / 2) it + 1
        else N - it}
    algoritmoAEjecutar(A, tnum, algoritmo)
}